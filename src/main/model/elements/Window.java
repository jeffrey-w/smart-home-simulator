package main.model.elements;

import main.model.parameters.permissions.Action;

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
    private boolean obstructed;

    /**
     * Constructs a {@code Window} with a {@value DEFAULT_OPEN_VALUE} open state and a {@value DEFAULT_OBSTRUCTED_VALUE}
     * obstructed state.
     */
    public Window() {
        this(DEFAULT_OPEN_VALUE, DEFAULT_OBSTRUCTED_VALUE);
    }

    /**
     * Constructs a {@code Window} with the specified {@code open} and {@code obstructed} state.
     *
     * @param open The specified open state
     * @param obstructed The specified obstructed state
     */
    public Window(boolean open, boolean obstructed) {
        setOpen(open);
        setObstructed(obstructed);
    }

    /**
     * @return The open state of this {@code Window}
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * @return The obstructed state of this {@code Window}
     */
    public boolean isObstructed() {
        return obstructed;
    }

    /**
     * Sets the {@code open} state of this {@code Window} to that specified.
     *
     * @param open The specified open state
     */
    public void setOpen(boolean open) {
        this.open = open;
    }

    /**
     * Sets the {@code obstructed} state of this {@code Window} to that specified.
     *
     * @param obstructed The specified obstructed state
     */
    public void setObstructed(boolean obstructed) {
        this.obstructed = obstructed;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Window)) {
            return false;
        }
        Window window = (Window) obj;
        return open == window.open && obstructed == window.obstructed;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(open);
        result = prime * result + Boolean.hashCode(obstructed);
        return result;
    }

}