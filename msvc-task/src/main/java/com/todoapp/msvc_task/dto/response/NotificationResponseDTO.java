package com.todoapp.msvc_task.dto.response;

public record NotificationResponseDTO (
         Long id,
         String message,       // Mongo usa String como id
         Long userId
){}
