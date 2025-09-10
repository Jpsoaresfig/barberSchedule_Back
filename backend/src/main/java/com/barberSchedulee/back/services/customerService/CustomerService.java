package com.barberSchedulee.back.services.customerService;

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
import com.barberSchedulee.back.exceptions.Customer_exceptions.CustomersInvalidEmailException;
import com.barberSchedulee.back.exceptions.Customer_exceptions.CustomersInvalidDataException;
import com.barberSchedulee.back.exceptions.Customer_exceptions.CustomersInvalidPasswordException;
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
            throw new CustomersInvalidEmailException("Email já está em uso.");
        }
        if (!customerRepository.findByPhone(dto.phone()).isEmpty()) {
            throw new CustomersInvalidDataException("Telefone já está em uso.");
        }
        if (dto.password() == null || dto.password().isBlank()) {
            throw new CustomersInvalidDataException("Senha não pode ser nula ou vazia.");
        }
        if (dto.name() == null || dto.name().isBlank()) {
            throw new CustomersInvalidDataException("Nome é obrigatório.");
        }
        if (dto.phone() == null || dto.phone().isBlank()) {
            throw new CustomersInvalidDataException("Telefone é obrigatório.");
        }
        if (dto.email() == null || dto.email().isBlank()) {
            throw new CustomersInvalidDataException("Email é obrigatório.");
        }

        Customer novo = new Customer();
        novo.setName(dto.name());
        novo.setPhone(dto.phone());
        novo.setEmail(dto.email());
        novo.setPassword(passwordEncoder.encode(dto.password()));
        novo.setRole("ROLE_CUSTOMER");

        customerRepository.save(novo);
    }

    public TokenResponseDTO login(LoginCustomerDTO dto) throws CustomersInvalidDataException {
        Customer customer = customerRepository.findByEmail(dto.email())
                .orElseThrow(() -> new CustomersInvalidEmailException("Email incorreto"));

        if (!passwordEncoder.matches(dto.password(), customer.getPassword())) {
            throw new CustomersInvalidPasswordException("senha incorretos");
        }

        UserDetails userDetails = User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .authorities(new SimpleGrantedAuthority(customer.getRole()))
                .build();

        String token = tokenService.gerarToken(userDetails);

        return new TokenResponseDTO(token);
    }
}
