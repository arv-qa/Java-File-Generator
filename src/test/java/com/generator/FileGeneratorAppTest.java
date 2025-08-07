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
        // Verify the class extends Application (without loading JavaFX classes)
        Class<?> superClass = FileGeneratorApp.class.getSuperclass();
        assertNotNull("Should have a superclass", superClass);
        assertTrue("Should extend some Application class",
                  superClass.getName().contains("Application"));
    }

    @Test
    public void testStartMethodExists() {
        // Check if start method exists without loading JavaFX classes
        java.lang.reflect.Method[] methods = FileGeneratorApp.class.getDeclaredMethods();
        boolean hasStartMethod = false;

        for (java.lang.reflect.Method method : methods) {
            if ("start".equals(method.getName()) && method.getParameterCount() == 1) {
                hasStartMethod = true;
                assertTrue("Start method should be public",
                          java.lang.reflect.Modifier.isPublic(method.getModifiers()));
                break;
            }
        }

        assertTrue("Should have a start method", hasStartMethod);
    }

    @Test
    public void testPackageStructure() {
        // Verify the class is in the correct package
        assertEquals("Should be in com.generator package", 
                    "com.generator", FileGeneratorApp.class.getPackage().getName());
    }
}
