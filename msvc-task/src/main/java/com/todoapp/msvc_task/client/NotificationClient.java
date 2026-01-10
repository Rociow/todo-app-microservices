package com.todoapp.msvc_task.client;

import com.todoapp.msvc_task.dto.response.NotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign client for interacting with the Notification Service.
 */

@FeignClient(name = "notification-service", url = "http://notification-service:8082", path = "/api/notifications")
public interface NotificationClient {

    /**
     * Creates a new notification for a user.
     *
     * @param userId  the ID of the user to notify
     * @param message the notification message
     * @return the created NotificationResponseDTO
     */
    @PostMapping
    NotificationResponseDTO createNotification(@RequestParam("userId") Long userId,
                                               @RequestParam("message") String message);
}

