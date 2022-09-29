FROM openjdk:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} school-test.jar
ENTRYPOINT ["java", "-jar", "/school-test.jar"]
