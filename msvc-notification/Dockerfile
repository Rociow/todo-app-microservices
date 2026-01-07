# Usa una imagen base de Java 17
FROM eclipse-temurin:17-jdk-alpine

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el jar generado por Maven al contenedor
COPY target/msvc-notification-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto definido en application.properties
EXPOSE 8082

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]