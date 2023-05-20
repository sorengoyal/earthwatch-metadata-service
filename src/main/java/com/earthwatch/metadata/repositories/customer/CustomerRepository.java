package com.earthwatch.metadata.repositories.customer;

import com.earthwatch.metadata.entities.CustomerEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Scope("Singleton") // scope is used to specify the number of instances of the object during autowiring
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {


}
