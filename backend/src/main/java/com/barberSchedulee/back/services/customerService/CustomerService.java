package com.barberSchedulee.back.services.customerService;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.customerDTO.LoginCustomerDTO;
import com.barberSchedulee.back.DTO.customerDTO.RegisterCustomerDTO;
import com.barberSchedulee.back.Entities.customer.Customer;
import com.barberSchedulee.back.Repository.CustomerRepository;
import com.barberSchedulee.back.infra.security.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

   @Transactional
public void register(RegisterCustomerDTO dto) {
    if (!customerRepository.findByEmail(dto.email()).isEmpty()) {
        throw new IllegalArgumentException("Email já está em uso.");
    }
    if (!customerRepository.findByPhone(dto.phone()).isEmpty()) {
        throw new IllegalArgumentException("Telefone já está em uso.");
    }

    Customer novo = new Customer();
    novo.setName(dto.name());
    novo.setPhone(dto.phone());
    novo.setEmail(dto.email());
    novo.setPassword(passwordEncoder.encode(dto.password()));
    novo.setRole("ROLE_CUSTOMER");

    customerRepository.save(novo);
}

     public ResponseEntity<TokenResponseDTO> login(LoginCustomerDTO dto) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(dto.email());

        if (optionalCustomer.isEmpty() || !passwordEncoder.matches(dto.password(), optionalCustomer.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Customer customer = optionalCustomer.get();

        UserDetails userDetails = User.builder()
            .username(customer.getEmail())
            .password(customer.getPassword())
            .authorities(new SimpleGrantedAuthority(customer.getRole()))
            .build();

        String token = tokenService.gerarToken(userDetails);

        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}

