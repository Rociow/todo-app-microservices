package com.todoapp.msvc_task.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for interacting with the User Service.
 */

@FeignClient(name = "user-service", url = "http://user-service:8081", path = "/api/users")
public interface UserClient {

    /**
     * Checks if a user exists by their ID.
     *
     * @param id the ID of the user
     * @return true if the user exists, false otherwise
     */
    @GetMapping("/exists/{id}")
    Boolean existsById(@PathVariable("id") Long id);
}

