FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/api-gateway-0.0.1.jar
COPY ${JAR_FILE} api-gateway.jar
LABEL authors="cristian"
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]