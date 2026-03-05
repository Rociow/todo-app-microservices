package com.todoapp.msvc_notification.mapper;

import com.todoapp.msvc_notification.dto.request.NotificationRequestDTO;
import com.todoapp.msvc_notification.dto.response.NotificationResponseDTO;
import com.todoapp.msvc_notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationResponseDTO toDto(Notification notification);
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "read", ignore = true)
    Notification toEntity(NotificationRequestDTO dto);
}

