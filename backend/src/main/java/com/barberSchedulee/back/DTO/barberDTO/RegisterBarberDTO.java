package com.barberSchedulee.back.DTO.barberDTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterBarberDTO(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "Telefone é obrigatório") String telefone,
    @NotBlank(message = "Email é obrigatório") String email,
    @NotBlank(message = "Senha é obrigatória") String senha
) {}
