package com.rocio.todoapp.msvc_gateway.dto.response;

import com.rocio.todoapp.msvc_gateway.dto.Role;

public record LoginResponse (
        String token,
        String email,
        Role role,
        boolean success,
        String error
){
}
