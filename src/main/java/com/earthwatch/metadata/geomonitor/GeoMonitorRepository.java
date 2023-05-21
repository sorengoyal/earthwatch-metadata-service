package com.earthwatch.metadata.geomonitor;

import com.earthwatch.metadata.entities.GeoMonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoMonitorRepository extends JpaRepository<GeoMonitorEntity, Integer> {
}
