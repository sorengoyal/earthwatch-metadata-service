package com.earthwatch.metadata.customer;

import com.earthwatch.metadata.customer.dto.CreateCustomerRequest;
import com.earthwatch.metadata.customer.dto.CreateCustomerResponse;
import com.earthwatch.metadata.customer.dto.GetCustomerResponse;
import com.earthwatch.metadata.customer.dto.LoginCustomerRequest;
import com.earthwatch.metadata.customer.dto.LoginCustomerResponse;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.customer.exceptions.IncorrectPasswordException;
import com.earthwatch.metadata.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        CreateCustomerResponse response = customerService.create(request);
        return ResponseEntity
                .created(URI.create("/customers/" + response.getId()))
                .body(response);
    }

    @PostMapping("/login/")
    public ResponseEntity<?> post(@RequestBody LoginCustomerRequest request ) {
        try {
            LoginCustomerResponse response = customerService.login(request);
            return ResponseEntity.ok().body(response);
        }
        catch (CustomerNotFoundException e) { //404
            System.out.println(e);
            return ResponseEntity.badRequest().body("Either username or password is incorrect");
        }
        catch (IncorrectPasswordException e) { //403
            System.out.println(e);
            return ResponseEntity.badRequest().body("Either username or password is incorrect");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        try {
            GetCustomerResponse response = customerService.getById(id);
            return ResponseEntity.ok(response);
        }
        catch (CustomerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }
    @Autowired
    private CustomerService customerService;
}
