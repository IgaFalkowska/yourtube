FROM openjdk:17-alpine
ARG FILE_JAR=./target/*.jar
COPY ${FILE_JAR} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]
