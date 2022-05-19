package com.example.cinemacrud.exeptions;


public class ResourceNotDeletedException extends RuntimeException {
    public ResourceNotDeletedException(String message) {
        super(message);
    }
}
