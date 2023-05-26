package com.earthwatch.metadata.area;

import com.earthwatch.metadata.area.dto.CreateAreaResponse;
import com.earthwatch.metadata.area.dto.CreateAreaRequest;
import com.earthwatch.metadata.area.dto.ErrorResponse;
import com.earthwatch.metadata.area.dto.GetAreaResponse;
import com.earthwatch.metadata.area.exception.AreaNotFoundException;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.entities.AreaEntity;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("areas")
public class AreaController {

    @PostMapping("/")
    public ResponseEntity<?> post(@RequestBody CreateAreaRequest request, @AuthenticationPrincipal Integer ownerId) {
        if (request.getCoordinates().size() < 4) {
            return ResponseEntity
                    .badRequest()
                    .body("Length of coordinates should be greater of equal to 4");
        }
        int N = request.getCoordinates().size();
        Coordinate[] points = new Coordinate[N];
        for (int i = 0; i < N; i++) {
            List<Double> point = request.getCoordinates().get(i);
            if (point.size() != 2) {
                return ResponseEntity
                        .badRequest()
                        .body("Each coordinate must have 2 double values");
            }
            points[i] = new Coordinate(point.get(0), point.get(1));
        }
        GeometryFactory geometryFactory = new GeometryFactory();
        LinearRing linearRing = geometryFactory.createLinearRing(points);
        Polygon polygon = geometryFactory.createPolygon(linearRing);
        try {
            CreateAreaResponse area = areaService.create(polygon, ownerId);
            return ResponseEntity
                    .created(URI.create("/areas/" + area.getId()))
                    .body(area);
        }
        catch (CustomerNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/")
    public final ResponseEntity<List<GetAreaResponse>> list(@AuthenticationPrincipal Integer ownerId) {
        if (ownerId == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
        }
        List<GetAreaResponse> areas = areaService.list(ownerId);
        return ResponseEntity.ok(new ArrayList<>()); //areas
    }

    @GetMapping("/{id}/")
    public ResponseEntity<GetAreaResponse> get(@PathVariable int id, @AuthenticationPrincipal Integer ownerId) throws AreaNotFoundException {
        GetAreaResponse response = areaService.getById(id, ownerId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AreaEntity> delete(@PathVariable int id,  @AuthenticationPrincipal Integer ownerId) throws AreaNotFoundException{
        AreaEntity area = areaService.deleteById(id, ownerId);
        return ResponseEntity.ok(area);
    }

    @ExceptionHandler(AreaNotFoundException.class)
    ResponseEntity<ErrorResponse> handleErrors(Exception e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    public AreaController(@Autowired AreaService areaService) {
        this.areaService = areaService;
    }

    private AreaService areaService;
}
