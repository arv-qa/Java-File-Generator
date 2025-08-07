package com.generator.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for FileGenerationParameters class
 */
public class FileGenerationParametersTest {

    @Test
    public void testDefaultConstructor() {
        FileGenerationParameters params = new FileGenerationParameters();
        
        assertEquals("", params.getOutputDirectory());
        assertEquals(1, params.getFileCount());
        assertEquals("file_{n}.txt", params.getFileNamePattern());
        assertEquals("This is file number {n}", params.getFileContent());
    }

    @Test
    public void testParameterizedConstructor() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", 100, "doc_{n}.txt", "Content {n}"
        );
        
        assertEquals("/tmp/test", params.getOutputDirectory());
        assertEquals(100, params.getFileCount());
        assertEquals("doc_{n}.txt", params.getFileNamePattern());
        assertEquals("Content {n}", params.getFileContent());
    }

    @Test
    public void testValidation_ValidParameters() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", 10, "file_{n}.txt", "Content"
        );
        
        assertTrue(params.isValid());
    }

    @Test
    public void testValidation_EmptyDirectory() {
        FileGenerationParameters params = new FileGenerationParameters(
            "", 10, "file_{n}.txt", "Content"
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testValidation_NullDirectory() {
        FileGenerationParameters params = new FileGenerationParameters(
            null, 10, "file_{n}.txt", "Content"
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testValidation_ZeroFileCount() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", 0, "file_{n}.txt", "Content"
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testValidation_NegativeFileCount() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", -5, "file_{n}.txt", "Content"
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testValidation_EmptyFileNamePattern() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", 10, "", "Content"
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testValidation_NullFileNamePattern() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", 10, null, "Content"
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testValidation_NullContent() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/tmp/test", 10, "file_{n}.txt", null
        );
        
        assertFalse(params.isValid());
    }

    @Test
    public void testSettersAndGetters() {
        FileGenerationParameters params = new FileGenerationParameters();
        
        params.setOutputDirectory("/new/path");
        params.setFileCount(50);
        params.setFileNamePattern("new_{n}.txt");
        params.setFileContent("New content {n}");
        
        assertEquals("/new/path", params.getOutputDirectory());
        assertEquals(50, params.getFileCount());
        assertEquals("new_{n}.txt", params.getFileNamePattern());
        assertEquals("New content {n}", params.getFileContent());
    }

    @Test
    public void testToString() {
        FileGenerationParameters params = new FileGenerationParameters(
            "/test", 5, "test_{n}.txt", "Test content"
        );
        
        String result = params.toString();
        assertTrue(result.contains("/test"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("test_{n}.txt"));
        assertTrue(result.contains("Test content"));
    }
}
