package com.todoapp.msvc_notification.service;

import com.todoapp.msvc_notification.dto.response.NotificationResponseDTO;
import com.todoapp.msvc_notification.entity.Notification;
import com.todoapp.msvc_notification.mapper.NotificationMapper;
import com.todoapp.msvc_notification.repository.INotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service class for managing notifications.
 */

@Service
public class NotificationService {

    private final INotificationRepository repository;
    private final NotificationMapper mapper;

    /**
     * Constructor for NotificationService.
     *
     * @param repository the notification repository
     * @param mapper     the notification mapper
     */
    public NotificationService(INotificationRepository repository, NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Creates a new notification for a user.
     *
     * @param userId  the ID of the user
     * @param message the notification message
     * @return the created notification as a DTO
     */
    public NotificationResponseDTO createNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        Notification saved = repository.save(notification);

        return new NotificationResponseDTO(saved.getId(), saved.getMessage(), saved.getUserId());
    }

    /**
     * Retrieves all notifications for a specific user.
     *
     * @param userId the ID of the user
     * @return a list of notification DTOs
     */
    public List<NotificationResponseDTO> getUserNotifications(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Marks a notification as read.
     *
     * @param id the ID of the notification
     */
    public void markAsRead(String id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setRead(true);
        repository.save(notification);
    }
}

