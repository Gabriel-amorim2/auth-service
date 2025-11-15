FROM maven:3.9.5-eclipse-temurin-17 AS builder

COPY src app/src
COPY pom.xml /app

WORKDIR /app
RUN mvn clean install

FROM openjdk:17-jdk-slim
COPY --from=builder /app/target/bankingPlatform-0.0.1-SNAPSHOT.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]