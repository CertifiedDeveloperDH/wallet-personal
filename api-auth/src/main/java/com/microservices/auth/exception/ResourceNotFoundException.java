package com.microservices.auth.exception;

public class ResourceNotFoundException  extends Exception{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
