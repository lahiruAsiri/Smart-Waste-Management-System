package edu.sliit.exception;

// Custom exception class for schedule-related errors
public class ScheduleException extends RuntimeException {
    // Constructor that accepts a message to describe the exception
    public ScheduleException(String message) {
        super(message); // Passes the message to the superclass (RuntimeException)
    }
}
