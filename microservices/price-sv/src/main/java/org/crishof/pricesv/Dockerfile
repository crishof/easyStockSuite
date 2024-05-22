FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/price-sv-0.0.1.jar
COPY ${JAR_FILE} app-category.jar
LABEL authors="cristian"
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "app-price.jar"]
