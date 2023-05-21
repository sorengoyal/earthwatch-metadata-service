package com.earthwatch.metadata.geomonitor.dto;

import org.locationtech.jts.geom.Polygon;
import com.earthwatch.metadata.entities.GeoMonitorType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateGeoMonitorResponse {
    private Integer id;
    private Polygon polygon;
    private GeoMonitorType type;
    private String ownerName;
}
