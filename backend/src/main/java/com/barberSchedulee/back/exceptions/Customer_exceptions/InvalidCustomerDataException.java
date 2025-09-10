package com.barberSchedulee.back.exceptions.Customer_exceptions;

public class InvalidCustomerDataException extends RuntimeException {
    public InvalidCustomerDataException(String message) {
        super(message);
    }

}
