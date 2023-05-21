package com.earthwatch.metadata.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CreateCustomerRequest {
    private String username;
    private String email;
    private String password;
}
