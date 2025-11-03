# ===== Stage 1: Build the application =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -q -B clean package -DskipTests

# ===== Stage 2: Run the application =====
FROM eclipse-temurin:17-jre
WORKDIR /phalanx
COPY --from=build /app/target/*-SNAPSHOT.jar phalanx.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "phalanx.jar"]