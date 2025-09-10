package com.barberSchedulee.back.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.customerDTO.LoginCustomerDTO;
import com.barberSchedulee.back.DTO.customerDTO.RegisterCustomerDTO;
import com.barberSchedulee.back.exceptions.Customer_exceptions.CustomersInvalidDataException;
import com.barberSchedulee.back.services.customerService.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth/customer")
public class AuthCustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody @Valid RegisterCustomerDTO dto) {
        customerService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer registered successfully.");
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody @Valid LoginCustomerDTO dto) throws CustomersInvalidDataException {
        return customerService.login(dto);
    }

}
