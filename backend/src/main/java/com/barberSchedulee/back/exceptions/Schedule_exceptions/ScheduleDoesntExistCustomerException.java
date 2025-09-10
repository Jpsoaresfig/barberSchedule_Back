package com.barberSchedulee.back.exceptions.Schedule_exceptions;

public class ScheduleDoesntExistCustomerException extends RuntimeException {
    
    public ScheduleDoesntExistCustomerException(String message) {
        super(message);
    }
}
