package com.todoapp.msvc_notification.dto.response;

public record NotificationResponseDTO (
    Long id,
    String message,
    Long userId
){
}
