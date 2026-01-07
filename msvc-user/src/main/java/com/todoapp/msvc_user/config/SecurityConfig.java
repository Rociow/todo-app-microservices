package com.todoapp.msvc_user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean //Esto lo registra en el ApplicationContext de Spring, y cualquier clase puede pedirlo por inyección.
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Configuración básica de seguridad para permitir todas las requests sin autenticación
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // nueva forma de desactivar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // permite todas las requests
                );
        return http.build();

    }

}
