package com.earthwatch.metadata.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "customers")
@Setter
@Getter
public class CustomerEntity extends BaseEntity{

    @Column(name = "name", nullable = false, length = 150)
    String name;
}
