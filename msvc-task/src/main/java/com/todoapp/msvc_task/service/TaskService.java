package com.todoapp.msvc_task.service;

import com.todoapp.msvc_task.client.NotificationClient;
import com.todoapp.msvc_task.client.UserClient;
import com.todoapp.msvc_task.dto.request.TaskRequestDTO;
import com.todoapp.msvc_task.dto.response.TaskResponseDTO;
import com.todoapp.msvc_task.entity.Task;
import com.todoapp.msvc_task.mapper.TaskMapper;
import com.todoapp.msvc_task.repository.ITaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for managing tasks.
 */

@Service
public class TaskService {
    private final ITaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserClient userClient;
    private final NotificationClient notificationClient;

    public TaskService(ITaskRepository taskRepository, TaskMapper taskMapper, UserClient userClient, NotificationClient notificationClient) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userClient = userClient;
        this.notificationClient = notificationClient;
    }

    /**
     * Find all tasks.
     *
     * @return List of TaskResponseDTO
     */
    public List<TaskResponseDTO> findAll() {
        var tasks = taskRepository.findAll();
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    /**
     * Get a task by its ID.
     *
     * @param id Task ID
     * @return TaskResponseDTO
     */
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        return taskMapper.toDto(task);
    }

    /**
     * Get tasks by user ID.
     *
     * @param userId User ID
     * @return List of TaskResponseDTO
     */
    public List<TaskResponseDTO> getTasksByUserId(Long userId) {
        var tasks = taskRepository.findByUserId(userId);
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    /**
     * Create a new task.
     *
     * @param taskRequestDTO Task data
     * @return Created TaskResponseDTO
     */
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO){
        Task taskRequest = taskMapper.toEntity(taskRequestDTO);
        // llamar al User Service para validar que el userId existe
        if (!userClient.existsById(taskRequest.getUserId())) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Task task = new Task();
        task.setUserId(taskRequest.getUserId());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(taskRequest.isCompleted());
        Task saved = taskRepository.save(task); // ahora guarda entidad Task

        // enviar notificación al usuario
        try {
            String message = "Se ha creado una nueva tarea: " + saved.getDescription();
            notificationClient.createNotification(saved.getUserId(), message);
        } catch (Exception e) {
            // manejar error de notificación (por ejemplo, registrar el error)
            System.err.println("Error al enviar notificación: " + e.getMessage());
        }

        return taskMapper.toDto(saved);
    }

    /**
     * Update an existing task.
     *
     * @param id             Task ID
     * @param taskRequestDTO Updated task data
     * @return Updated TaskResponseDTO
     */
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        Task task = taskMapper.toEntity(taskRequestDTO);
        existingTask.setDescription(task.getDescription());
        existingTask.setCompleted(task.isCompleted());

        Task updatedTask = taskRepository.save(existingTask);
        return taskMapper.toDto(updatedTask);
    }

    /**
     * Delete a task by its ID.
     *
     * @param id Task ID
     */
    public void deleteTask(Long id) {
        if(!taskRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada");
        }
        taskRepository.deleteById(id);
    }
}
