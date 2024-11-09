FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/invoice-sv-0.0.1.jar
COPY ${JAR_FILE} app_invoice.jar
LABEL authors="cristian"
EXPOSE 9008
ENTRYPOINT ["java", "-jar", "app_invoice.jar"]