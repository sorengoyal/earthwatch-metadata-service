package com.earthwatch.metadata.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class GetCustomerResponse {
    private Integer id;
    private String username;
    private String email;
}
