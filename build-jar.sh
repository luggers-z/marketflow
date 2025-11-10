#!/bin/bash
# Build script for MarketFlow fat jar
# This script sets up the environment and builds an executable JAR

set -e

echo "Building MarketFlow fat JAR..."

# Detect Java 25 installation
if [ -d "/usr/lib/jvm/java-25-openjdk-amd64" ]; then
    export JAVA_HOME=/usr/lib/jvm/java-25-openjdk-amd64
elif command -v javac &> /dev/null; then
    JAVA_VERSION=$(javac -version 2>&1 | awk '{print $2}' | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -eq 25 ]; then
        echo "Java 25 detected in PATH"
    else
        echo "Warning: Java 25 is required but Java $JAVA_VERSION is in PATH"
        echo "The build may fail. Please install Java 25."
    fi
else
    echo "Error: Java 25 not found. Please install Java 25."
    exit 1
fi

# Display Java version
echo "Using Java:"
"${JAVA_HOME}/bin/java" -version 2>&1 | head -1

# Build the fat JAR with cross-platform support
echo ""
echo "Running Maven package..."
mvn clean package -DskipTests

# Check if build was successful
if [ -f "target/marketflow-1.0.0-SNAPSHOT.jar" ]; then
    echo ""
    echo "✓ Build successful!"
    echo ""
    echo "Fat JAR created at: target/marketflow-1.0.0-SNAPSHOT.jar"
    echo ""
    echo "To run the application:"
    echo "  java -jar target/marketflow-1.0.0-SNAPSHOT.jar"
    echo ""
    JAR_SIZE=$(du -h target/marketflow-1.0.0-SNAPSHOT.jar | cut -f1)
    echo "JAR size: $JAR_SIZE"
else
    echo ""
    echo "✗ Build failed!"
    exit 1
fi
