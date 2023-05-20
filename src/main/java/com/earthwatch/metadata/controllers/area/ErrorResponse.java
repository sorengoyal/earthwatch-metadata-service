package com.earthwatch.metadata.controllers.area;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @Getter
    @Setter
    private String message;
}