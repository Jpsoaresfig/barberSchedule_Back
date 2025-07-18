package com.barberSchedulee.back.DTO.customerDTO;

public record RegisterCustomerDTO(
    String name,
    String email,
    String password,
    String phone,
    String role
) {
    public RegisterCustomerDTO {
        if (role == null) {
            role = "ROLE_CUSTOMER";
        }
    }
}
