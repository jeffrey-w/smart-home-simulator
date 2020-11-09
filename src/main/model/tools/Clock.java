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
 * will continuously be looking at the system clock every 1 second.
 *
 * @author Philippe Vo
 */
public class Clock {

    private int[] clockTime;
    private final long offset;
    private long referenceTime;
    private int multiplier;
    private Calendar cal;

    /**
     * Constructs a {@code Clock} that will be used to keep track of time. It will be running a thread that
     * will continously be looking at the system clock every 1 second.
     */
    public Clock(){
        offset = System.currentTimeMillis();
        referenceTime = System.currentTimeMillis();
        multiplier = 1;
        cal = Calendar.getInstance();

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
        private long realMillis;
        private long apparentMillis;
        private long apparentTime;
        private int h;
        private int m;
        private int s;

        public void actionPerformed(ActionEvent e) {
            realMillis = System.currentTimeMillis() - offset;
            apparentMillis = realMillis * multiplier; // seconds that can be "sped up"

            apparentTime = referenceTime + apparentMillis;

            cal.setTimeInMillis(apparentTime);

            h = cal.get(Calendar.HOUR);
            m = cal.get(Calendar.MINUTE);
            s = cal.get(Calendar.SECOND);

            clockTime = new int[] {h,m,s};
        }
    }
}
