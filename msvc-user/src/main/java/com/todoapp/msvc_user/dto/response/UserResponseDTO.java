package com.todoapp.msvc_user.dto.response;

public record UserResponseDTO (
    Long id,
    String name,
    String email,
    String role
){}
