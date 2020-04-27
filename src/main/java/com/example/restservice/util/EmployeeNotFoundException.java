package com.example.restservice.util;


public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Could not found employee " + id);
    }
}
