package com.todoapp.msvc_task.service;

import com.todoapp.msvc_task.dto.request.TaskRequestDTO;
import com.todoapp.msvc_task.dto.response.TaskResponseDTO;
import com.todoapp.msvc_task.entity.Task;
import com.todoapp.msvc_task.mapper.TaskMapper;
import com.todoapp.msvc_task.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskService(ITaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskResponseDTO> findAll() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        return taskMapper.toDto(task);
    }

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO){
        Task taskRequest = taskMapper.toEntity(taskRequestDTO);
        Task task = new Task();
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        var saved = taskRepository.save(task); // ahora guarda entidad Task
        return taskMapper.toDto(saved);    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        Task task = taskMapper.toEntity(taskRequestDTO);
        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.isCompleted());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }

    public void deleteTask(Long id) {
        if(!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada");
        }
        taskRepository.deleteById(id);
    }
}
