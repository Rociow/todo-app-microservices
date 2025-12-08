package com.todoapp.msvc_task.dto;

public record TaskDTO(
         Long id,
         String description,
         boolean completed,
         Long userId

) {
}
