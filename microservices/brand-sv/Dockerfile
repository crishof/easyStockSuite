FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/brand-sv-0.0.1.jar
COPY ${JAR_FILE} app_brand.jar
LABEL authors="cristian"
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "app_brand.jar"]