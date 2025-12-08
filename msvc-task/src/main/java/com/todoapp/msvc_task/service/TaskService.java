package com.todoapp.msvc_task.service;

import com.todoapp.msvc_task.dto.TaskDTO;
import com.todoapp.msvc_task.entity.Task;
import com.todoapp.msvc_task.mapper.TaskMapper;
import com.todoapp.msvc_task.repository.ITaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(ITaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public ResponseEntity<TaskDTO> getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        TaskDTO taskDTO = taskMapper.toDto(task);
        return ResponseEntity.ok(taskDTO);
    }

    public ResponseEntity<TaskDTO> createTask(Task task){
        Task savedTask = taskRepository.save(task);

        TaskDTO savedTaskDTO = taskMapper.toDto(savedTask);
        return ResponseEntity.ok(savedTaskDTO);
    }

    public ResponseEntity<TaskDTO> updateTask(Long id, Task task) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.isCompleted());

        Task updatedTask = taskRepository.save(existingTask);
        TaskDTO updatedTaskDTO = taskMapper.toDto(updatedTask);
        return ResponseEntity.ok(updatedTaskDTO);
    }
}
