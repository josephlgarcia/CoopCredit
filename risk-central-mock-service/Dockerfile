# Stage 1: Build with Maven and JDK 17
FROM maven:3.9.0-eclipse-temurin-17 AS build

WORKDIR /app

# Copiar pom.xml para aprovechar el cache de dependencias
COPY pom.xml .

# Descargar las dependencias para cachearlas
RUN mvn dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Construir el paquete (omitimos tests para acelerar el proceso)
RUN mvn clean package -DskipTests

# Stage 2: Imagen final con JRE Alpine de Temurin 17 (más ligera)
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar el archivo .jar de la etapa de build
COPY --from=build /app/target/risk-central-mock-service-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8081 (ya que el servicio usa este puerto)
EXPOSE 8081

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
