package edu.sliit.config;

import org.modelmapper.ModelMapper;

/**
 * Singleton implementation of ModelMapper.
 * Ensures that only one instance of ModelMapper is used throughout the application.
 */
public class ModelMapperSingleton {

    // The single instance of ModelMapper
    private static ModelMapper instance;

    // Private constructor to prevent instantiation
    private ModelMapperSingleton() {
    }

    // Public method to provide access to the single instance of ModelMapper
    public static ModelMapper getInstance() {
        if (instance == null) {
            synchronized (ModelMapperSingleton.class) {
                if (instance == null) {
                    instance = new ModelMapper();
                }
            }
        }
        return instance;
    }
}
