package com.barberSchedulee.back.exceptions.Schedule_exceptions;

public class ScheduleDoesntExistBarberException extends RuntimeException{
    
    public ScheduleDoesntExistBarberException(String message) {
        super(message);
    }
    
}
