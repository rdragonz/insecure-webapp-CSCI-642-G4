# Use the latest Ubuntu as the base image
FROM ubuntu:latest

# Install dependencies: JDK, Maven, curl, Python, and pip
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven curl python3 python3-pip python3-venv && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory
WORKDIR /app

# Create a virtual environment for Python
RUN python3 -m venv venv

# Activate the virtual environment and install cve-bin-tool
RUN . venv/bin/activate && pip install cve-bin-tool

# Copy the Maven project files (pom.xml and source code)
COPY webapp/pom.xml /app/
COPY webapp/src /app/src

# Build the application with Maven
RUN mvn clean package -DskipTests

# Expose the application's port (default is 8080, change if necessary)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["mvn", "exec:java", "-Dexec.mainClass=com.example.calendar.App"]
