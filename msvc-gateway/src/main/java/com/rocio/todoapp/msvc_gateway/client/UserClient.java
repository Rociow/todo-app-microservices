package com.rocio.todoapp.msvc_gateway.client;


import com.rocio.todoapp.msvc_gateway.dto.UserResponseDTO;
import com.rocio.todoapp.msvc_gateway.dto.request.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://user-service:8081", path = "/api/users")
public interface UserClient {

    @PostMapping("/validate")
    ResponseEntity<UserResponseDTO> validateCredentials(@RequestBody LoginRequest loginRequest);
}

