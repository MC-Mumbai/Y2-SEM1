package edu.curtin.oose2024s1.assignment2.exceptions;

public class NotEnoughResourcesForDeliveryException extends RuntimeException {
    public NotEnoughResourcesForDeliveryException() {
        super("FAILURE: Not enough space or cash for delivery.");
    }
}
