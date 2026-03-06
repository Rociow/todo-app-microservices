package com.rocio.todoapp.msvc_gateway.config;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * Esta clase será reemplazada en un futuro por un microservicio Eureka Server, que se encargará de registrar los microservicios y hacer el balanceo de carga.
 * Por ahora, esta clase define manualmente las rutas para cada microservicio.
 * Cada ruta se asocia a un microservicio específico, y el API Gateway se encarga de enrutar las solicitudes entrantes a los microservicios correspondientes según el path de la URL
 */

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> userServiceRoute() {
        return GatewayRouterFunctions.route("user-service")
                .route(RequestPredicates.path("/api/users/**"),
                        HandlerFunctions.http("http://user-service:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskServiceRoute() {
        return GatewayRouterFunctions.route("task-service")
                .route(RequestPredicates.path("/api/tasks/**"),
                        HandlerFunctions.http("http://task-service:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoute() {
        return GatewayRouterFunctions.route("notification-service")
                .route(RequestPredicates.path("/api/notifications/**"),
                        HandlerFunctions.http("http://notification-service:8082"))
                .build();
    }
}