package com.rocio.todoapp.msvc_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
//protege todo el sistema
/**
 * Es la configuración de Spring Security en el API Gateway.
 * Su trabajo es:
 * Leer el JWT que llega en cada request.
 * Validarlo.
 * Decidir si la request puede pasar o no según el rol y reglas.
 * Si la request pasa → se reenvía al microservicio correspondiente.
 */
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter; //lee y valida el token en cada request
    private final CustomAccessDeniedHandler customAccessDeniedHandler; //maneja errores de acceso denegado

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        /**
                         *  No uses sesiones ni cookies.
                         * El usuario no queda logueado en el servidor.
                         * Cada request debe traer su token.
                         */
                )
                //reglas de autorizacion, le dice que rutas puede usar cada rol
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sin autenticación)
                        .requestMatchers(
                                "/api/auth/**",
                                "/actuator/**"
                        ).permitAll()

                        // Solo rol ADMIN para todos estos métodos y rutas
                        .requestMatchers(
                                "/api/reportes/**",
                                "/api/tarifas/{id}/admin/{idAdmin}", //p
                                "/api/usuarios/monopatines/administracion/{idAdmin}/reporte-uso",
                                "/api/cuentas/{id}/administraccion/{idAdmin}/habilitar",
                                "/api/cuentas/{id}/administraccion/{idAdmin}/deshabilitar"
                        ).hasRole("ADMIN")

                        // Solo el POST de /api/tarifas requiere rol ADMIN
                        .requestMatchers(HttpMethod.POST, "/api/tarifas").hasRole("ADMIN") //p

                        // El resto de métodos en /api/tarifas (GET, PUT, etc.) públicos o
                        // si preferís solo autenticados:
                        .requestMatchers("/api/tarifas").permitAll()//p

                        // Todos los demás requieren autenticación
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                /**
                 * Tu filtro hace esto:
                 * Lee el token del header.
                 * Lo valida (firma, expiración, formato).
                 * Si es válido → carga los claims (email, rol, etc.) en el contexto de seguridad de Spring
                 */
                .build();
    }
}
