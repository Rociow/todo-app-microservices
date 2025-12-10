package com.todoapp.msvc_notification.mapper;

import com.todoapp.msvc_notification.dto.request.NotificationRequestDTO;
import com.todoapp.msvc_notification.dto.response.NotificationResponseDTO;
import com.todoapp.msvc_notification.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationResponseDTO toDto(Notification notification);
    Notification toEntity(NotificationRequestDTO dto);
}

