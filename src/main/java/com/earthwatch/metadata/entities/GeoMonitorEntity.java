package com.earthwatch.metadata.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity(name = "geomonitors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GeoMonitorEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "area_id")
    private AreaEntity area;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GeoMonitorType geoMonitorType;

//    @Column(name = "owner", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private CustomerEntity owner;

}
