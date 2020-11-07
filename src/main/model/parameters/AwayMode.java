package main.model.parameters;

import main.model.elements.AbstractManipulable;

/**
 * The AwayMode is activated by the user when they leave the simulated house and no other user is in the house.
 * It allows the remote user to be alerted, should anyone suspicious be detected around or in the house.
 * The simulation user can set a specified delay, after which authorities will be alerted of suspicious activities.
 *
 * @author Émilie Martin
 */
public class AwayMode extends AbstractManipulable {

    private static boolean AWAY_MODE_ON = false;
    private static int AWAY_MODE_DELAY = 10_000; // in seconds

    private boolean on;
    private int delay;

    /**
     * Constructs an AwayMode object with the default parameters.
     */
    public AwayMode() {
        setAwayMode(AWAY_MODE_ON);
        setAwayModeDelay(AWAY_MODE_DELAY);
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
}
