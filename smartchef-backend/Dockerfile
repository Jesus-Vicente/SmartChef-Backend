# Etapa 1: Construcción (Build)
# Usamos una imagen de Maven con JDK 21 para compilar
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el pom.xml para descargar las dependencias primero (aprovecha la caché de Docker)
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Ejecución (Runtime)
# Usamos una imagen ligera de JRE 21 para correr la app
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copiamos el archivo .jar generado en la etapa anterior
# El nombre suele ser el artifactId-version.jar definido en el pom.xml
COPY --from=build /app/target/*.jar app.jar

# Render asigna automáticamente un puerto, le decimos a Spring que lo use
EXPOSE 8080

# Comando para ejecutar la aplicación
# Usamos parámetros de optimización de memoria para que no falle en el plan gratuito de Render
ENTRYPOINT ["java", "-jar", "app.jar"]