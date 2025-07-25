package com.barberSchedulee.back.DTO.barberDTO;



public record RegisterBarberDTO(
    String nome,
    String telefone,
    String email,
    String senha
) {}