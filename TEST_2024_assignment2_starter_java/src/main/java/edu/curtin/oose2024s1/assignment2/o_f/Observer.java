package edu.curtin.oose2024s1.assignment2.o_f;

public interface Observer {
    void update(String message);


    public class ConsoleObserver implements Observer 
    {
        @Override
        public void update(String message) {
            System.out.println("Update received: " + message);
        }
    }

}
