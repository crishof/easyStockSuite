FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/supplier-sv-0.0.1.jar
COPY ${JAR_FILE} app_supplier.jar
LABEL authors="cristian"
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "app_supplier.jar"]