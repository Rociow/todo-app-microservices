package com.todoapp.msvc_user.dto.interservice;

import com.todoapp.msvc_user.entity.Role;

public record UserResponseValidated(
        Long id,
        String email,
        Role role
) {
}
