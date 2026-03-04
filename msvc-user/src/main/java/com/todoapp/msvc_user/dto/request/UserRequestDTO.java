package com.todoapp.msvc_user.dto.request;

public record UserRequestDTO (
    String username,
    String email,
    String password
){}
