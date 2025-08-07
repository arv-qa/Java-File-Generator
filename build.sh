#!/bin/bash
# Build script for Java File Generator (Unix/Linux/macOS)

echo "Building Java File Generator..."
echo

# Clean and build the project
mvn clean package

if [ $? -eq 0 ]; then
    echo
    echo "Build successful!"
    echo
    echo "The executable JAR file is located at:"
    echo "target/java-file-generator-1.0.0.jar"
    echo
    echo "To run the application:"
    echo "java -jar target/java-file-generator-1.0.0.jar"
    echo
    echo "Or simply double-click the JAR file if Java is properly configured."
else
    echo
    echo "Build failed! Please check the error messages above."
fi
