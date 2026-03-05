package com.todoapp.msvc_notification.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "notifications")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    private String id;

    private Long userId;
    private String message;
    private LocalDateTime createdAt;
    private boolean read = false;

    @CreatedDate
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
