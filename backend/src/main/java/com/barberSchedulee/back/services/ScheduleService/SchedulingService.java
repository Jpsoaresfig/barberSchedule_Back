package com.barberSchedulee.back.services.ScheduleService;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.barberSchedulee.back.DTO.scheduleDTO.CreateScheduleDTO;
import com.barberSchedulee.back.Entities.barber.Barber;
import com.barberSchedulee.back.Entities.customer.Customer;
import com.barberSchedulee.back.Entities.scheduling.scheduling;
import com.barberSchedulee.back.Repository.BarberRepository;
import com.barberSchedulee.back.Repository.CustomerRepository;
import com.barberSchedulee.back.Repository.SchedulingRepository;
import com.barberSchedulee.back.enums.Status;
import com.barberSchedulee.back.exceptions.Schedule_exceptions.ScheduleDoesntExistBarberException;
import com.barberSchedulee.back.exceptions.Schedule_exceptions.ScheduleDoesntExistCustomerException;
import com.barberSchedulee.back.exceptions.Schedule_exceptions.ScheduleSameTimeException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final BarberRepository barberRepo;
    private final CustomerRepository customerRepo;
    private final SchedulingRepository schedulingRepo;

    public scheduling create(CreateScheduleDTO dto) {

        LocalDate date = LocalDate.parse(dto.date());
        LocalTime time = LocalTime.parse(dto.time());

        if (schedulingRepo.existsByBarberIdAndDateAndTime(dto.barberId(), date, time)) {
            throw new ScheduleSameTimeException("Já existe um agendamento para este barbeiro na data e hora informadas.");
        }


        Barber barber = barberRepo.findById(dto.barberId())
            .orElseThrow(() -> new ScheduleDoesntExistBarberException("Barber não encontrado"));

        Customer customer = customerRepo.findById(dto.customerId())
            .orElseThrow(() -> new ScheduleDoesntExistCustomerException("Customer não encontrado"));

        Status status = Status.valueOf(dto.status().toUpperCase());

        scheduling sched = new scheduling();
        sched.setBarber(barber);
        sched.setCustomer(customer);
        sched.setDate(date);
        sched.setTime(time);
        sched.setService(dto.service());
        sched.setStatus(status);

        return schedulingRepo.save(sched);
    }

}
