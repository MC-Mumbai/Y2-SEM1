package edu.curtin.stopwatch;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene; 
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.*;

/**
 * Displays a stopwatch in a JavaFX GUI Window.
 */
public class StopWatch extends Application
{
    private static final long TIMER_UPDATE_PERIOD = 1L; // Milliseconds

    private Button startStopBtn = new Button("Start");
    private TextField display = new TextField("0:00.000");
    
    private long elapsedTime = 0L;
    private long lastTime = 0L;
        
    private Timer timer = new Timer(true);
    private TimerTask timerTask = null;
    
    public static void main(String[] args)
    {
        Application.launch(args); // Run JavaFX
        // This will effectively do 'new StopWatch()' and then call 'start(...)'.
    }
    
    @Override
    public void start(Stage stage)
    {
        Platform.setImplicitExit(true);
        stage.setTitle("Stopwatch");
        
        BorderPane mainBox = new BorderPane();
        mainBox.setTop(startStopBtn);
        mainBox.setCenter(display);
        
        startStopBtn.setOnAction((event) -> buttonPressed());
        display.setFont(new Font(30.0));
       
        Scene scene = new Scene(mainBox);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }
    
    private void buttonPressed()
    {
        if(timerTask == null)
        {
            lastTime = System.currentTimeMillis();
            startStopBtn.setText("Stop");
            timerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    long thisTime = System.currentTimeMillis();
                    elapsedTime += thisTime - lastTime;
                    lastTime = thisTime;
                    
                    Platform.runLater(() ->
                    {
                        long mins = elapsedTime / 60000;
                        long secs = (elapsedTime / 1000) % 60;
                        long millis = elapsedTime % 1000;
                        display.setText(String.format("%d:%02d.%03d", mins, secs, millis));
                    });
                }
            };
            timer.scheduleAtFixedRate(timerTask, TIMER_UPDATE_PERIOD, TIMER_UPDATE_PERIOD);
        }
        else
        {
            timerTask.cancel();
            timerTask = null;
            startStopBtn.setText("Start");
        }
    }
}
