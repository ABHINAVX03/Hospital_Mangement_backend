package com.codingshuttle.springboot0To100.hospitalManagementSystem.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resource, fieldName, fieldValue));
    }
}
