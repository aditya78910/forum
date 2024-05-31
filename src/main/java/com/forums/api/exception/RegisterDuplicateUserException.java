package com.forums.api.exception;

import lombok.Data;

@Data
public class RegisterDuplicateUserException extends RuntimeException{
    private ErrorResponse<String> errorResponse;
    public RegisterDuplicateUserException(ErrorResponse<String> errorResponse){
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}
