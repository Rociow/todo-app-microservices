package com.todoapp.msvc_notification.dto.request;

public record NotificationRequestDTO(
        String id,
    String message,
    Long userId
) {
}
