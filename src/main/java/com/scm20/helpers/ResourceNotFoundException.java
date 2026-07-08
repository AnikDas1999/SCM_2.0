package com.scm20.helpers;

public class ResourceNotFoundException extends RuntimeException {
    
    // Default constructor
    public ResourceNotFoundException() {
        super("Resource not found");
    }

    // 🔑 MAKE SURE YOU HAVE THIS CONSTRUCTOR:
    public ResourceNotFoundException(String message) {
        super(message);
    }
}