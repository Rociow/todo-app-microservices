package com.todoapp.msvc_user.mapper;

import com.todoapp.msvc_user.dto.request.UserRequestDTO;
import com.todoapp.msvc_user.dto.response.UserResponseDTO;
import com.todoapp.msvc_user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(User user);
}
