FROM openjdk:17

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY sub/target/sub-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
EXPOSE 8081

# Starting the application
CMD ["java", "-jar", "sub-0.0.1-SNAPSHOT.jar"]