package com.generator.model;

import javafx.concurrent.Task;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Background task for generating files.
 * Extends JavaFX Task to provide progress updates and run in background thread.
 */
public class FileGeneratorTask extends Task<Void> {
    
    private final FileGenerationParameters parameters;
    private volatile boolean cancelled = false;

    public FileGeneratorTask(FileGenerationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    protected Void call() throws Exception {
        updateMessage("Starting file generation...");
        updateProgress(0, parameters.getFileCount());

        // Create output directory if it doesn't exist
        File outputDir = new File(parameters.getOutputDirectory());
        if (!outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Failed to create output directory: " + parameters.getOutputDirectory());
            }
        }

        // Generate files
        for (int i = 1; i <= parameters.getFileCount() && !cancelled; i++) {
            if (isCancelled()) {
                updateMessage("Generation cancelled");
                break;
            }

            try {
                generateFile(i);
                updateProgress(i, parameters.getFileCount());
                updateMessage(String.format("Generated file %d of %d", i, parameters.getFileCount()));
                
                // Small delay to prevent overwhelming the system
                if (i % 100 == 0) {
                    Thread.sleep(1);
                }
            } catch (IOException e) {
                updateMessage("Error generating file " + i + ": " + e.getMessage());
                throw e;
            }
        }

        if (!cancelled && !isCancelled()) {
            updateMessage("File generation completed successfully!");
            updateProgress(parameters.getFileCount(), parameters.getFileCount());
        }

        return null;
    }

    /**
     * Generates a single file with the specified index
     */
    private void generateFile(int fileIndex) throws IOException {
        // Replace {n} placeholder in filename pattern
        String fileName = parameters.getFileNamePattern().replace("{n}", String.valueOf(fileIndex));
        File file = new File(parameters.getOutputDirectory(), fileName);

        // Replace placeholders in file content
        String content = parameters.getFileContent()
                .replace("{n}", String.valueOf(fileIndex))
                .replace("{date}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Write file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        }
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        cancelled = true;
        updateMessage("File generation was cancelled");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("File generation failed: " + getException().getMessage());
    }
}
