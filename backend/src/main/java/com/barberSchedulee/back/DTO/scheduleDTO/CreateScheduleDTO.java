package com.barberSchedulee.back.DTO.scheduleDTO;

public record CreateScheduleDTO(
    Long barberId,
    Long customerId,
    String date,
    String time,
    String service,
    String status
) {}
