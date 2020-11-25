package main.model.parameters;

import main.model.AbstractManipulable;

import java.time.LocalTime;
import java.util.Objects;

/**
 * The AwayMode is activated by the user when they leave the simulated house and no other user is in the house.
 * It allows the remote user to be alerted, should anyone suspicious be detected around or in the house.
 * The simulation user can set a specified delay, after which authorities will be alerted of suspicious activities.
 *
 * @author Émilie Martin
 */
public class AwayMode extends AbstractManipulable {

    private static final boolean AWAY_MODE_ON = false;
    private static final int AWAY_MODE_DELAY = 10_000; // in seconds
    public static final LocalTime DEFAULT_AWAY_LIGHT_START = LocalTime.of(18, 0);
    public static final LocalTime DEFAULT_AWAY_LIGHT_END = LocalTime.of(0, 0);

    private boolean on;
    private int delay;
    private LocalTime awayLightStart;
    private LocalTime awayLightEnd;

    /**
     * Constructs an AwayMode object with the default parameters.
     */
    public AwayMode() {
        setAwayMode(AWAY_MODE_ON);
        setAwayModeDelay(AWAY_MODE_DELAY);
        awayLightStart = DEFAULT_AWAY_LIGHT_START;
        awayLightEnd = DEFAULT_AWAY_LIGHT_END;
    }

    /**
     * @return The AwayMode state
     */
    public boolean getAwayMode() {
        return (this.on);
    }

    /**
     * Allows the user to set {@code AwayMode} to specified value.
     *
     * @param bool The given value of {@code AwayMode}. If true turns ON, else turns OFF.
     */
    public void setAwayMode(boolean bool) {
        this.on = bool;
    }

    /**
     * @return The AwayMode delay
     */
    public int getAwayModeDelay() {
        return (this.delay);
    }

    /**
     * Allows the user to change the {@code AwayMode} delay to that of the specified value.
     *
     * @param delay The specified delay value (in seconds)
     */
    public void setAwayModeDelay(int delay) {
        this.delay = delay;
    }

    /**
     *
     * @return The start of away light mode
     */
    public LocalTime getDefaultAwayLightStart() {
        return awayLightStart;
    }

    /**
     *
     * @return The end of away light mode
     */
    public LocalTime getAwayLightEnd() {
        return awayLightEnd;
    }

    /**
     * Sets the {@code start} of away light mode to the time specified
     * @param start the specified start of away light mode
     * @throws NullPointerException if the specified {@code start} is {@code null}
     */
    public void setAwayLightStart(LocalTime start) {
        awayLightStart = Objects.requireNonNull(start);
    }

    /**
     * Sets the {@code end} of away light mode to the time specified
     * @param end the specified end of away light mode
     * @throws NullPointerException if the specified {@code end} is {@code null}
     */
    public void setAwayLightEnd(LocalTime end) {
        awayLightEnd = Objects.requireNonNull(end);
    }

}
