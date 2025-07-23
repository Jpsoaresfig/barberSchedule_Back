package com.barberSchedulee.back.services.barberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.barberSchedulee.back.DTO.TokenResponseDTO;
import com.barberSchedulee.back.DTO.barberDTO.LoginBarberDTO;
import com.barberSchedulee.back.DTO.barberDTO.RegisterBarberDTO;
import com.barberSchedulee.back.Entities.barber.Barber;
import com.barberSchedulee.back.Repository.BarberRepository;
import com.barberSchedulee.back.infra.security.TokenService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BarberService {

    private final BarberRepository barberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void register(RegisterBarberDTO dto) {
        if (barberRepository.findByEmail(dto.email()).isPresent()) {
            throw new IllegalArgumentException("Email já está em uso.");
        }

        if (dto.senha() == null || dto.senha().isBlank()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }

        if (dto.telefone() == null || dto.telefone().isBlank()) {
            throw new IllegalArgumentException("Telefone é obrigatório.");
        }

        if (!barberRepository.findByPhone(dto.telefone()).isEmpty()) {
            throw new IllegalArgumentException("Telefone já está em uso.");
        }
        Barber novo = new Barber();
        novo.setName(dto.nome());
        novo.setEmail(dto.email());
        novo.setPassword(passwordEncoder.encode(dto.senha()));
        novo.setRole("ROLE_BARBER");

        barberRepository.save(novo);
    }

    public ResponseEntity<TokenResponseDTO> login(LoginBarberDTO dto) {
        var barber = barberRepository.findByEmail(dto.email()).orElse(null);

        if (barber == null || !passwordEncoder.matches(dto.senha(), barber.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = User.builder()
                .username(barber.getEmail())
                .password(barber.getPassword())
                .authorities(new SimpleGrantedAuthority(barber.getRole()))
                .build();

        String token = tokenService.gerarToken(userDetails);
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
