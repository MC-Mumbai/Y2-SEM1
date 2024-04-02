package edu.curtin.stopwatch;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;

public class StopWatchTest
{
    private PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;

    @AfterEach
    void tearDown()
    {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    @SuppressWarnings("PMD.CloseResource") // No actual external IO operations
    void testStopWatch() throws IOException, InterruptedException
    {
        // Capture the production code's output.
        var outputBuf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputBuf));

        // Get an output stream that we can use to feed _input_ into the production code in real
        // time.
        var pipedOS = new PipedOutputStream();
        var testInputPrinter = new PrintStream(pipedOS);
        System.setIn(new PipedInputStream(pipedOS));

        // Start the production code in a separate thread, so that we can watch what it does at
        // the same time (since _timing_ is the whole point of the production code).
        var productionThread = new Thread(() -> StopWatch.main(null));
        productionThread.start();

        // Wait for the production code to start up, then simulate an enter key press.
        Thread.sleep(1000);
        testInputPrinter.println();

        for(int i = 1; i < 4; i++)
        {
            // Find the production code's reported time at (approximately) 1000ms intervals; check
            // that the correct number of seconds is reported (approximately).

            Thread.sleep(1000);
            String out = outputBuf.toString();
            double reportedTime = Double.parseDouble(out.substring(out.lastIndexOf(':') + 1));
            assertEquals((double)i, reportedTime, 0.1, "After " + i + " second(s)");
        }

        // Simulate another enter key press, and check that the production code has shut down.
        testInputPrinter.println();
        Thread.sleep(1000);
        assertFalse(productionThread.isAlive());
    }
}
