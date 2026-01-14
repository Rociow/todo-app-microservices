package com.rocio.todoapp.msvc_gateway.dto;

public record UserResponseDTO(
        Long id,
        String email,
        Role role
) {
}
