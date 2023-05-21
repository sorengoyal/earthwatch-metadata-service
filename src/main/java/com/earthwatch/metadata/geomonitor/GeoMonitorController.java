package com.earthwatch.metadata.geomonitor;

import com.earthwatch.metadata.area.exception.AreaNotFoundException;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.entities.GeoMonitorEntity;
import com.earthwatch.metadata.geomonitor.dto.CreateGeoMonitorRequest;
import com.earthwatch.metadata.geomonitor.dto.CreateGeoMonitorResponse;
import com.earthwatch.metadata.geomonitor.dto.GetGeoMonitorResponse;
import com.earthwatch.metadata.geomonitor.exceptions.GeoMonitorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("geomonitors")
public class GeoMonitorController {

    @PostMapping("/")
    public ResponseEntity<?> post(@RequestBody CreateGeoMonitorRequest request) {
        try {
            CreateGeoMonitorResponse response = geoMonitorService.create(request);
            return ResponseEntity
                    .created(URI.create("/geomonitors/" + response.getId()))
                    .body(response);
        }
        catch (AreaNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (CustomerNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> list(@RequestParam(required = false) Integer ownerId,
                                  @RequestParam(required = false) Integer areaId) {
        if (areaId != null) {
            try {
                List<GetGeoMonitorResponse> geoMonitors = geoMonitorService.listByAreaId(areaId)
                        .stream()
                        .map(this::convertToGetGeoMonitorResponse)
                        .toList();
                return ResponseEntity
                        .ok(geoMonitors);
            }
            catch (AreaNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        else if (ownerId != null) {
            try {
                List<GetGeoMonitorResponse> geoMonitors = geoMonitorService.listByOwnerId(ownerId)
                        .stream()
                        .map(this::convertToGetGeoMonitorResponse)
                        .toList();
                return ResponseEntity
                        .ok(geoMonitors);
            }
            catch (CustomerNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        else {
            return ResponseEntity.badRequest().body("Invalid parameter");
        }
    }

    @GetMapping("/{id}/")
    public ResponseEntity<?> get(@PathVariable int id) {
        try {
            GeoMonitorEntity geoMonitor = geoMonitorService.getById(id);
            return ResponseEntity.ok().body(convertToGetGeoMonitorResponse(geoMonitor));
        }
        catch (GeoMonitorNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    private GetGeoMonitorResponse convertToGetGeoMonitorResponse(GeoMonitorEntity geoMonitor) {
        return GetGeoMonitorResponse.builder()
                .id(geoMonitor.getId())
                .type(geoMonitor.getGeoMonitorType())
                .geometery(geoMonitor.getArea().getGeometry())
                .ownerName(geoMonitor.getOwner().getUsername())
                .build();
    }

    @Autowired
    private GeoMonitorService geoMonitorService;

}
