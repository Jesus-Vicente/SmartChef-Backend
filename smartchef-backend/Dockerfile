# Etapa 1: Construcción (Build)
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos solo el pom.xml primero para descargar dependencias.
# Esto acelera futuros despliegues porque Docker cachea las librerías.
COPY pom.xml .
RUN mvn dependency:go-offline

# Ahora copiamos el código fuente y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Usar el asterisco *.jar es buena idea si no quieres escribir el nombre completo,
# pero asegúrate de que solo haya un archivo .jar en target
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto
EXPOSE 8080

# Usamos la variable de entorno PORT que Render inyecta automáticamente
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-Xmx512m", "-jar", "app.jar"]