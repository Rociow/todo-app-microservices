package com.todoapp.msvc_task.mapper;

import com.todoapp.msvc_task.dto.TaskDTO;
import com.todoapp.msvc_task.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);
    Task toEntity(TaskDTO dto);
}

