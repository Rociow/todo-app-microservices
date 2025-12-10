package com.todoapp.msvc_task.mapper;

import com.todoapp.msvc_task.dto.request.TaskRequestDTO;
import com.todoapp.msvc_task.dto.response.TaskResponseDTO;
import com.todoapp.msvc_task.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponseDTO toDto(Task task);
    Task toEntity(TaskRequestDTO dto);
}

