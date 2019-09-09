package com.kodilla;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Toolkit;

public class TimerTest {
    public static void main(String args[]) {
        Timer timer = new Timer();
        int counter = 0;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Task invoked: ");
                }
            }, 1000, 500);

    }
}
