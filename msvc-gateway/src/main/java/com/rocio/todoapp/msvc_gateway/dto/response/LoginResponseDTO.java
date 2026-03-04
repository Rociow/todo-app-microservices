package com.rocio.todoapp.msvc_gateway.dto.response;

import com.rocio.todoapp.msvc_gateway.dto.Role;

public record LoginResponseDTO(
        String token,
        String email,
        String role,
        boolean success,
        String error
){
}
