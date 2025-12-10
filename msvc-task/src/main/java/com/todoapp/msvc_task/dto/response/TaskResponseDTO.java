package com.todoapp.msvc_task.dto.response;

public record TaskResponseDTO(
        Long id,
        String description,
        boolean completed,
        Long userId
) {
}
