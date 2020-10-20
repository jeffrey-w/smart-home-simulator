package main.model.elements;

/**
 * The {@code Door} class represents a door object that the user can interact with. A user can lock and unlock doors as
 * they please.
 *
 * @author Émilie Martin
 * @author Philippe Vo
 */

public class Door {

    //If not provided by the user, the system uses these default values.
    private static final boolean DEFAULT_DOOR_LOCATION = true;
    private static final boolean DEFAULT_DOOR_LOCK = false;

    private final boolean located;
    private boolean isLocked;

    /**
     * Constructs a {@code Door} with the default location and locked state.
     */
    public Door() {
        this.located = DEFAULT_DOOR_LOCATION;
        this.isLocked = DEFAULT_DOOR_LOCK;
    }

    /**
     * Constructs a {@code Door} with a given location and locked state.
     *
     * @param loc This boolean refers to whether a door is located on a wall or not. Each room contains a Door
     *         array of length 4 to signify each wall (N-E-S-W)
     * @param locked This boolean refers to the state of the door, be it locked or not. If the boolean is false,
     *         then it is unlocked. If it is true, it is locked.
     */
    public Door(boolean loc, boolean locked) {
        this.located = loc;
        this.isLocked = locked;
    }

    /**
     * This setter allows the user to lock a door.
     *
     * @param locked The new door state.
     */
    void setLocked(boolean locked) {
        this.isLocked = locked;
    }

    /**
     * Outputs the state of the door to the user.
     */
    void isLockedString() {
        if (this.isLocked) {
            System.out.println("This door is locked.");
        } else {
            System.out.println("This door is not locked.");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Door)) {
            return false;
        }
        Door door = (Door) obj;
        return located == door.located && isLocked == door.isLocked;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(located);
        result = prime * result + Boolean.hashCode(isLocked);
        return result;
    }
}
