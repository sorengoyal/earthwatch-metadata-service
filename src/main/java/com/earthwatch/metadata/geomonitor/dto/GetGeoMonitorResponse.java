package com.earthwatch.metadata.geomonitor.dto;

import com.earthwatch.metadata.entities.GeoMonitorType;
import com.earthwatch.metadata.util.json.JTSPolygonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetGeoMonitorResponse {
    private Integer id;
    @JsonSerialize(using = JTSPolygonSerializer.class)
    private Polygon geometery;
    private GeoMonitorType type;
    private String ownerName;
}
