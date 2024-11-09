FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/customer-sv-0.0.1.jar
COPY ${JAR_FILE} app_customer.jar
LABEL authors="cristian"
EXPOSE 9012
ENTRYPOINT ["java", "-jar", "app_customer.jar"]