package com.earthwatch.metadata.customer;

import com.earthwatch.metadata.customer.dto.CreateCustomerRequest;
import com.earthwatch.metadata.customer.dto.CreateCustomerResponse;
import com.earthwatch.metadata.customer.dto.GetCustomerResponse;
import com.earthwatch.metadata.customer.dto.LoginCustomerRequest;
import com.earthwatch.metadata.customer.dto.LoginCustomerResponse;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.customer.exceptions.IncorrectPasswordException;
import com.earthwatch.metadata.entities.CustomerEntity;
import com.earthwatch.metadata.customer.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    public CreateCustomerResponse create(CreateCustomerRequest request) {
        CustomerEntity customer = modelMapper.map(request, CustomerEntity.class);
        CustomerEntity savedCustomer = customerRepository.save(customer);
        CreateCustomerResponse response = modelMapper.map(savedCustomer, CreateCustomerResponse.class);
        return response;
    }

    public LoginCustomerResponse login(LoginCustomerRequest request) throws CustomerNotFoundException, IncorrectPasswordException {
        Optional<CustomerEntity> customer = customerRepository.findByUsername(request.getUsername());
        if (customer.isEmpty()) {
            String message = String.format("%s not found", request.getUsername());
            throw new CustomerNotFoundException(message);
        }
        if (!customer.get().getPassword().equals(request.getPassword())) {
            throw new CustomerNotFoundException("Passwords did not match");
        }
        LoginCustomerResponse response = LoginCustomerResponse.builder()
                .token("Random_token")
                .build();
        return response;

    }

    public GetCustomerResponse getById(Integer id) throws CustomerNotFoundException {
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(id + " not found");
        }
        GetCustomerResponse response = modelMapper.map(customer.get(), GetCustomerResponse.class);
        return response;
    }

    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;
}
// Testing solutions
// @DataJpaTest -> only for repos
// @SpringBootTest -> for general spring testing. but very slow

