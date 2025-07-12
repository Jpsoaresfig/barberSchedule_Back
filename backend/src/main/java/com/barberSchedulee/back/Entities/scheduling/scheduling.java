
package com.barberSchedulee.back.Entities.scheduling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;

import com.barberSchedulee.back.Entities.barber.Barber;
import com.barberSchedulee.back.Entities.customer.Customer;
import lombok.Data;

@Entity
@Data
public class scheduling {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Customer customer;

  @ManyToOne
  private Barber barber;

  private LocalDate data;
  private LocalTime horario;
  private String servico;
  private String status;
}