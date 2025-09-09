package com.barberSchedulee.back.exceptions.exceptions;

public class BarberInvalidPasswordException extends RuntimeException {
    public BarberInvalidPasswordException(String message) {
        super(message);
    }
}