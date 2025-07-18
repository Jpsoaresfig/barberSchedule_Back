package com.barberSchedulee.back.services;

import com.barberSchedulee.back.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customerRepository.findByEmail(email)
            .map(customer -> new org.springframework.security.core.userdetails.User(
                customer.getEmail(),
                customer.getPassword(),  
                List.of(new SimpleGrantedAuthority(customer.getRole()))
            ))
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
    }
}
