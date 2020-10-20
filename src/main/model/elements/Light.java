package main.model.elements;

/**
 * The {@code Light} class represents a light object, which the user can interact with. A user can choose to turn on/off
 * lights as they please.
 *
 * @author Émilie Martin
 */
public class Light {

    // If not provided by the user, the system uses these default values.
    private final boolean DEFAULT_LIGHT_PRESENCE = true;
    private final boolean DEFAULT_LIGHT_ON = true;

    private final boolean isPresent;
    private boolean isOn;

    /**
     * Constructs a {@code Light} object with the default presence and state.
     */
    public Light() {
        this.isPresent = DEFAULT_LIGHT_PRESENCE;
        this.isOn = DEFAULT_LIGHT_ON;
    }

    /**
     * Constructs a {@code Light} object with a given presence and state.
     *
     * @param pres This boolean serves to determine if there is a {@code Light} object in this Room or not. If
     *         true, there is a light in this room. If false, there isn't.
     * @param on This boolean serves to determine if the light is on or not.
     */
    public Light(boolean pres, boolean on) {
        this.isPresent = pres;
        this.isOn = on;
    }

    /**
     * By setting the state, the user can turn lights on or off.
     *
     * @param state The new light state. If the boolean is true, it is on. If it is false, it is off.
     */
    void setOnState(boolean state) {
        this.isOn = state;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Light)) {
            return false;
        }
        Light light = (Light) obj;
        return isPresent == light.isPresent && isOn == light.isOn;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + Boolean.hashCode(isPresent);
        result = prime * result + Boolean.hashCode(isOn);
        return result;
    }
}