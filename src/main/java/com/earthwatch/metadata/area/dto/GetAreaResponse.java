package com.earthwatch.metadata.area.dto;

import com.earthwatch.metadata.util.json.JTSPolygonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

@NoArgsConstructor
@Getter
@Setter
public class GetAreaResponse {
    Integer id;
    @JsonSerialize(using = JTSPolygonSerializer.class)
    private Polygon geometry;
}
