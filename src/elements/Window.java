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
    public final boolean DEFAULT_WINDOW_LOCATION = true;
    public final boolean DEFAULT_WINDOW_OBSTRUCTION = false;

    private boolean located;
    private boolean isObstructed;

    /**
     * Contructs a window with the default location and obstruction state.
     */
    public Window() {
        this.located = DEFAULT_WINDOW_LOCATION;
        this.isObstructed = DEFAULT_WINDOW_OBSTRUCTION;
    }

    /**
     * Constructs a Window with the given location and obstruction state.
     *
     * @param loc This boolean refers to whether a window is located on a wall or not.
     *            Each room contains a Window array of length 4 to signify each wall (N-E-S-W)
     * @param obstr This boolean refers to the state of the window, be it obstructed or not.
     *              If the boolean is true, then it is obstructed. If it is false, it is not obstructed.
     */
    public Window(boolean loc, boolean obstr) {
        this.located = loc;
        this.isObstructed = obstr;
    }

    /**
     * This setter allows the user to obstruct a window.
     *
     * @param obstr The new window state
     */
    public void setObstructed(boolean obstr){
        this.isObstructed = obstr;
    }

    /**
     * Outputs the state of the window to the user.
     */
    void isObstructedString() {
        if(this.isObstructed) {
            System.out.println("This window is obstructed.");
        }
        else {
            System.out.println("This window is not obstructed.");
        }
    }
}