package elements;

import util.Bearing;

/**
 * The {@code Window} class represents the Window object, which the user can interact with. A user can choose to
 * obstruct windows as they please.
 *
 * @author Émilie Martin
 * @author Philippe Vo
 */
public class Window {
    /**
     * If not provided by the user, the system uses these default values.
     */
    private final boolean DEFAULT_WINDOW_LOCATION = true;
    private final boolean DEFAULT_WINDOW_OBSTRUCTION = false;

    private boolean located;
    private boolean isObstructed;
    private Bearing wall;

    /**
     * Contructs a window with the default location and obstruction state.
     */
    public Window() {
        this.located = DEFAULT_WINDOW_LOCATION;
        this.isObstructed = DEFAULT_WINDOW_OBSTRUCTION;
    }

    /**
     * Constructs a Window with the given location, obstructed state, and wall.
     *
     * @param loc This boolean refers to whether a window is located on a wall or not. Each room contains a
     *         Window array of length 4 to signify each wall (N-E-S-W)
     * @param obstr This boolean refers to the state of the window, be it obstructed or not. If the boolean is
     *         true, then it is obstructed. If it is false, it is not obstructed.
     * @param wall the wall that this window is on
     */
    public Window(boolean loc, boolean obstr, Bearing wall) {
        this.located = loc;
        this.isObstructed = obstr;
        this.wall = wall;
    }

    /**
     * Outputs the state of the window to the user.
     */
    public boolean isObstructedString() {
        return isObstructed;
    }

    /**
     * @return the wall that this {@code Window} is on
     */
    public Bearing getWall() {
        return wall;

    }

    /**
     * This setter allows the user to obstruct a window.
     *
     * @param obstr The new window state
     */
    public void setObstructed(boolean obstr) {
        this.isObstructed = obstr;
    }

    @Override
    public String toString() {
        return wall.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Window)) {
            return false;
        }
        Window window = (Window) obj;
        return located == window.located && isObstructed == window.isObstructed;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(located);
        result = prime * result + Boolean.hashCode(isObstructed);
        return result;
    }
}