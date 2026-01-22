# Usa una imagen base de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar generado por tu build de Spring Boot
COPY target/gateway-0.0.1-SNAPSHOT.jar app.jar

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]