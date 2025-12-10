package com.todoapp.msvc_notification.dto.request;

public record NotificationRequestDTO(
    String message,
    Long userId
) {
}
