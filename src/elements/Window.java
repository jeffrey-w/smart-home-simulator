package elements;

/**
 * The {@code Window} class represents the Window object, which the user can interact with.
 * A user can choose to obstruct windows as they please.
 *
 * @author Émilie Martin
 * @author Philippe Vo
 */
public class Window {
    /**
     * If not provided by the user, the system uses these default values.
     */
    private final boolean DEFAULT_LOCATION = true;
    private final boolean DEFAULT_OBSTRUCTION = false;

    private boolean located;
    private boolean isObstructed;
    private ProximitySensor proximitySensor = null;

    /**
     * Constructs a Window with the given location and obstructed state.
     *
     * @param loc This boolean refers to whether a window is located on a wall or not.
     *            Each room contains a Window array of length 4 to signify each wall (N-E-S-W)
     * @param obstr This boolean refers to the state of the window, be it obstructed or not.
     *              If the boolean is true, then it is obstructed. If it is false, it is not obstructed.
     */
    public Window(boolean loc, boolean obstr) {
        this.located = loc;
        this.isObstructed = obstr;
        this.proximitySensor = new ProximitySensor(); // FIXME : Not sure if we should just create a sensor here out of "thin air"
    }
    // FIXME : Not sure if we should pass "obstr" into the contructor of the Window Class.
    // FIXME : [linked with] -> Not sure if we should have "obstructed" data in the "house JSON" file, it is only used to draw the house.

    /**
     * This setter allows the user to obstruct a window.
     *
     * @param obstr The new window state
     */
    public void setObstructed(boolean obstr){
        this.isObstructed = obstr;
    }
    // FIXME : Not sure if we want to set obstructed here, should come from a sensor that detects if there are any obstacles.

    /**
     * Outputs the state of the window to the user.
     */
    public boolean isBlocked() {
        boolean blockState = this.proximitySensor.detect();

        if(blockState) {
            System.out.println("This window is obstructed.");
        }
        else {
            System.out.println("This window is not obstructed.");
        }
        return blockState;
    }
    // FIXME : Modified how we detect the window block status by using a sensor since  this would make more sense with the theme of a "Smart Home"

    /**
     * Allows to access the sensor of the window
     */
    public ProximitySensor getProximitySensor() {
        return this.proximitySensor;
    }
}