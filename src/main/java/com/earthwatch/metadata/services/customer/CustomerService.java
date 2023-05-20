package com.earthwatch.metadata.services.customer;

import com.earthwatch.metadata.entities.CustomerEntity;
import com.earthwatch.metadata.repositories.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    public CustomerEntity create(String name) {
        CustomerEntity customer = new CustomerEntity();
        customer.setName(name);
        CustomerEntity savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Autowired
    private CustomerRepository customerRepository;
}
