package com.todoapp.msvc_task.client;

import com.todoapp.msvc_task.dto.response.NotificationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-notification", path = "/api/notifications")
public interface NotificationClient {
    @PostMapping
    NotificationResponseDTO createNotification(@RequestParam("userId") Long userId,
                                               @RequestParam("message") String message);

}
