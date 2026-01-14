package com.rocio.todoapp.msvc_gateway.controller;


import com.rocio.todoapp.msvc_gateway.dto.request.LoginRequest;
import com.rocio.todoapp.msvc_gateway.dto.response.LoginResponse;
import com.rocio.todoapp.msvc_gateway.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        // llamo al servicio de autenticacion
        LoginResponse response = authService.authenticate(request);

        if (response.success()) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(response);
    }
}
