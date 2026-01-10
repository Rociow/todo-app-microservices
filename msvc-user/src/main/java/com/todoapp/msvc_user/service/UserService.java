package com.todoapp.msvc_user.service;

import com.todoapp.msvc_user.config.JwtUtil;
import com.todoapp.msvc_user.dto.request.LoginRequestDTO;
import com.todoapp.msvc_user.dto.request.UserRequestDTO;
import com.todoapp.msvc_user.dto.response.LoginResponseDTO;
import com.todoapp.msvc_user.dto.response.UserResponseDTO;
import com.todoapp.msvc_user.entity.User;
import com.todoapp.msvc_user.mapper.UserMapper;
import com.todoapp.msvc_user.repository.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Con registro, login, perfil, cambio de contraseña, CRUD usuarios (admin)
 */

@Service
public class UserService{

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntity(userRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        // encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User usuarioNuevo = userRepository.save(user);
        return userMapper.toDto(usuarioNuevo);
    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
        String email = loginRequestDTO.email();
        String rawPassword = loginRequestDTO.password();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            // credenciales válidas → generar token
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole().toString());

            return new LoginResponseDTO(
                    token,
                    user.getEmail(),
                    user.getRole(),
                    true,
                    null // mensaje de error vacío
            );
        }

        // credenciales inválidas
        return new LoginResponseDTO(
                null,
                null,
                null,
                false,
                "Credenciales inválidas"
        );
    }

// Obtener perfil
    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Cambiar contraseña
    public void changePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return userMapper.toDto(user);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }

    public UserResponseDTO updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        // Si se proporciona una nueva contraseña, encriptarla
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        existingUser.setRole(updatedUser.getRole());
        existingUser.setActive(updatedUser.getActive());

        User savedUser = userRepository.save(existingUser);
        return userMapper.toDto(savedUser);
    }

    // Listar todos (solo admin)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }



}
