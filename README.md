# Java File Generator

A simple, elegant desktop application for generating large volumes of files with customizable content and naming patterns.

## Features

- **Simple & Intuitive UI**: Clean JavaFX interface with easy-to-use controls
- **Flexible File Naming**: Customizable naming patterns with placeholder support
- **Template-based Content**: Define content templates with dynamic placeholders
- **Progress Tracking**: Real-time progress bar and status updates
- **Background Processing**: Non-blocking file generation with cancellation support
- **Cross-Platform**: Runs on Windows, macOS, and Linux
- **Zero Installation**: Single executable JAR file

## Requirements

- Java 8 or higher
- JavaFX runtime (included in Java 8, separate module for Java 11+)

## Usage

### Running the Application

1. **Double-click the JAR file** (if Java is properly configured)
2. **Command line**: `java -jar java-file-generator-1.0.0.jar`

### Using the Application

1. **Select Output Directory**: Click "Browse" to choose where files will be created
2. **Set File Count**: Enter the number of files to generate (1 to 1,000,000)
3. **Define Naming Pattern**: Use `{n}` as placeholder for file number (e.g., `document_{n}.txt`)
4. **Create Content Template**: Write template content using placeholders:
   - `{n}` - File number
   - `{date}` - Current date and time
5. **Click Generate**: Start the file generation process
6. **Monitor Progress**: Watch the progress bar and status messages
7. **Cancel if Needed**: Click "Cancel" to stop generation at any time

### Example Patterns

**File Names:**
- `file_{n}.txt` → file_1.txt, file_2.txt, file_3.txt...
- `document_{n}.pdf` → document_1.pdf, document_2.pdf...
- `data_{n}.json` → data_1.json, data_2.json...

**Content Templates:**
```
This is file number {n}
Generated on: {date}
Content goes here...
```

## Building from Source

### Prerequisites
- Java 8 JDK
- Apache Maven 3.6+

### Build Steps

1. **Clone or download the source code**
2. **Navigate to project directory**:
   ```bash
   cd java-file-generator
   ```
3. **Build the project**:
   ```bash
   mvn clean package
   ```
4. **Find the executable JAR**:
   ```
   target/java-file-generator-1.0.0.jar
   ```

### Development

**Run in development mode**:
```bash
mvn javafx:run
```

**Run tests**:
```bash
mvn test
```

## Technical Details

- **Architecture**: Model-View-Controller (MVC) pattern
- **UI Framework**: JavaFX with FXML
- **Build Tool**: Apache Maven
- **Packaging**: Maven Shade Plugin (fat JAR)
- **Threading**: JavaFX Task for background processing

## Project Structure

```
java-file-generator/
├── src/main/java/com/generator/
│   ├── FileGeneratorApp.java          # Main application class
│   ├── controller/
│   │   └── MainController.java        # UI controller
│   └── model/
│       ├── FileGenerationParameters.java  # Data model
│       └── FileGeneratorTask.java     # Background task
├── src/main/resources/
│   └── fxml/
│       └── MainWindow.fxml            # UI layout
├── pom.xml                            # Maven configuration
└── README.md                          # This file
```

## License

This project is open source and available under the MIT License.

## Support

For issues, questions, or contributions, please refer to the project documentation or contact the development team.
