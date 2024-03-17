FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/image-sv-0.0.1.jar
COPY ${JAR_FILE} app-image.jar
LABEL authors="cristian"
ENTRYPOINT ["java", "-jar", "app-image.jar"]