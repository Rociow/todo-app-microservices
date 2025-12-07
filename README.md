# ToDo App Microservices

**Descripción:** Backend multi-módulo con Spring Boot 3, JPA, Security, Actuator.
- **Módulos:** msvc-user, msvc-task, msvc-notification
- **Tecnologías:** Java 21, Spring Boot, JPA, Lombok, Docker (opcional)
- **Estado:** En desarrollo. Advertencias de seguridad monitoreadas y gestionadas vía BOM y overrides.

## Arquitectura
- **Padre (pom packaging):** Gestiona versiones con Spring Boot BOM.
- **Msvc-user:** Usuarios, roles, password hashing con BCrypt.
- **Msvc-task:** Tareas, relaciones con usuarios.
- **Msvc-notification:** Notificaciones (ej. MongoDB).

## Cómo correr
- Requiere Java 21.
- Desde IDE: run Application en cada módulo.
- Puertos: user: 8081, task: 8082, notification: 8083 (ejemplo).

## Seguridad
- Passwords con BCrypt.
- Warnings de dependencias se mitigan con overrides en `dependencyManagement`.

## Notas
- Estructura multi-módulo compatible con Maven/IntelliJ.
