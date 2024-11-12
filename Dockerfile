FROM openjdk:24-jdk-oracle AS base
WORKDIR /application

FROM maven:3.9-eclipse-temurin-22-alpine AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM base AS deploy
WORKDIR /application
COPY --from=build /build/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]