package main.model.parameters;

import javax.swing.*;
import java.time.LocalTime;

/**
 * The {@code Clock} class represents a clock object that will be used to keep track of time. It will be running a
 * thread that will continuously be incrementing clock by 1 (default) every 1 second.
 *
 * @author Philippe Vo
 */
public class Clock {

    private static final int NUM_FIELDS = 3;
    private static final int HOURS = 0, MINUTES = 1, SECONDS = 2;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * SECONDS_PER_MINUTE;
    private static final int SECONDS_PER_MILLISECOND = 1000;
    private static final int HOURS_PER_DAY = 24;

    private final int[] clockTime;
    private long referenceTime;
    private int multiplier;

    /**
     * Constructs a {@code Clock} that will be used to keep track of time. It will be running a thread that will
     * continuously be looking at the system clock every 1 second.
     */
    public Clock() {
        LocalTime now = LocalTime.now();
        Timer timer;
        clockTime = new int[NUM_FIELDS];
        referenceTime = now.getHour() * SECONDS_PER_HOUR + now.getMinute() * SECONDS_PER_MINUTE + now.getSecond();
        multiplier = 1;
        timer = new Timer(SECONDS_PER_MILLISECOND, e -> {
            referenceTime = referenceTime + multiplier; // increment clock
            clockTime[HOURS] = (int) referenceTime / SECONDS_PER_HOUR;
            if (clockTime[HOURS] < HOURS_PER_DAY) {
                clockTime[MINUTES] = ((int) referenceTime % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
                clockTime[SECONDS] = (int) (referenceTime % SECONDS_PER_MINUTE);
            } else {
                referenceTime = clockTime[HOURS] = clockTime[MINUTES] = clockTime[SECONDS] = 0;
            }
        });
        timer.start();
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
    public void setTime(int[] time) {
        int h = time[HOURS];
        int m = time[MINUTES];
        int s = time[SECONDS];

        referenceTime = (h * SECONDS_PER_HOUR) + (m * SECONDS_PER_MINUTE) + s;
    }

    /**
     * @return {@code int[]} the clock time
     */
    public int[] getClockTime() {
        return clockTime;
    }

}
