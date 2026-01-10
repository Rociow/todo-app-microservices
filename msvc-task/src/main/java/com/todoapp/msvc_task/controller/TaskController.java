package com.todoapp.msvc_task.controller;

import com.todoapp.msvc_task.dto.request.TaskRequestDTO;
import com.todoapp.msvc_task.dto.response.TaskResponseDTO;
import com.todoapp.msvc_task.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing tasks.
 */

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Get all tasks.
     *
     * @return List of TaskResponseDTO
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks() {
        List<TaskResponseDTO> tasks = taskService.findAll();
        return ResponseEntity.ok(tasks);
    }

    /**
     * Get a task by its ID.
     *
     * @param id Task ID
     * @return TaskResponseDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Get tasks by user ID.
     *
     * @param userId User ID
     * @return List of TaskResponseDTO
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskResponseDTO>> getTasksByUserId(@PathVariable Long userId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Create a new task.
     *
     * @param taskRequestDTO Task data
     * @return Created TaskResponseDTO
     */
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
        return ResponseEntity.ok(createdTask);
    }

    /**
     * Update an existing task.
     *
     * @param id             Task ID
     * @param taskRequestDTO Updated task data
     * @return Updated TaskResponseDTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(id, taskRequestDTO);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Delete a task by its ID.
     *
     * @param id Task ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
