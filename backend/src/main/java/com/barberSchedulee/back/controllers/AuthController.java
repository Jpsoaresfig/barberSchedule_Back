package com.barberSchedulee.back.controllers;

import org.springframework.security.core.Authentication;
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
import com.barberSchedulee.back.Entities.barber.Barber;

import com.barberSchedulee.back.DTO.LoginDTO;
import com.barberSchedulee.back.DTO.RegisterDTO;
import com.barberSchedulee.back.DTO.TokenResponseDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final BarberRepository barberRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginDTO dto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.email(),
                dto.senha());

        Authentication authentication = authenticationManager.authenticate(authToken);
        String token = tokenService.gerarToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO dto) {
        if (barberRepository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Barber novo = new Barber(dto.nome(), dto.email(), passwordEncoder.encode(dto.senha()), "ROLE_USER");
        barberRepository.save(novo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
