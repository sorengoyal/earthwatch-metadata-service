package com.earthwatch.metadata.area;

import com.earthwatch.metadata.entities.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Integer>  {
    public List<AreaEntity> findByOwnerId(Integer ownerId);
}
