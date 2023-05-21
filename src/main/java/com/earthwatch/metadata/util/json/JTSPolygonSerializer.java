package com.earthwatch.metadata.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

import java.io.IOException;

public class JTSPolygonSerializer extends JsonSerializer<Polygon> {
    @Override
    public void serialize(Polygon value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("type");
        gen.writeString("Polygon");
        gen.writeFieldName("coordinates");
        gen.writeStartArray();
        for (Coordinate point : value.getCoordinates()) {
            gen.writeStartArray();
            gen.writeNumber(point.x);
            gen.writeNumber(point.y);
            gen.writeEndArray();
        }
        gen.writeEndArray();

        gen.writeEndObject();
    }
}
