package com.barberSchedulee.back.exceptions.Barber_exceptions;

public class BarberInvalidPasswordException extends RuntimeException {
    public BarberInvalidPasswordException(String message) {
        super(message);
    }
}