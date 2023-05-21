package com.earthwatch.metadata.geomonitor;

import com.earthwatch.metadata.area.exception.AreaNotFoundException;
import com.earthwatch.metadata.area.AreaRepository;
import com.earthwatch.metadata.customer.CustomerRepository;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.entities.AreaEntity;
import com.earthwatch.metadata.entities.CustomerEntity;
import com.earthwatch.metadata.entities.GeoMonitorEntity;
import com.earthwatch.metadata.geomonitor.dto.CreateGeoMonitorRequest;
import com.earthwatch.metadata.geomonitor.dto.CreateGeoMonitorResponse;
import com.earthwatch.metadata.geomonitor.exceptions.GeoMonitorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.TransactionScoped;
import java.util.List;
import java.util.Optional;

@Service
public class GeoMonitorService {
    @TransactionScoped
    public CreateGeoMonitorResponse create(CreateGeoMonitorRequest request) throws AreaNotFoundException, CustomerNotFoundException {
        Optional<AreaEntity> area = areaRepository.findById(request.getAreaId());
        if (area.isEmpty()) {
            String message = String.format("No area exists with id: %d", request.getAreaId());
            throw new AreaNotFoundException(message);
        }
        Optional<CustomerEntity> customer = customerRepository.findById(request.getOwnerId());
        if (customer.isEmpty()) {
            String message = String.format("No customer exists with id: %d", request.getOwnerId());
            throw new CustomerNotFoundException(message);
        }
        // Save GeoMonitor
        GeoMonitorEntity geoMonitor = GeoMonitorEntity.builder()
                .area(area.get())
                .owner(customer.get())
                .geoMonitorType(request.getType())
                .build();
        GeoMonitorEntity savedGeomonitor = geoMonitorRepository.save(geoMonitor);

//        // Update customer
//        List<GeoMonitorEntity> geoMonitors_customer = customer.get().getGeoMonitors();
//        geoMonitors_customer.add(savedGeomonitor);
//        customer.get().setGeoMonitors(geoMonitors_customer);
//        customerRepository.save(customer.get());
//
//        // Update Area
//        List<GeoMonitorEntity> geoMonitors_area = area.get().getGeoMonitors();
//        geoMonitors_area.add(savedGeomonitor);
//        customer.get().setGeoMonitors(geoMonitors_area);
//        areaRepository.save(area.get());

        CreateGeoMonitorResponse response = CreateGeoMonitorResponse.builder()
                .id(savedGeomonitor.getId())
                .type(savedGeomonitor.getGeoMonitorType())
                .ownerName(savedGeomonitor.getOwner().getUsername())
                .build();
        return response;
    }

    public List<GeoMonitorEntity> listByOwnerId(Integer ownerid) throws CustomerNotFoundException {
        Optional<CustomerEntity> customer = customerRepository.findById(ownerid);
        if (customer.isEmpty()) {
            String message = String.format("No customer exists with id: %d", ownerid);
            throw new CustomerNotFoundException(message);
        }
        return customer.get().getGeoMonitors();
    }

    public List<GeoMonitorEntity> listByAreaId(Integer areaId) throws AreaNotFoundException{
        Optional<AreaEntity> area = areaRepository.findById(areaId);
        if (area.isEmpty()) {
            String message = String.format("No area exists with id: %d", areaId);
            throw new AreaNotFoundException(message);
        }
        return area.get().getGeoMonitors();
    }

    public GeoMonitorEntity getById(Integer id) throws GeoMonitorNotFoundException {
        Optional<GeoMonitorEntity> geoMonitor = geoMonitorRepository.findById(id);
        if (geoMonitor.isEmpty()) {
            String message = String.format("No geomonitor exists with id: %d", id);
            throw new GeoMonitorNotFoundException(message);
        }
        return geoMonitor.get();
    }
    @Autowired
    private GeoMonitorRepository geoMonitorRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CustomerRepository customerRepository;
}
