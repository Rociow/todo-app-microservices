package com.todoapp.msvc_user.dto.request;

public record UserRequestDTO (
    String name,
    String email,
    String password
){}
