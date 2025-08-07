package com.generator.controller;

import com.generator.model.FileGenerationParameters;
import com.generator.model.FileGeneratorTask;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the main window.
 * Handles user interactions and coordinates between the view and model.
 */
public class MainController implements Initializable {

    @FXML private TextField outputDirectoryField;
    @FXML private TextField fileCountField;
    @FXML private TextField fileNamePatternField;
    @FXML private TextArea contentTemplateArea;
    @FXML private Button browseButton;
    @FXML private Button generateButton;
    @FXML private Button cancelButton;
    @FXML private ProgressBar progressBar;
    @FXML private Label statusLabel;

    private FileGeneratorTask currentTask;
    private Thread currentThread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set default values
        outputDirectoryField.setText(System.getProperty("user.home"));
        
        // Add input validation listeners
        fileCountField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fileCountField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    /**
     * Handles the browse directory button click
     */
    @FXML
    private void handleBrowseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Output Directory");
        
        // Set initial directory
        String currentPath = outputDirectoryField.getText();
        if (currentPath != null && !currentPath.trim().isEmpty()) {
            File currentDir = new File(currentPath);
            if (currentDir.exists() && currentDir.isDirectory()) {
                directoryChooser.setInitialDirectory(currentDir);
            }
        }

        Stage stage = (Stage) browseButton.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        
        if (selectedDirectory != null) {
            outputDirectoryField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    /**
     * Handles the generate files button click
     */
    @FXML
    private void handleGenerateFiles() {
        if (!validateInput()) {
            return;
        }

        // Create parameters from UI input
        FileGenerationParameters parameters = createParametersFromInput();
        
        // Create and configure the task
        currentTask = new FileGeneratorTask(parameters);
        
        // Bind progress properties
        progressBar.progressProperty().bind(currentTask.progressProperty());
        statusLabel.textProperty().bind(currentTask.messageProperty());
        
        // Handle task completion
        currentTask.setOnSucceeded(e -> {
            generateButton.setDisable(false);
            cancelButton.setDisable(true);
            progressBar.progressProperty().unbind();
            statusLabel.textProperty().unbind();
            statusLabel.setText("File generation completed successfully!");
        });
        
        currentTask.setOnFailed(e -> {
            generateButton.setDisable(false);
            cancelButton.setDisable(true);
            progressBar.progressProperty().unbind();
            statusLabel.textProperty().unbind();
            
            Throwable exception = currentTask.getException();
            String errorMessage = exception != null ? exception.getMessage() : "Unknown error occurred";
            statusLabel.setText("Error: " + errorMessage);
            
            showErrorAlert("Generation Failed", errorMessage);
        });
        
        currentTask.setOnCancelled(e -> {
            generateButton.setDisable(false);
            cancelButton.setDisable(true);
            progressBar.progressProperty().unbind();
            statusLabel.textProperty().unbind();
            statusLabel.setText("File generation was cancelled");
        });

        // Update UI state
        generateButton.setDisable(true);
        cancelButton.setDisable(false);
        
        // Start the task in a new thread
        currentThread = new Thread(currentTask);
        currentThread.setDaemon(true);
        currentThread.start();
    }

    /**
     * Handles the cancel generation button click
     */
    @FXML
    private void handleCancelGeneration() {
        if (currentTask != null && !currentTask.isDone()) {
            currentTask.cancel();
        }
    }

    /**
     * Validates user input
     */
    private boolean validateInput() {
        StringBuilder errors = new StringBuilder();

        // Validate output directory
        String outputDir = outputDirectoryField.getText().trim();
        if (outputDir.isEmpty()) {
            errors.append("- Output directory is required\n");
        } else {
            File dir = new File(outputDir);
            if (!dir.exists()) {
                // Try to create the directory
                if (!dir.mkdirs()) {
                    errors.append("- Cannot create output directory: ").append(outputDir).append("\n");
                }
            } else if (!dir.isDirectory()) {
                errors.append("- Output path is not a directory: ").append(outputDir).append("\n");
            }
        }

        // Validate file count
        String fileCountText = fileCountField.getText().trim();
        if (fileCountText.isEmpty()) {
            errors.append("- Number of files is required\n");
        } else {
            try {
                int count = Integer.parseInt(fileCountText);
                if (count <= 0) {
                    errors.append("- Number of files must be greater than 0\n");
                } else if (count > 1000000) {
                    errors.append("- Number of files cannot exceed 1,000,000\n");
                }
            } catch (NumberFormatException e) {
                errors.append("- Invalid number format for file count\n");
            }
        }

        // Validate file name pattern
        String pattern = fileNamePatternField.getText().trim();
        if (pattern.isEmpty()) {
            errors.append("- File name pattern is required\n");
        } else if (!pattern.contains("{n}")) {
            errors.append("- File name pattern must contain {n} placeholder\n");
        }

        if (errors.length() > 0) {
            showErrorAlert("Validation Error", "Please fix the following issues:\n\n" + errors.toString());
            return false;
        }

        return true;
    }

    /**
     * Creates FileGenerationParameters from UI input
     */
    private FileGenerationParameters createParametersFromInput() {
        return new FileGenerationParameters(
            outputDirectoryField.getText().trim(),
            Integer.parseInt(fileCountField.getText().trim()),
            fileNamePatternField.getText().trim(),
            contentTemplateArea.getText()
        );
    }

    /**
     * Shows an error alert dialog
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
