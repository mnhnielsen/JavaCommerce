FROM openjdk:17

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY pub/target/pub-0.0.1-SNAPSHOT.jar /app

# Exposing port 8080
EXPOSE 8080

# Starting the application
CMD ["java", "-jar", "pub-0.0.1-SNAPSHOT.jar"]