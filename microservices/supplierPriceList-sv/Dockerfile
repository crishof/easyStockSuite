FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/supplierPriceList-sv-0.0.1.jar
COPY ${JAR_FILE} app-supplierPriceList.jar
LABEL authors="cristian"
EXPOSE 9007
ENTRYPOINT ["java", "-jar", "app-supplierPriceList.jar"]