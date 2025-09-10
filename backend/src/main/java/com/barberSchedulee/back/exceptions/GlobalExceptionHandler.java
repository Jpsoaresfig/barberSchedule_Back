package com.barberSchedulee.back.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.barberDTO.LoginBarberDTO;
import com.barberSchedulee.back.exceptions.Barber_exceptions.BarberEmailException;
import com.barberSchedulee.back.exceptions.Barber_exceptions.BarberInvalidPasswordException;

import jakarta.validation.Valid;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BarberInvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPassword(BarberInvalidPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(BarberEmailException.class)
    public ResponseEntity<String> handleConflict(BarberEmailException ex) {
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
