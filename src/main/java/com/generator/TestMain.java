package com.generator;

/**
 * Simple test main class for CI/CD verification
 * This class doesn't depend on JavaFX and can be used to test basic compilation
 */
public class TestMain {
    
    public static void main(String[] args) {
        System.out.println("Java File Generator - Build Test");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Build successful!");
    }
}
