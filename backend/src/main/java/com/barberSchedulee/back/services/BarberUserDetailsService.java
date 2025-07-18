package com.barberSchedulee.back.services;

import com.barberSchedulee.back.Repository.BarberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarberUserDetailsService implements UserDetailsService {

    private final BarberRepository barberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return barberRepository.findByEmail(email)
            .map(barber -> new org.springframework.security.core.userdetails.User(
                barber.getEmail(),
                barber.getPassword(),
                List.of(new SimpleGrantedAuthority(barber.getRole()))
            ))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }
}
