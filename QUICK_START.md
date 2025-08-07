# Quick Start Guide

## Prerequisites
- Java 8 or higher installed
- Maven 3.6+ (for building from source)

## Option 1: Build and Run

### Windows
1. Open Command Prompt in the project directory
2. Run: `build.bat`
3. Run: `java -jar target\java-file-generator-1.0.0.jar`

### Linux/macOS
1. Open Terminal in the project directory
2. Make build script executable: `chmod +x build.sh`
3. Run: `./build.sh`
4. Run: `java -jar target/java-file-generator-1.0.0.jar`

## Option 2: Maven Commands

```bash
# Build the project
mvn clean package

# Run the application
java -jar target/java-file-generator-1.0.0.jar

# Or run in development mode
mvn javafx:run

# Run tests
mvn test
```

## Using the Application

1. **Select Output Directory**: Choose where to create files
2. **Set Number of Files**: Enter how many files to generate (1-1,000,000)
3. **File Name Pattern**: Use `{n}` for file number (e.g., `document_{n}.txt`)
4. **Content Template**: Write content with placeholders:
   - `{n}` = file number
   - `{date}` = current date/time
5. **Click Generate**: Start creating files
6. **Monitor Progress**: Watch the progress bar
7. **Cancel if Needed**: Stop generation anytime

## Example Usage

**Generate 1000 text files:**
- Output Directory: `/home/user/documents`
- Number of Files: `1000`
- File Name Pattern: `report_{n}.txt`
- Content Template:
  ```
  Report #{n}
  Generated: {date}
  
  This is an automatically generated report.
  ```

This will create files: `report_1.txt`, `report_2.txt`, ..., `report_1000.txt`

## Troubleshooting

**JavaFX Issues on Java 11+:**
If you get JavaFX errors on Java 11+, try:
```bash
java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -jar target/java-file-generator-1.0.0.jar
```

**Memory Issues with Large File Counts:**
For generating millions of files, increase heap size:
```bash
java -Xmx2g -jar target/java-file-generator-1.0.0.jar
```
