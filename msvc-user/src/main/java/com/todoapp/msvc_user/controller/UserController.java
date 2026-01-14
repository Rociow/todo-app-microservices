package com.todoapp.msvc_user.controller;

import com.todoapp.msvc_user.dto.request.LoginRequestDTO;
import com.todoapp.msvc_user.dto.request.UserRequestDTO;
import com.todoapp.msvc_user.dto.response.LoginResponseDTO;
import com.todoapp.msvc_user.dto.response.UserResponseDTO;
import com.todoapp.msvc_user.entity.User;
import com.todoapp.msvc_user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users.
 */

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to check if a user exists by ID.
     * @param id
     * @return ResponseEntity<Boolean> indicating if the user exists.
     */
    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user != null);
    }

    /**
     * Endpoint to validate user credentials.
     * @param loginRequestDTO
     * @return ResponseEntity<UserResponseDTO> containing the validated user's details.
     */
    @GetMapping("/validate")
    public ResponseEntity<LoginResponseDTO> validateCredentials(LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO user = userService.authenticate(loginRequestDTO);
        return ResponseEntity.ok(user);
    }

    /**
     * Endpoint to register a new user.
     * @param userDto
     * @return ResponseEntity<UserResponseDTO> containing the registered user's details.
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO userDto) {
        UserResponseDTO user = userService.registerUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Endpoint to log in a user.
     */
    // Login (genera JWT)
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = userService.authenticate(request);
        if (response.success()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * Endpoint to retrieve all users (admin only).
     * @return ResponseEntity<List<UserResponseDTO>> containing the list of all users.
     */
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        UserResponseDTO user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Long id, @RequestParam String newPassword) {
        UserResponseDTO user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.changePassword(id, newPassword);
        return ResponseEntity.noContent().build();
        }

}
