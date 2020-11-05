package main.model.elements;

/**
 * The {@code Light} class represents a light object, which the user can interact with. A user can choose to turn on/off
 * lights as they please.
 *
 * @author Émilie Martin
 */
public class Light extends AbstractManipulable {

    // If not provided by the user, the system uses this default value.
    private static final boolean DEFAULT_ON_VALUE = true;

    private boolean on;
    private boolean autoMode;

    /**
     * Constructs a {@code Light} object with a {@value DEFAULT_ON_VALUE} state.
     */
    public Light() {
        this(DEFAULT_ON_VALUE);
    }

    /**
     * Constructs a {@code Light} object with the specified {@code on} state.
     *
     * @param on The specified on state
     */
    public Light(boolean on) {
        setOn(on);
    }

    /**
     * @return The on state of this {@code Light}
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Sets the {@code on} state of this {@code Light} to that specified.
     *
     * @param state The specified on state
     */
    public void setOn(boolean state) {
        this.on = state;
    }

    public boolean isAutoMode() {
        return autoMode;
    }

    /**
     * Sets the {@code autoMode} state of this {@code Light} to that specified.
     *
     * @param state The specified on state
     */
    public void setAutoMode(boolean state) {
        this.autoMode = state;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Light)) {
            return false;
        }
        return on == ((Light) obj).on;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(on);
        return result;
    }
}