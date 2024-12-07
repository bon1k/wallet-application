
FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/wallet-application-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

