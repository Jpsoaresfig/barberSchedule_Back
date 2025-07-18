package com.barberSchedulee.back.controllers;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.customerDTO.LoginCustomerDTO;
import com.barberSchedulee.back.DTO.customerDTO.RegisterCustomerDTO;
import com.barberSchedulee.back.Entities.customer.Customer;
import com.barberSchedulee.back.Repository.CustomerRepository;
import com.barberSchedulee.back.infra.security.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/customer")
@RequiredArgsConstructor
public class AuthCustomerController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterCustomerDTO dto) {
        if (customerRepository.findByEmail(dto.email()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Customer novo = new Customer();
        novo.setName(dto.name());
        novo.setPhone(dto.phone());
        novo.setEmail(dto.email());
        novo.setPassword(passwordEncoder.encode(dto.password()));
        novo.setRole("ROLE_CUSTOMER");

        customerRepository.save(novo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

 @PostMapping("/login")
public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginCustomerDTO dto) {
    var customer = customerRepository.findByEmail(dto.email())
        .orElse(null);

    if (customer == null || !passwordEncoder.matches(dto.password(), customer.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    UserDetails userDetails = org.springframework.security.core.userdetails.User
        .withUsername(customer.getEmail())
        .password(customer.getPassword()) 
        .authorities(new SimpleGrantedAuthority(customer.getRole()))
        .build();

    String token = tokenService.gerarToken(userDetails);

    return ResponseEntity.ok(new TokenResponseDTO(token));
}
}
