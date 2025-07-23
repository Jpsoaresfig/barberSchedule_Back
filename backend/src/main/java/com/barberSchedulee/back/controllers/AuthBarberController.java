package com.barberSchedulee.back.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.barberSchedulee.back.infra.security.TokenService;

import jakarta.validation.Valid;

import com.barberSchedulee.back.Repository.BarberRepository;
import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.barberDTO.LoginBarberDTO;
import com.barberSchedulee.back.DTO.barberDTO.RegisterBarberDTO;
import com.barberSchedulee.back.Entities.barber.Barber;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/barber")
@RequiredArgsConstructor
public class AuthBarberController {

    private final TokenService tokenService;
    private final BarberRepository barberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginBarberDTO dto) {
    var barber = barberRepository.findByEmail(dto.email())
        .orElse(null);

    if (barber == null || !passwordEncoder.matches(dto.senha(), barber.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    
    UserDetails userDetails = org.springframework.security.core.userdetails.User
        .withUsername(barber.getEmail())
        .password(barber.getPassword()) 
        .authorities(new SimpleGrantedAuthority(barber.getRole()))
        .build();

    String token = tokenService.gerarToken(userDetails);

    return ResponseEntity.ok(new TokenResponseDTO(token));
}

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterBarberDTO dto) {
        if (barberRepository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Barber novo = new Barber(dto.nome(), dto.email(), passwordEncoder.encode(dto.senha()), "ROLE_USER");
        barberRepository.save(novo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
