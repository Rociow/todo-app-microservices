package com.todoapp.msvc_task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-user", url = "http://localhost:8081/api/users")
public interface UserClient {
    @GetMapping("/exists/{id}")
    public Boolean existsById(@PathVariable Long id);
}
