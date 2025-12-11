package com.todoapp.msvc_user.service;

import com.todoapp.msvc_user.dto.request.UserRequestDTO;
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

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        User user = userMapper.toEntityFromRequestDto(userRequestDTO);
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }

        // encriptar la contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User usuarioNuevo = userRepository.save(user);
        return userMapper.toDto(usuarioNuevo);
    }

    public boolean login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    // Obtener perfil
    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // Cambiar contraseña
    public void changePassword(String username, String newPassword) {
        User user = getProfile(username);
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
            throw new RuntimeException("Usuario no encontrado");
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
