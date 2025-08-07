package com.generator.model;

/**
 * Model class that encapsulates all parameters needed for file generation.
 */
public class FileGenerationParameters {
    private String outputDirectory;
    private int fileCount;
    private String fileNamePattern;
    private String fileContent;

    /**
     * Default constructor
     */
    public FileGenerationParameters() {
        this.outputDirectory = "";
        this.fileCount = 1;
        this.fileNamePattern = "file_{n}.txt";
        this.fileContent = "This is file number {n}";
    }

    /**
     * Constructor with all parameters
     */
    public FileGenerationParameters(String outputDirectory, int fileCount, 
                                  String fileNamePattern, String fileContent) {
        this.outputDirectory = outputDirectory;
        this.fileCount = fileCount;
        this.fileNamePattern = fileNamePattern;
        this.fileContent = fileContent;
    }

    // Getters and setters
    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getFileNamePattern() {
        return fileNamePattern;
    }

    public void setFileNamePattern(String fileNamePattern) {
        this.fileNamePattern = fileNamePattern;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    /**
     * Validates the parameters
     * @return true if all parameters are valid, false otherwise
     */
    public boolean isValid() {
        return outputDirectory != null && !outputDirectory.trim().isEmpty() &&
               fileCount > 0 &&
               fileNamePattern != null && !fileNamePattern.trim().isEmpty() &&
               fileContent != null;
    }

    @Override
    public String toString() {
        return "FileGenerationParameters{" +
                "outputDirectory='" + outputDirectory + '\'' +
                ", fileCount=" + fileCount +
                ", fileNamePattern='" + fileNamePattern + '\'' +
                ", fileContent='" + fileContent + '\'' +
                '}';
    }
}
