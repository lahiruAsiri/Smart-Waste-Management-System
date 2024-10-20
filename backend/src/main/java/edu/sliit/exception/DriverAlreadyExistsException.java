package edu.sliit.exception;

public class DriverAlreadyExistsException extends RuntimeException {
    public DriverAlreadyExistsException(String message) {
        super(message);
    }
}
