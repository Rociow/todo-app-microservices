package com.todoapp.msvc_notification.controller;

import com.todoapp.msvc_notification.dto.response.NotificationResponseDTO;
import com.todoapp.msvc_notification.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Controller for managing notifications.
 * Provides endpoints to create, retrieve, and update notifications.
 */

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId The ID of the user whose notifications are to be retrieved.
     * @return A list of NotificationResponseDTO objects.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserNotifications(userId));
    }

    /**
     * Creates a new notification for a user.
     *
     * @param userId  The ID of the user to whom the notification is to be sent.
     * @param message The message content of the notification.
     * @return The created NotificationResponseDTO object.
     */
    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestParam("userId") Long userId,
                                                  @RequestParam("message") String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createNotification(userId, message));
    }

    /**
     * Marks a notification as read.
     *
     * @param id The ID of the notification to be marked as read.
     * @return A ResponseEntity with no content.
     */
    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable String id) {
        service.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}

