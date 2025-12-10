package com.todoapp.msvc_notification.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
public class Notification {
    @Id
    private String id;

    private Long userId;
    private String message;
    private LocalDateTime createdAt;
    private boolean read;
}
