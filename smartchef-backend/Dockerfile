# ETAPA 1: Construcción
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos archivos de configuración
COPY pom.xml .
# Forzamos la descarga de dependencias
RUN mvn dependency:go-offline -B

# Copiamos código y compilamos
COPY src ./src
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Usamos un comodín más seguro para copiar el JAR
COPY --from=build /app/target/*.jar app.jar

# Obligatorio para Render
EXPOSE 8080

# Ajuste de memoria y puerto para evitar errores silenciosos
ENTRYPOINT ["java", "-Xmx512m", "-Dserver.port=${PORT:8080}", "-jar", "app.jar"]