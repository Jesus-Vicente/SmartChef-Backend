# ETAPA 1: Construcción
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# ETAPA 2: Ejecución
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Al haber quitado el 'classifier' del POM, este nombre ahora sí es el correcto:
COPY --from=build /app/target/smartchef-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Dserver.port=${PORT:8080}", "-Xmx512m", "-jar", "app.jar"]