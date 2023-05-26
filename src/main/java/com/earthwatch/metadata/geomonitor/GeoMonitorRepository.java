package com.earthwatch.metadata.geomonitor;

import com.earthwatch.metadata.entities.GeoMonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeoMonitorRepository extends JpaRepository<GeoMonitorEntity, Integer> {
    List<GeoMonitorEntity> findByAreaId(Integer areaId);
    List<GeoMonitorEntity> findByOwnerId(Integer ownerId);
}
