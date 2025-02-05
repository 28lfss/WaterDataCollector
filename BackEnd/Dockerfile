# Use an official Java runtime as the base image
FROM openjdk:23-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY ./build/libs/WaterDataCollector-0.0.1-SNAPSHOT.jar app.jar

# Copy the SQLite database file into the container
COPY ./water_data_collector.db ./water_data_collector.db

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]