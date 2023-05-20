package com.earthwatch.metadata.entities;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.IOException;

@Entity (name = "areas")
@Setter
@Getter
public class Area extends BaseEntity {

    @Column(name = "geometry", nullable = false, columnDefinition = "geometry(POLYGON,4326)")
    @JsonSerialize(using = JTSPolygonSerializer.class)
    Polygon geometry;
}

class JTSPolygonSerializer extends JsonSerializer<Polygon> {
    @Override
    public void serialize(Polygon value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("type");
        gen.writeString("Polygon");
        gen.writeFieldName("coordinates");
        gen.writeStartArray();
        for (Coordinate point: value.getCoordinates()) {
            gen.writeStartArray();
            gen.writeNumber(point.x);
            gen.writeNumber(point.y);
            gen.writeEndArray();
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}

// Relationship @manyToOne @OneToMany
