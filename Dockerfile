# Use the latest Ubuntu as the base image
FROM ubuntu:latest

# Install dependencies: JDK and Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven curl && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Copy the Maven project files (pom.xml and source code)
COPY webapp/pom.xml /app/
COPY webapp/src /app/src

# Build the application with maven
RUN mvn clean package

# Expose the application's port (default is 8080, change if necessary)
EXPOSE 8080

#run the Spring Boot application
CMD ["mvn", "tomcat:run"]