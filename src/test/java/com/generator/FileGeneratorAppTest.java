package com.generator;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Integration tests for FileGeneratorApp
 */
public class FileGeneratorAppTest {

    @Test
    public void testMainMethodExists() {
        // Verify that the main method exists and can be called
        try {
            // Get the main method
            java.lang.reflect.Method mainMethod = FileGeneratorApp.class.getMethod("main", String[].class);
            assertNotNull("Main method should exist", mainMethod);
            
            // Verify it's static
            assertTrue("Main method should be static", 
                      java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
            
            // Verify it's public
            assertTrue("Main method should be public", 
                      java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
            
        } catch (NoSuchMethodException e) {
            fail("Main method should exist: " + e.getMessage());
        }
    }

    @Test
    public void testApplicationClassStructure() {
        // Verify the class extends Application
        Class<?> superClass = FileGeneratorApp.class.getSuperclass();
        assertEquals("Should extend javafx.application.Application", 
                    "javafx.application.Application", superClass.getName());
    }

    @Test
    public void testStartMethodExists() {
        try {
            // Get the start method
            java.lang.reflect.Method startMethod = FileGeneratorApp.class.getMethod("start", 
                                                   Class.forName("javafx.stage.Stage"));
            assertNotNull("Start method should exist", startMethod);
            
            // Verify it's public
            assertTrue("Start method should be public", 
                      java.lang.reflect.Modifier.isPublic(startMethod.getModifiers()));
            
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            fail("Start method should exist: " + e.getMessage());
        }
    }

    @Test
    public void testPackageStructure() {
        // Verify the class is in the correct package
        assertEquals("Should be in com.generator package", 
                    "com.generator", FileGeneratorApp.class.getPackage().getName());
    }
}
