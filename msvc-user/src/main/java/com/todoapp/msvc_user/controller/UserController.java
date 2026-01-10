package com.todoapp.msvc_user.controller;

import com.todoapp.msvc_user.dto.request.UserRequestDTO;
import com.todoapp.msvc_user.dto.response.UserResponseDTO;
import com.todoapp.msvc_user.entity.User;
import com.todoapp.msvc_user.service.UserService;
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
     * Endpoint to get the authenticated user's details.
     * @return ResponseEntity<UserResponseDTO> containing the user's details.
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser() {
        UserResponseDTO user = userService.getAuthenticatedUser();
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
     * @param username
     * @param password
     * @return ResponseEntity<Boolean> indicating if the login was successful.
     */
    // Login (público, devuelve true/false por ahora)
    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String username,
                                         @RequestParam String password) {
        boolean success = userService.login(username, password);
        return ResponseEntity.ok(success);
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
