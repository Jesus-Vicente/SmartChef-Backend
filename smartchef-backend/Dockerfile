# ETAPA 1: Construcción
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copiamos todo el contenido
COPY . .

# CORRECCIÓN: Damos permisos de ejecución al archivo mvnw
RUN chmod +x mvnw

# Ejecutamos el wrapper (fíjate que es ./mvnw, con la 'w' al final)
RUN ./mvnw clean package -DskipTests

# ETAPA 2: Ejecución
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el JAR generado
COPY --from=build /app/target/*.jar app.jar

# Puerto para Render
EXPOSE 8080

# Ejecución optimizada para el puerto dinámico de Render
ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-Xmx512m", "-jar", "app.jar"]