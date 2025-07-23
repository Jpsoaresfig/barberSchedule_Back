package com.barberSchedulee.back.Repository;

import com.barberSchedulee.back.Entities.barber.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {
    Optional<Barber> findByEmail(String email);
    List<Barber> findByPhone(String phone);
    
}
