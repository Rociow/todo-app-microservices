package com.todoapp.msvc_notification.dto.response;

public record NotificationResponseDTO (
    String id,
    String message,
    Long userId
){
}
