FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/category-sv-0.0.1.jar
COPY ${JAR_FILE} app-category.jar
LABEL authors="cristian"
ENTRYPOINT ["java", "-jar", "app-category.jar"]
