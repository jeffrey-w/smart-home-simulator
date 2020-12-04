package main.model.parameters;

import javax.swing.*;
import java.time.LocalTime;

/**
 * The {@code Clock} class is a facility for measuring the passage of time.
 *
 * @author Philippe Vo
 */
public class Clock {

    public static final int SECONDS_PER_MILLISECOND = 1000;
    private static final int NUM_FIELDS = 3;
    public static final int HOURS = 0, MINUTES = 1, SECONDS = 2;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * SECONDS_PER_MINUTE;
    private static final int HOURS_PER_DAY = 24;

    private final int[] clockTime;
    private long referenceTime;
    private int multiplier;

    /**
     * Constructs a {@code Clock} object.
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
     * @param multiplier the amount of time this {@code Clock} is incremented by per actual second.
     */
    public void setMultiplier(int multiplier) { // TODO validate multiplier
        this.multiplier = multiplier;
    }

    /**
     * @return The current time this {@code Clock} is set to
     */
    public int[] getTime() {
        return clockTime;
    }

    /**
     * Sets the {@code time} of this {@code Clock} to that specified.
     *
     * @param time the specified time
     */
    public void setTime(int[] time) { // TODO validate fields
        int h = time[HOURS];
        int m = time[MINUTES];
        int s = time[SECONDS];

        referenceTime = ((long) h * SECONDS_PER_HOUR) + ((long) m * SECONDS_PER_MINUTE) + s;
    }

}
