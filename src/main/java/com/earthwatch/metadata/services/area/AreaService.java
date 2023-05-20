package com.earthwatch.metadata.services.area;

import com.earthwatch.metadata.area.AreaNotFoundException;
import com.earthwatch.metadata.entities.Area;
import com.earthwatch.metadata.repositories.area.AreaRepository;

//import org.postgis.Polygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    public Area create(Polygon polygon) {
        Area area = new Area();
        area.setGeometry(polygon);
        Area areaWithId = areaRepository.save(area);
        return areaWithId;
    }

    public List<Area> getAll() {
        List<Area> areas = areaRepository.findAll();
        return areas;
    }
    public Area update(){
        throw new UnsupportedOperationException();
    }

    public Area getById(int id) throws AreaNotFoundException {
        Optional<Area> area = areaRepository.findById(id);
        if (area.isEmpty()) {
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        return area.get();
    }

    public Area deleteById(int id) throws AreaNotFoundException {
        Optional<Area> area = areaRepository.findById(id);
        if (area.isEmpty()) {
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        areaRepository.delete(area.get());

        return area.get();
    }

    public void deleteTask(int id) {
        throw new UnsupportedOperationException();
    }
    public Area add(Area area) {
        throw new UnsupportedOperationException();
    }

    @Autowired
    private AreaRepository areaRepository;
}