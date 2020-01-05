FROM openjdk:8-jdk-alpine

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/hackTest-0.0.1-SNAPSHOT.jar


# Add the application's jar to the container
ADD ${JAR_FILE} hackTest.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/hackTest.jar"]