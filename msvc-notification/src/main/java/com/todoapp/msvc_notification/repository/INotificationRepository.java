package com.todoapp.msvc_notification.repository;

import com.todoapp.msvc_notification.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface INotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByUserId(Long userId);
}
