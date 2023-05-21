package com.earthwatch.metadata.area;

import com.earthwatch.metadata.area.exception.AreaNotFoundException;
import com.earthwatch.metadata.entities.AreaEntity;

//import org.postgis.Polygon;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    public AreaEntity create(Polygon polygon) {
        AreaEntity area = new AreaEntity();
        area.setGeometry(polygon);
        AreaEntity areaWithId = areaRepository.save(area);
        return areaWithId;
    }

    public List<AreaEntity> getAll() {
        List<AreaEntity> areas = areaRepository.findAll();
        return areas;
    }
    public AreaEntity update(){
        throw new UnsupportedOperationException();
    }

    public AreaEntity getById(int id) throws AreaNotFoundException {
        Optional<AreaEntity> area = areaRepository.findById(id);
        if (area.isEmpty()) {
            String msg = String.format("id: %d not found", id);
            throw new AreaNotFoundException(msg);
        }
        return area.get();
    }

    public AreaEntity deleteById(int id) throws AreaNotFoundException {
        Optional<AreaEntity> area = areaRepository.findById(id);
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
    public AreaEntity add(AreaEntity area) {
        throw new UnsupportedOperationException();
    }

    @Autowired
    private AreaRepository areaRepository;
}