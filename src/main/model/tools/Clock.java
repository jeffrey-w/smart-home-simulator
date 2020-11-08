package main.model.tools;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Calendar;
/**
 * The {@code Clock} class represents a clock object that will be used to keep track of time. It will be running a thread that
 * will continuously be looking at the system clock every 1 second.
 *
 * @author Philippe Vo
 */
public class Clock {

    private int[] clockTime;
    private final long offset;
    private int multiplier;

    /**
     * Constructs a {@code Clock} that will be used to keep track of time. It will be running a thread that
     * will continously be looking at the system clock every 1 second.
     */
    public Clock(){
        offset = System.currentTimeMillis();
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
     * @return {@code int[]} the clock time
     */
    public int[] getClockTime() {
        return clockTime;
    }

    // this method is called every 1 second by the javax.swing.Timer class timerClock
    class ClockListener implements ActionListener {
        private long realSeconds;
        private long apparentSeconds;

        public void actionPerformed(ActionEvent e) {
            realSeconds = getSeconds();
            apparentSeconds = realSeconds * multiplier; // seconds that can be "sped up"
            clockTime = splitToComponentTimes(apparentSeconds);
        }

        public long getSeconds()
        {
            long seconds = ((System.currentTimeMillis() - offset) / 1000);
            return seconds;
        }

        public int[] splitToComponentTimes(long totalSeconds)
        {
            int hours = (int) totalSeconds / 3600;
            int remainder = (int) totalSeconds - hours * 3600;
            int mins = remainder / 60;
            remainder = remainder - mins * 60;
            int secs = remainder;

            int[] ints = {hours , mins , secs};
            return ints;
        }
    }
}
