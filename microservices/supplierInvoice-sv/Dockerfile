FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/supplierInvoice-sv-0.0.1.jar
COPY ${JAR_FILE} app_supplierInvoice.jar
LABEL authors="cristian"
EXPOSE 9016
ENTRYPOINT ["java", "-jar", "app_supplierInvoice.jar"]