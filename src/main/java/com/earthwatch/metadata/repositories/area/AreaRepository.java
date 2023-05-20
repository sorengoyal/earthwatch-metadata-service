package com.earthwatch.metadata.repositories.area;

import com.earthwatch.metadata.entities.Area;
import com.earthwatch.metadata.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends JpaRepository<Area, Integer>  {
}
