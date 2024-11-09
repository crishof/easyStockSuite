FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/price-sv-0.0.1.jar
COPY ${JAR_FILE} app_price.jar
LABEL authors="cristian"
EXPOSE 9006
ENTRYPOINT ["java", "-jar", "app_price.jar"]