package edu.curtin.oose2024s1.assignment2.O.F;

import edu.curtin.oose2024s1.assignment2.Bikes.Bike;
import edu.curtin.oose2024s1.assignment2.Events.DeliveryEvent;
import edu.curtin.oose2024s1.assignment2.Events.DropOffEvent;
import edu.curtin.oose2024s1.assignment2.Events.Event;
import edu.curtin.oose2024s1.assignment2.Events.PickUpEvent;
import edu.curtin.oose2024s1.assignment2.Events.PurchaseInStoreEvent;
import edu.curtin.oose2024s1.assignment2.Events.PurchaseOnlineEvent;
import edu.curtin.oose2024s1.assignment2.States.BikeState;

public class Factory 
{
    public static Bike createBike(BikeState state) 
    {
        return new Bike(state);
    }

    public static Event createEvent(String message, String email) 
    {
        switch (message) 
        {
            case "DELIVERY":
                return new DeliveryEvent();
            case "DROP-OFF":
                return new DropOffEvent(email);
            case "PURCHASE-ONLINE":
                return new PurchaseOnlineEvent(email);
            case "PURCHASE-IN-STORE":
                return new PurchaseInStoreEvent();
            case "PICK-UP":
                return new PickUpEvent(email);
            default:
                throw new IllegalArgumentException("Unknown event has occurred: " + message);
        }
    }
}
