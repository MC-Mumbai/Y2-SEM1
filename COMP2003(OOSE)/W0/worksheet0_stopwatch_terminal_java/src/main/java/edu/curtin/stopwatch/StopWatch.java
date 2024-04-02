package edu.curtin.stopwatch;

import java.io.*;
import java.util.*;

/**
 * Displays a stopwatch at the terminal.
 */
public class StopWatch 
{
    public static void main(String[] args)
    {
        try
        {
            @SuppressWarnings("PMD.CloseResource") // No need to close standard input
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Press enter to begin");
            br.readLine();
            long startTime = System.currentTimeMillis();
            
            System.out.println("Press enter to end");
            while(!br.ready())
            {
                long time = System.currentTimeMillis() - startTime;
                long mins = time / 60000;
                long secs = (time / 1000) % 60;
                long millis = time % 1000;
                
                System.out.printf("\r%d:%02d.%03d", mins, secs, millis);
                
                try
                {
                    Thread.sleep(1);
                }
                catch(InterruptedException e)
                {
                    throw new AssertionError(e); // Ought to be impossible.
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error in standard input: " + e.getMessage());
        }
    }
}
