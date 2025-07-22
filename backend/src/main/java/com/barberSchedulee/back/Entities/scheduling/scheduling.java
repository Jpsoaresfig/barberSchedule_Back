
package com.barberSchedulee.back.Entities.scheduling;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

import com.barberSchedulee.back.Entities.barber.Barber;
import com.barberSchedulee.back.Entities.customer.Customer;
import com.barberSchedulee.back.enums.Status;

import lombok.Data;

@Entity
@Table(name = "scheduling")
@Data
public class scheduling {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Customer customer;

  @ManyToOne
  private Barber barber;

  private LocalDate date;
  private LocalTime time;
  private String service;

  @Enumerated(EnumType.STRING)
  private Status status;
}