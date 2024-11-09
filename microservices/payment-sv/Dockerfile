FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/payment-sv-0.0.1.jar
COPY ${JAR_FILE} app_payment.jar
LABEL authors="cristian"
EXPOSE 9018
ENTRYPOINT ["java", "-jar", "app_payment.jar"]