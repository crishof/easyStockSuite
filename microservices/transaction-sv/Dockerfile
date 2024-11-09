FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/transaction-sv-0.0.1.jar
COPY ${JAR_FILE} app_transaction.jar
LABEL authors="cristian"
EXPOSE 9017
ENTRYPOINT ["java", "-jar", "app_transaction.jar"]