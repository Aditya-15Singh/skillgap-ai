# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the maven wrapper and pom.xml file
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Install dependencies (this step is cached if pom.xml doesn't change)
# Using 'go-offline' can sometimes be flaky, so we just run a clean install skipping tests
# RUN ./mvnw dependency:go-offline
# Instead, we'll just copy the source and build effectively.

COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Copy the built jar file to the container
# Adjust the wildcards if you have a specific naming convention
RUN cp target/*.jar app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
