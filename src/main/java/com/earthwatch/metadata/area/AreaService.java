package com.earthwatch.metadata.area;

import com.earthwatch.metadata.area.dto.CreateAreaResponse;
import com.earthwatch.metadata.area.dto.GetAreaResponse;
import com.earthwatch.metadata.area.exception.AreaNotFoundException;
import com.earthwatch.metadata.customer.CustomerRepository;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.entities.AreaEntity;

//import org.postgis.Polygon;
import com.earthwatch.metadata.entities.CustomerEntity;
import org.locationtech.jts.geom.Polygon;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    public CreateAreaResponse create(Polygon polygon, Integer ownerId) throws CustomerNotFoundException {
        Optional<CustomerEntity> customer = customerRepository.findById(ownerId);
        if (customer.isEmpty()) {
            String message = String.format("No customer exists with id: %d", ownerId);
            throw new CustomerNotFoundException(message);
        }
        AreaEntity area = new AreaEntity();
        area.setGeometry(polygon);
        area.setOwner(customer.get());
        AreaEntity areaWithId = areaRepository.save(area);
        CreateAreaResponse response = modelMapper.map(areaWithId, CreateAreaResponse.class);
        return response;
    }

    public List<GetAreaResponse> list(Integer ownerId) {
        List<GetAreaResponse> areas = areaRepository.findByOwnerId(ownerId).stream()
                .map(areaEnity -> modelMapper.map(areaEnity, GetAreaResponse.class))
                .toList();
        return areas;
    }
    public AreaEntity update(){
        throw new UnsupportedOperationException();
    }

    public GetAreaResponse getById(int id, Integer  ownerId) throws AreaNotFoundException {
        Optional<AreaEntity> area = areaRepository.findById(id);
        if (area.isEmpty()) {
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        if (area.get().getOwner().getId() != ownerId) {
            System.out.println(String.format("Customer %d is not allowed to access Area %d", ownerId, id));
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        GetAreaResponse response = modelMapper.map(area.get(), GetAreaResponse.class);
        return response;
    }

    public AreaEntity deleteById(Integer id, Integer ownerId) throws AreaNotFoundException {
        Optional<AreaEntity> area = areaRepository.findById(id);
        if (area.isEmpty()) {
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        if (area.get().getOwner().getId() != ownerId) {
            System.out.println(String.format("Customer %d is not allowed to access Area %d", ownerId, id));
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        areaRepository.delete(area.get());

        return area.get();
    }

    public void deleteTask(int id) {
        throw new UnsupportedOperationException();
    }
    public AreaEntity add(AreaEntity area) {
        throw new UnsupportedOperationException();
    }

    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
}