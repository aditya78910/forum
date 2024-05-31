package com.forums.api.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse<T> {
    T data;
    private String message;
    private String description;
}
