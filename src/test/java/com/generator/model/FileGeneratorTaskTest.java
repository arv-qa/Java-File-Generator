package com.generator.model;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

/**
 * Unit tests for FileGeneratorTask class
 */
public class FileGeneratorTaskTest {

    private Path tempDir;
    private FileGenerationParameters parameters;

    @Before
    public void setUp() throws IOException {
        // Create temporary directory for testing
        tempDir = Files.createTempDirectory("file-generator-test");
        
        // Set up test parameters
        parameters = new FileGenerationParameters(
            tempDir.toString(),
            5,
            "test_{n}.txt",
            "Test file content {n}\nGenerated on: {date}"
        );
    }

    @After
    public void tearDown() throws IOException {
        // Clean up temporary directory
        if (tempDir != null && Files.exists(tempDir)) {
            Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }

    @Test
    public void testFileGenerationSuccess() throws Exception {
        FileGeneratorTask task = new FileGeneratorTask(parameters);
        
        // Execute the task
        task.call();
        
        // Verify files were created
        for (int i = 1; i <= 5; i++) {
            File expectedFile = new File(tempDir.toFile(), "test_" + i + ".txt");
            assertTrue("File test_" + i + ".txt should exist", expectedFile.exists());
            
            // Verify file content
            String content = new String(Files.readAllBytes(expectedFile.toPath()));
            assertTrue("Content should contain file number", content.contains("Test file content " + i));
            assertTrue("Content should contain date placeholder replacement", content.contains("Generated on:"));
        }
    }

    @Test
    public void testProgressUpdates() throws Exception {
        FileGeneratorTask task = new FileGeneratorTask(parameters);
        
        // Track progress updates
        final double[] lastProgress = {-1};
        task.progressProperty().addListener((obs, oldVal, newVal) -> {
            lastProgress[0] = newVal.doubleValue();
        });
        
        // Execute the task
        task.call();
        
        // Verify progress reached 100%
        assertEquals("Progress should reach 100%", 1.0, lastProgress[0], 0.001);
    }

    @Test
    public void testMessageUpdates() throws Exception {
        FileGeneratorTask task = new FileGeneratorTask(parameters);
        
        // Track message updates
        final String[] lastMessage = {""};
        task.messageProperty().addListener((obs, oldVal, newVal) -> {
            lastMessage[0] = newVal;
        });
        
        // Execute the task
        task.call();
        
        // Verify final message
        assertTrue("Final message should indicate completion", 
                   lastMessage[0].contains("completed successfully"));
    }

    @Test
    public void testCancellation() throws Exception {
        // Create parameters for many files to allow cancellation
        FileGenerationParameters manyFilesParams = new FileGenerationParameters(
            tempDir.toString(),
            1000,
            "cancel_test_{n}.txt",
            "Content {n}"
        );
        
        FileGeneratorTask task = new FileGeneratorTask(manyFilesParams);
        
        // Start task in separate thread
        Thread taskThread = new Thread(() -> {
            try {
                task.call();
            } catch (Exception e) {
                // Expected when cancelled
            }
        });
        taskThread.start();
        
        // Cancel after a short delay
        Thread.sleep(50);
        task.cancel();
        
        // Wait for task to complete
        taskThread.join(5000);
        
        // Verify task was cancelled
        assertTrue("Task should be cancelled", task.isCancelled());
    }

    @Test
    public void testDirectoryCreation() throws Exception {
        // Use non-existent directory
        Path nonExistentDir = tempDir.resolve("new-directory");
        FileGenerationParameters dirParams = new FileGenerationParameters(
            nonExistentDir.toString(),
            3,
            "dir_test_{n}.txt",
            "Content {n}"
        );
        
        FileGeneratorTask task = new FileGeneratorTask(dirParams);
        
        // Execute the task
        task.call();
        
        // Verify directory was created
        assertTrue("Directory should be created", Files.exists(nonExistentDir));
        assertTrue("Path should be a directory", Files.isDirectory(nonExistentDir));
        
        // Verify files were created in the new directory
        File testFile = new File(nonExistentDir.toFile(), "dir_test_1.txt");
        assertTrue("File should exist in new directory", testFile.exists());
    }

    @Test
    public void testPlaceholderReplacement() throws Exception {
        FileGenerationParameters placeholderParams = new FileGenerationParameters(
            tempDir.toString(),
            2,
            "placeholder_{n}.txt",
            "File number: {n}\nTimestamp: {date}\nEnd of file"
        );
        
        FileGeneratorTask task = new FileGeneratorTask(placeholderParams);
        
        // Execute the task
        task.call();
        
        // Verify placeholder replacement in file 1
        File file1 = new File(tempDir.toFile(), "placeholder_1.txt");
        String content1 = new String(Files.readAllBytes(file1.toPath()));
        assertTrue("Should replace {n} with 1", content1.contains("File number: 1"));
        assertFalse("Should not contain {n} placeholder", content1.contains("{n}"));
        assertFalse("Should not contain {date} placeholder", content1.contains("{date}"));
        
        // Verify placeholder replacement in file 2
        File file2 = new File(tempDir.toFile(), "placeholder_2.txt");
        String content2 = new String(Files.readAllBytes(file2.toPath()));
        assertTrue("Should replace {n} with 2", content2.contains("File number: 2"));
    }

    @Test(expected = IOException.class)
    public void testInvalidDirectory() throws Exception {
        // Use invalid directory path (assuming this path doesn't exist and can't be created)
        FileGenerationParameters invalidParams = new FileGenerationParameters(
            "/invalid/path/that/cannot/be/created",
            1,
            "test_{n}.txt",
            "Content"
        );
        
        FileGeneratorTask task = new FileGeneratorTask(invalidParams);
        
        // This should throw an IOException
        task.call();
    }

    @Test
    public void testEmptyContent() throws Exception {
        FileGenerationParameters emptyContentParams = new FileGenerationParameters(
            tempDir.toString(),
            2,
            "empty_{n}.txt",
            ""
        );
        
        FileGeneratorTask task = new FileGeneratorTask(emptyContentParams);
        
        // Execute the task
        task.call();
        
        // Verify files were created with empty content
        File file1 = new File(tempDir.toFile(), "empty_1.txt");
        assertTrue("File should exist", file1.exists());
        assertEquals("File should be empty", 0, file1.length());
    }
}
