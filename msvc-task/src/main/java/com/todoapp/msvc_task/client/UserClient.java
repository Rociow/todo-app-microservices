package com.todoapp.msvc_task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://user-service:8081", path = "/api/users")
public interface UserClient {
    @GetMapping("/exists/{id}")
    Boolean existsById(@PathVariable("id") Long id);
}

