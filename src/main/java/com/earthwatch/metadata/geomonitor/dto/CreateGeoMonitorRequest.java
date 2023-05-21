package com.earthwatch.metadata.geomonitor.dto;

import com.earthwatch.metadata.entities.GeoMonitorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateGeoMonitorRequest {
    private Integer areaId;
    private GeoMonitorType type;
    private Integer ownerId;
}
