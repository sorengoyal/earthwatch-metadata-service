package com.earthwatch.metadata.area;

import com.earthwatch.metadata.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Integer>  {
}
