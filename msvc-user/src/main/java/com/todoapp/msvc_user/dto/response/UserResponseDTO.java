package com.todoapp.msvc_user.dto.response;

public record UserResponseDTO (
    Long id,
    String username,
    String email,
    String role
){}
