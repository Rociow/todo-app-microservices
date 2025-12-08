package com.todoapp.msvc_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean //Esto lo registra en el ApplicationContext de Spring, y cualquier clase puede pedirlo por inyección.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
