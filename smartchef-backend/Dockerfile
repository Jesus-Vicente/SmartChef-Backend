# ETAPA 1: Construcción
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos archivos de configuración
COPY . .
# Forzamos la descarga de dependencias
RUN ./mvn clean package -DskipTests

# Copiamos código y compilamos

# ETAPA 2: Ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app

# Usamos un comodín más seguro para copiar el JAR
COPY --from=build /app/target/*.jar app.jar

# Obligatorio para Render
EXPOSE 8080

# Ajuste de memoria y puerto para evitar errores silenciosos
ENTRYPOINT ["java", "-jar", "app.jar"]