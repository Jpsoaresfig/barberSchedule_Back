package com.barberSchedulee.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.barberSchedulee.back.exceptions.exceptions.BarberConflictException;
import com.barberSchedulee.back.exceptions.exceptions.BarberInvalidPasswordException;
import com.barberSchedulee.back.exceptions.exceptions.BarberUserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BarberUserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(BarberUserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BarberInvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPassword(BarberInvalidPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BarberConflictException.class)
    public ResponseEntity<String> handleConflict(BarberConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Ocorreu um erro inesperado: " + ex.getMessage());
    }
}
