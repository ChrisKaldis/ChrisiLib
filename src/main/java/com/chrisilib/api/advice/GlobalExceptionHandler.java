package com.chrisilib.api.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.chrisilib.api.exception.ResourceNotFoundException;


/**
 * A centralized exception handler for the entire REST API.
 * This class uses @RestControllerAdvice to intercept exceptions thrown from controllers
 * and formats them into a consistent, client-friendly JSON response.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions triggered by the @Valid annotation.
     *
     * @param ex The caught MethodArgumentNotValidException.
     * @return A map of field names to their corresponding validation error messages.
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return errors;
    }

    /**
     * Handles resource not found exceptions, typically for 404 errors.
     *
     * @param ex The caught ResourceNotFoundException.
     * @return A map containing a user-friendly error message.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        
        errorMap.put("message", ex.getMessage());

        return errorMap;
    }

}
