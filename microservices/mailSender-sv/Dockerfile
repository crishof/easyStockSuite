FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/mailSender-sv-0.0.1.jar
COPY ${JAR_FILE} app_mailSender.jar
LABEL authors="cristian"
EXPOSE 9013
ENTRYPOINT ["java", "-jar", "app_mailSender.jar"]