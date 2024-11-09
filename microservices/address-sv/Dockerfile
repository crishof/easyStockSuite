FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/address-sv-0.0.1.jar
COPY ${JAR_FILE} app_address.jar
LABEL authors="cristian"
EXPOSE 9010
ENTRYPOINT ["java", "-jar", "app_address.jar"]