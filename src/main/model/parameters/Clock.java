package main.model.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * The {@code Clock} class represents a clock object that will be used to keep track of time. It will be running a thread that
 * will continuously be incrementing clock by 1 (default) every 1 second.
 *
 * @author Philippe Vo
 *
 */
public class Clock {

    private int[] clockTime;
    private long referenceTime;
    private int multiplier;

    /**
     * Constructs a {@code Clock} that will be used to keep track of time. It will be running a thread that
     * will continously be looking at the system clock every 1 second.
     */
    public Clock(){
        referenceTime = 0; // close to 24:00:00 -> 86390 seconds
        multiplier = 1;

        javax.swing.Timer timerClock = new javax.swing.Timer(1000, new Clock.ClockListener());
        timerClock.start();
    }

    /**
     * Sets the {@code multiplier} state of this {@code Clock} to that specified.
     *
     * @param multiplier the amount of time we want to speed the time
     */
    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    /**
     * Sets the {@code referenceTime} num of this {@code Clock} to that specified.
     *
     * @param time the current time to be referenced
     */
    public void updateTime(int[] time) {
        int h = time[0];
        int m = time[1];
        int s = time[2];

        this.referenceTime = (h * 60 * 60) + (m * 60) + s;
    }

    /**
     * @return {@code int[]} the clock time
     */
    public int[] getClockTime() {
        return clockTime;
    }

    // this method is called every 1 second by the javax.swing.Timer class timerClock
    class ClockListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            referenceTime = referenceTime + (1 * multiplier); // increment clock

            clockTime = splitToComponentTimes(referenceTime);
        }

        /**
         * Returns the totalSeconds into h:m:s
         *
         * @param totalSeconds the total amount of seconds
         */
        public int[] splitToComponentTimes(long totalSeconds)
        {
            int hours = (int) totalSeconds / 3600;
            int remainder = (int) totalSeconds - hours * 3600;
            int mins = remainder / 60;
            remainder = remainder - mins * 60;
            int secs = remainder;

            if(hours >= 24){ // if past the 24hrs mark -> reset the reference time
                referenceTime = 0;
                hours = 0;
                mins = 0;
                secs = 0;
            }

            int[] ints = {hours , mins , secs};
            return ints;
        }
    }
}
