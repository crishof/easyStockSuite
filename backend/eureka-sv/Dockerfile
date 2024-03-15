FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/eureka-sv-0.0.1.jar
COPY ${JAR_FILE} app_eureka.jar
LABEL authors="cristian"
ENTRYPOINT ["java", "-jar", "app_eureka.jar"]