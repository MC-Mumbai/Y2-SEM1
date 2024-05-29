package edu.curtin.oose2024s1.assignment2.exceptions;

public class NoBikesAvailableException extends RuntimeException {
    public NoBikesAvailableException(String message) {
        super(message);
    }
}
