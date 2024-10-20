package edu.sliit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class to bootstrap and launch the Waste Management System Spring Boot application.
 */
@SpringBootApplication
public class WasteManagementSystemApplication {

    /**
     * The main method acts as the entry point of the Spring Boot application.
     * It launches the application using Spring's SpringApplication.run() method.
     *
     * @param args Command line arguments passed during the application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(WasteManagementSystemApplication.class, args);
    }
}
