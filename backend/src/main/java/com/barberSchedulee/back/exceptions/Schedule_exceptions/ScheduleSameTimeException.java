package com.barberSchedulee.back.exceptions.Schedule_exceptions;

public class ScheduleSameTimeException extends RuntimeException{
    
    public ScheduleSameTimeException(String message) {
        super(message);
    }
    
}
