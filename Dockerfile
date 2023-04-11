FROM openjdk:8-jdk-alpine
ARG JAR_FILE=backend-code/target/demo-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=docker","-XX:+UseSerialGC","-Xss512k","-XX:MaxRAM=200m","-Dlogging.level.root=ERROR","/demo-0.0.1-SNAPSHOT.jar"]
