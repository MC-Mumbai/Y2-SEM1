package edu.curtin.oose2024s1.assignment2.exceptions; 

public class NotEnoughSpaceForDropOffException extends RuntimeException {
    public NotEnoughSpaceForDropOffException() {
        super("FAILURE: Not enough space for drop-off.");
    }
}
