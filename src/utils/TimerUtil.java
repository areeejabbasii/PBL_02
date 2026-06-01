package utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class TimerUtil {
    private Timer timer;
    private JLabel timerLabel;
    private int timeRemaining;
    private Runnable onTimeout;
    
    public TimerUtil(JLabel timerLabel, int minutes, Runnable onTimeout) {
        this.timerLabel = timerLabel;
        this.timeRemaining = minutes * 60;
        this.onTimeout = onTimeout;
    }
    
    public void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (timeRemaining <= 0) {
                    timer.cancel();
                    SwingUtilities.invokeLater(() -> {
                        if (onTimeout != null) {
                            onTimeout.run();
                        }
                    });
                    return;
                }
                
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                SwingUtilities.invokeLater(() -> {
                    timerLabel.setText(String.format("Time Remaining: %02d:%02d", minutes, seconds));
                });
                timeRemaining--;
            }
        }, 0, 1000);
    }
    
    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
    
    public int getTimeRemaining() {
        return timeRemaining;
    }
}