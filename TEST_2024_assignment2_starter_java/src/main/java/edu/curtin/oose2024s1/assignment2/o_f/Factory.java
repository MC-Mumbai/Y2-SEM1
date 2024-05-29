package edu.curtin.oose2024s1.assignment2.o_f;

import edu.curtin.oose2024s1.assignment2.bikes.Bike;
import edu.curtin.oose2024s1.assignment2.events.DeliveryEvent;
import edu.curtin.oose2024s1.assignment2.events.DropOffEvent;
import edu.curtin.oose2024s1.assignment2.events.Event;
import edu.curtin.oose2024s1.assignment2.events.PickUpEvent;
import edu.curtin.oose2024s1.assignment2.events.PurchaseInStoreEvent;
import edu.curtin.oose2024s1.assignment2.events.PurchaseOnlineEvent;
import edu.curtin.oose2024s1.assignment2.states.BikeState;


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
