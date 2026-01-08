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

    @GetMapping
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        return ResponseEntity.ok(service.getAllNotifications());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<NotificationResponseDTO>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserNotifications(userId));
    }

    @PostMapping
    public ResponseEntity<NotificationResponseDTO> create(@RequestParam("userId") Long userId,
                                                  @RequestParam("message") String message) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createNotification(userId, message));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable String id) {
        service.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}

