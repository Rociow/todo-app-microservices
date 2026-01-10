package com.todoapp.msvc_user.dto.response;

import com.todoapp.msvc_user.entity.Role;

public record LoginResponseDTO(
        String token,
        String email,
        Role role,
        boolean success,
        String error
) {
}
