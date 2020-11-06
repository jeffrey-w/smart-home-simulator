package main.model.elements;

import main.model.parameters.permissions.Action;

/**
 * The {@code Door} class represents a door object that the user can interact with. A user can lock and unlock doors as
 * they please.
 *
 * @author Émilie Martin
 * @author Philippe Vo
 */

public class Door extends AbstractManipulable {

    // If not provided by the user, the system uses these default values.
    private static final boolean DEFAULT_OPEN_VALUE = false;
    private static final boolean DEFAULT_LOCKED_VALUE = false;

    private boolean open;
    private boolean locked;

    /**
     * Constructs a {@code Door} with a {@value DEFAULT_OPEN_VALUE} open state a {@value DEFAULT_LOCKED_VALUE} locked
     * state.
     */
    public Door() {
        this(DEFAULT_OPEN_VALUE, DEFAULT_LOCKED_VALUE);
    }

    /**
     * Constructs a {@code Door} with the specified {@code open} {@code locked} state.
     *
     * @param open The specified open state
     * @param locked The specified locked state
     */
    public Door(boolean open, boolean locked) {
        setOpen(open);
        setLocked(locked);
    }

    /**
     *
     * @return The open state of this {@code Door}
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @return {@code true} if this {@code Door} is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the {@code open} state of this {@code Door} to that specified.
     *
     * @param open The specified open state
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Sets the {@code locked} state of this {@code Door} to that specified.
     *
     * @param locked The specified locked state
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Door)) {
            return false;
        }
        return locked == ((Door) obj).locked;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(locked);
        return result;
    }

}
