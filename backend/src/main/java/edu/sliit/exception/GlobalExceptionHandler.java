package edu.sliit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Global exception handler for the application
@ControllerAdvice // Allows handling exceptions globally across all controllers
public class GlobalExceptionHandler {

    // Handles ScheduleException specifically
    @ExceptionHandler(ScheduleException.class)
    public ResponseEntity<String> handleScheduleException(ScheduleException ex) {
        // Returns a response with HTTP status 500 (Internal Server Error) and the error message
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error: " + ex.getMessage());
    }

    // Handles all other generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // Returns a response with HTTP status 500 (Internal Server Error) and a generic error message
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }

}
