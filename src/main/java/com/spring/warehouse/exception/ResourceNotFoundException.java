package com.spring.warehouse.exception;

/**
 * @author gopal_re
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
