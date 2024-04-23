FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/product-sv-0.0.1.jar
COPY ${JAR_FILE} app_product.jar
LABEL authors="cristian"
ENTRYPOINT ["java", "-jar", "app_product.jar"]