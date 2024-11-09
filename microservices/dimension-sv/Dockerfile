FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/dimension-sv-0.0.1.jar
COPY ${JAR_FILE} app_dimension.jar
LABEL authors="cristian"
EXPOSE 9011
ENTRYPOINT ["java", "-jar", "app_dimension.jar"]