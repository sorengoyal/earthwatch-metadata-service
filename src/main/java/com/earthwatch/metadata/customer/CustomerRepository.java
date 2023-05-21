package com.earthwatch.metadata.customer;

import com.earthwatch.metadata.entities.CustomerEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Scope("Singleton") // scope is used to specify the number of instances of the object during autowiring
// use @Transaction to make a method a transaction
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

    public Optional<CustomerEntity> findByUsername(String username);

}
