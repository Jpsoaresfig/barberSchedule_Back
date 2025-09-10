package com.barberSchedulee.back.exceptions.Customer_exceptions;

public class PhoneAlreadyUsedException extends RuntimeException {
    public PhoneAlreadyUsedException(String message) {
        super(message);
    }
}
