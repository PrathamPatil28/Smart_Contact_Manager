package com.example.SCM.Exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message ){
        super(message);
    }

    public ResourceNotFoundException(){
        super("Resource Not Found");
    }
}
