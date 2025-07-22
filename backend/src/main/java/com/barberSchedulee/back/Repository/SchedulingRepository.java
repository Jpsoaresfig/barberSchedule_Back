package com.barberSchedulee.back.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.data.jpa.repository.JpaRepository;
import com.barberSchedulee.back.Entities.scheduling.scheduling;

public interface SchedulingRepository extends JpaRepository<scheduling, Long> {
    boolean existsByBarberIdAndDateAndTime(Long barberId, LocalDate date, LocalTime time);
}
