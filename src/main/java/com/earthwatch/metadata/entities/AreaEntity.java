package com.earthwatch.metadata.entities;

import com.earthwatch.metadata.util.json.JTSPolygonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Polygon;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.util.ArrayList;
import java.util.List;

@Entity (name = "areas")
@Setter
@Getter
public class AreaEntity extends BaseEntity {

    @Column(name = "geometry", nullable = false, columnDefinition = "geometry(POLYGON,4326)")
    @JsonSerialize(using = JTSPolygonSerializer.class)
    private Polygon geometry;

//    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<GeoMonitorEntity> geoMonitors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private CustomerEntity owner;
}

