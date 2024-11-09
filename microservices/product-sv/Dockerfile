FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/product-sv-0.0.1.jar
COPY ${JAR_FILE} app_product.jar
LABEL authors="cristian"
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "app_product.jar"]