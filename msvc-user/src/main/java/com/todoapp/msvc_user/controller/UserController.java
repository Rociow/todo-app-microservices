package com.todoapp.msvc_user.controller;

import com.todoapp.msvc_user.dto.request.UserRequestDTO;
import com.todoapp.msvc_user.dto.response.UserResponseDTO;
import com.todoapp.msvc_user.entity.User;
import com.todoapp.msvc_user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//COMPLETAR LA CLASE UserController CON LOS SIGUIENTES ENDPOINTS:
// /me (GET, devuelve el usuario autenticado)

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user != null);
    }

    // Registro de usuario (público)
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userDto) {
        UserResponseDTO user = userService.registerUser(userDto);
        return ResponseEntity.ok(user);
    }

    // Login (público, devuelve true/false por ahora)
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String username,
                                         @RequestParam String password) {
        boolean success = userService.login(username, password);
        return ResponseEntity.ok(success);
    }

    // Listar todos los usuarios (solo ADMIN)
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }


}
