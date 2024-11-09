FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/stock-sv-0.0.1.jar
COPY ${JAR_FILE} app_stock.jar
LABEL authors="cristian"
EXPOSE 9009
ENTRYPOINT ["java", "-jar", "app_stock.jar"]