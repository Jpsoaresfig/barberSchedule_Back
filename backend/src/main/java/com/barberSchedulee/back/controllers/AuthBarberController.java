package com.barberSchedulee.back.controllers;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.barberSchedulee.back.infra.security.TokenService;
import com.barberSchedulee.back.services.barberService.BarberService;
import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.barberDTO.LoginBarberDTO;
import com.barberSchedulee.back.DTO.barberDTO.RegisterBarberDTO;
import com.barberSchedulee.back.Repository.BarberRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/auth/barber")
@RequiredArgsConstructor
public class AuthBarberController {

    private final TokenService tokenService;
    private final BarberRepository barberRepository;
    private final PasswordEncoder passwordEncoder;
    private final BarberService barberService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterBarberDTO dto) {
        barberService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginBarberDTO dto) {
        var barber = barberRepository.findByEmail(dto.email())
                .orElse(null);

        if (barber == null || !passwordEncoder.matches(dto.senha(), barber.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var userDetails = User
                .withUsername(barber.getEmail())
                .password(barber.getPassword())
                .authorities(new SimpleGrantedAuthority(barber.getRole()))
                .build();

        String token = tokenService.gerarToken(userDetails);
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
