package com.barberSchedulee.back.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barberSchedulee.back.Entities.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
