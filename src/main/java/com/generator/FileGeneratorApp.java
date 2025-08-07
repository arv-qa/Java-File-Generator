package com.generator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for the Java File Generator.
 * This class serves as the entry point for the JavaFX application.
 */
public class FileGeneratorApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        Parent root = loader.load();

        // Set up the primary stage
        primaryStage.setTitle("Java File Generator");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Main method - entry point for the application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
