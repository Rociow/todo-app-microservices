package com.todoapp.msvc_notification.service;

import com.todoapp.msvc_notification.dto.response.NotificationResponseDTO;
import com.todoapp.msvc_notification.entity.Notification;
import com.todoapp.msvc_notification.mapper.NotificationMapper;
import com.todoapp.msvc_notification.repository.INotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final INotificationRepository repository;
    private final NotificationMapper mapper;

    public NotificationService(INotificationRepository repository, NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<NotificationResponseDTO> getAllNotifications() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public NotificationResponseDTO createNotification(Long userId, String message) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);

        Notification saved = repository.save(notification);

        return new NotificationResponseDTO(saved.getId(), saved.getMessage(), saved.getUserId());

    }

    public List<NotificationResponseDTO> getUserNotifications(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public void markAsRead(String id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setRead(true);
        repository.save(notification);
    }
}

