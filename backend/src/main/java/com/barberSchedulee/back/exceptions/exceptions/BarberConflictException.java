package com.barberSchedulee.back.exceptions.exceptions;


public class BarberConflictException extends RuntimeException {
    public BarberConflictException(String message) {
        super(message);
    }
}