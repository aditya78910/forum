package com.forums.api.exception;

import java.util.List;

public class NotFoundException extends RuntimeException {
    private ErrorResponse<List<String>> errorResponse;
    public NotFoundException(ErrorResponse<List<String>> errorResponse){
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}
