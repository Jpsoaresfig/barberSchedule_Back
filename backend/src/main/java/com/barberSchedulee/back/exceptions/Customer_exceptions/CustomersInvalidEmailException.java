package com.barberSchedulee.back.exceptions.Customer_exceptions;

public class CustomersInvalidEmailException extends RuntimeException {
    public CustomersInvalidEmailException(String message) {
        super(message);
    }
}