package com.forums.api.exception;

import lombok.Data;

@Data
public class AuthenticationFailedException extends RuntimeException{
    private ErrorResponse<String> errorResponse;
    public AuthenticationFailedException(ErrorResponse<String> errorResponse){
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}