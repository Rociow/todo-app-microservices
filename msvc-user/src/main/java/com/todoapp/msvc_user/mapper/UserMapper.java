package com.todoapp.msvc_user.mapper;

import com.todoapp.msvc_user.dto.request.UserRequestDTO;
import com.todoapp.msvc_user.dto.response.UserResponseDTO;
import com.todoapp.msvc_user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDto(User user);
    User toEntity(UserResponseDTO dto);
    UserRequestDTO toRequestDto(User user);
    User toEntityFromRequestDto(UserRequestDTO dto);
}
