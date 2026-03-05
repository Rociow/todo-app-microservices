package com.todoapp.msvc_task.mapper;

import com.todoapp.msvc_task.dto.request.TaskRequestDTO;
import com.todoapp.msvc_task.dto.response.TaskResponseDTO;
import com.todoapp.msvc_task.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskResponseDTO toDto(Task task);
    @Mapping(target = "id", ignore = true)
    Task toEntity(TaskRequestDTO dto);
}

