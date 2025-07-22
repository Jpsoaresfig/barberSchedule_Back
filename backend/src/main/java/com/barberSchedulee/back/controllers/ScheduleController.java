package com.barberSchedulee.back.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import com.barberSchedulee.back.DTO.scheduleDTO.CreateScheduleDTO;
import com.barberSchedulee.back.Entities.scheduling.scheduling;
import com.barberSchedulee.back.services.ScheduleService.SchedulingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    private final SchedulingService scheduleService;

    @PostMapping("/create")
    public ResponseEntity<scheduling> createSchedule(@RequestBody @Valid CreateScheduleDTO dto) {
        scheduling schedule = scheduleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
    }
      
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleConflict(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
