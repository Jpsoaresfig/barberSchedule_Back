package com.barberSchedulee.back.exceptions.Customer_exceptions;

public class CustomersInvalidPasswordException extends RuntimeException {
    public CustomersInvalidPasswordException(String message) {
        super(message);
    }
}
