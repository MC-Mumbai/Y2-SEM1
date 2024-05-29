package edu.curtin.oose2024s1.assignment2.exceptions;



public class NoMatchingEmailException extends RuntimeException {
    public NoMatchingEmailException() {
        super("FAILURE: No bike matching customer email.");
    }
}