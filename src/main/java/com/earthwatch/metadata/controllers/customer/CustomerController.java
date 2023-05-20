package com.earthwatch.metadata.controllers.customer;

import com.earthwatch.metadata.entities.CustomerEntity;
import com.earthwatch.metadata.services.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("customers")
public class CustomerController {
    @PostMapping("/")
    public ResponseEntity<?> post(@RequestBody CreateCustomerRequest request) {

        CustomerEntity customer = customerService.create(request.getName());
        return ResponseEntity
                .created(URI.create("/areas/" + customer.getId()))
                .body(customer);
    }

    @Autowired
    private CustomerService customerService;
}
