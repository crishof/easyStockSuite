FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/user-sv-0.0.1.jar
COPY ${JAR_FILE} app_user.jar
LABEL authors="cristian"
EXPOSE 9014
ENTRYPOINT ["java", "-jar", "app_user.jar"]