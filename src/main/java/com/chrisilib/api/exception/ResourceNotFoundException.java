package com.chrisilib.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is thrown when a requested resource is not found in the database.
 * The @ResponseStatus annotation causes Spring Boot to respond with the specified
 * HTTP status code (404 NOT_FOUND) whenever this exception is thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}