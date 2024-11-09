FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/branch-sv-0.0.1.jar
COPY ${JAR_FILE} app_branch.jar
LABEL authors="cristian"
EXPOSE 9015
ENTRYPOINT ["java", "-jar", "app_branch.jar"]