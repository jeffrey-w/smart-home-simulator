package main.model.elements;

import main.model.AbstractManipulable;

/**
 * The {@code Window} class represents the Window object, which the user can interact with. A user can choose to
 * obstruct windows as they please.
 *
 * @author Émilie Martin
 * @author Philippe Vo
 */
public class Window extends AbstractManipulable {

    // If not provided by the user, the system uses these default values.
    private static final boolean DEFAULT_OPEN_VALUE = false;
    private static final boolean DEFAULT_OBSTRUCTED_VALUE = false;

    private boolean open;
    private boolean blocked;

    /**
     * Constructs a {@code Window} with a {@value DEFAULT_OPEN_VALUE} open state and a {@value DEFAULT_OBSTRUCTED_VALUE}
     * obstructed state.
     */
    public Window() {
        this(DEFAULT_OPEN_VALUE, DEFAULT_OBSTRUCTED_VALUE);
    }

    /**
     * Constructs a {@code Window} with the specified {@code open} and {@code blocked} state.
     *
     * @param open The specified open state
     * @param blocked The specified blocked state
     */
    public Window(boolean open, boolean blocked) {
        setOpen(open);
        setBlocked(blocked);
    }

    /**
     * @return The open state of this {@code Window}
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @return The blocked state of this {@code Window}
     */
    public boolean isBlocked() {
        return blocked;
    }

    /**
     * Sets the {@code open} state of this {@code Window} to that specified.
     *
     * @param open The specified open state
     * @throws IllegalStateException If this {@code Window} is blocked
     */
    public void setOpen(boolean open) {
        if (blocked) {
            throw new IllegalStateException("Please unblock this window first.");
        }
        this.open = open;
    }

    /**
     * Sets the {@code blocked} state of this {@code Window} to that specified.
     *
     * @param blocked The specified obstructed state
     */
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Window)) {
            return false;
        }
        Window window = (Window) obj;
        return open == window.open && blocked == window.blocked;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(open);
        result = prime * result + Boolean.hashCode(blocked);
        return result;
    }

}