package com.todoapp.msvc_task.dto.request;

public record TaskRequestDTO(
        String description,
        boolean completed,
        Long userId
) {
}
