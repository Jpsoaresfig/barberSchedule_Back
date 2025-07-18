package com.barberSchedulee.back.infra.security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.barberSchedulee.back.Entities.barber.Barber;
import com.barberSchedulee.back.Entities.customer.Customer;
import com.barberSchedulee.back.Repository.BarberRepository;
import com.barberSchedulee.back.Repository.CustomerRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final CustomerRepository customerRepository;
    private final BarberRepository barberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        
        if (path.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = recuperarToken(request);

        if (token != null) {
            try {
                String email = tokenService.getSubject(token); 

                
                UsernamePasswordAuthenticationToken authentication = buildAuthentication(path, email);

                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
            
            }
        }

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken buildAuthentication(String path, String email) {
        if (path.startsWith("/barber")) {
            
            return barberRepository.findByEmail(email)
                    .map(this::authFromBarber)
                    .orElseGet(() -> customerRepository.findByEmail(email)
                            .map(this::authFromCustomer)
                            .orElse(null));
        } else {
            
            return customerRepository.findByEmail(email)
                    .map(this::authFromCustomer)
                    .orElseGet(() -> barberRepository.findByEmail(email)
                            .map(this::authFromBarber)
                            .orElse(null));
        }
    }

    private UsernamePasswordAuthenticationToken authFromCustomer(Customer c) {
        var authorities = List.of(new SimpleGrantedAuthority(nullSafeRole(c.getRole(), "ROLE_CUSTOMER")));
        return new UsernamePasswordAuthenticationToken(c.getEmail(), null, authorities);
    }

    private UsernamePasswordAuthenticationToken authFromBarber(Barber b) {
        var authorities = List.of(new SimpleGrantedAuthority(nullSafeRole(b.getRole(), "ROLE_BARBER")));
        return new UsernamePasswordAuthenticationToken(b.getEmail(), null, authorities);
    }

    private String nullSafeRole(String role, String fallback) {
        return (role == null || role.isBlank()) ? fallback : role;
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); 
        }
        return null;
    }
}
