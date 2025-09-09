package com.barberSchedulee.back.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import com.barberSchedulee.back.services.barberService.BarberService;
import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.barberDTO.LoginBarberDTO;
import com.barberSchedulee.back.DTO.barberDTO.RegisterBarberDTO;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth/barber")
@RequiredArgsConstructor
public class AuthBarberController {

    private final BarberService barberService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterBarberDTO dto) {
        barberService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginBarberDTO dto) {
        String token = barberService.login(dto);
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
