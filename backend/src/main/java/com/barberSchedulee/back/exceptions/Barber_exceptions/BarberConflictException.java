package com.barberSchedulee.back.exceptions.Barber_exceptions;


public class BarberConflictException extends RuntimeException {
    public BarberConflictException(String message) {
        super(message);
    }
}