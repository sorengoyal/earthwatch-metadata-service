package com.earthwatch.metadata.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity(name = "customers")
@Setter
@Getter
public class CustomerEntity extends BaseEntity{

    @Column(name = "username", nullable = false, length = 150)
    String username;

    @Column(name = "email", nullable = true, length = 100)
    private String email;
    @Column(name = "password", nullable = true, length = 100)
    String password;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeoMonitorEntity> geoMonitors;
}
