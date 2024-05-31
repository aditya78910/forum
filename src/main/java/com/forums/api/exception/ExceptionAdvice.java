package com.forums.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ RegisterDuplicateUserException.class })
    public ResponseEntity<ErrorResponse<String>> handleRegisterDuplicateUserException(
            RegisterDuplicateUserException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.I_AM_A_TEAPOT);
    }

    @ExceptionHandler({ AuthenticationFailedException.class })
    public ResponseEntity<ErrorResponse<String>> handleAuthenticationException(
            AuthenticationFailedException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ErrorResponse<List<String>>> handleNotFoundException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ErrorResponse<String>> handleGenericException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
