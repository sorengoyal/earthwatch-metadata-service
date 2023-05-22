package com.earthwatch.metadata.customer;

import com.earthwatch.metadata.customer.dto.CreateCustomerRequest;
import com.earthwatch.metadata.customer.dto.CreateCustomerResponse;
import com.earthwatch.metadata.customer.dto.GetCustomerResponse;
import com.earthwatch.metadata.customer.dto.LoginCustomerRequest;
import com.earthwatch.metadata.customer.dto.LoginCustomerResponse;
import com.earthwatch.metadata.customer.exceptions.CustomerNotFoundException;
import com.earthwatch.metadata.customer.exceptions.IncorrectPasswordException;
import com.earthwatch.metadata.entities.CustomerEntity;
import com.earthwatch.metadata.security.jwt.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    public CreateCustomerResponse create(CreateCustomerRequest request) {
        String password = request.getPassword();
        request.setPassword(passwordEncoder.encode(password));
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
        Boolean passwordsMatch = passwordEncoder.matches(request.getPassword(), customer.get().getPassword());
        if (!passwordsMatch) {
            throw new CustomerNotFoundException("Passwords did not match");
        }
        LoginCustomerResponse response = LoginCustomerResponse.builder()
                .jwtToken(jwtService.create(customer.get().getId()))
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

    private Boolean matchPassword(String passwordFromCustomer, String encodedPasswordFromDb) {
        return passwordEncoder.matches(passwordFromCustomer, encodedPasswordFromDb);

    }

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
}
// Testing solutions
// @DataJpaTest -> only for repos
// @SpringBootTest -> for general spring testing. but very slow

