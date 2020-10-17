package elements;

/**
 * The {@code Light} class represents the Light object, which the user can interact with.
 * A user can choose to turn on/off lights as they please.
 *
 * @author Émilie Martin
 */
public class Light {

    /**
     * If not provided by the user, the system uses these default values.
     */
    private final boolean DEFAULT_PRESENT = true;
    private final boolean DEFAULT_STATE = true;

    private boolean isPresent;
    private boolean isOn;

    /**
     * Constructs a Light object with a given location and state.
     *
     * @param pres This boolean serves to determine if there is a {@code Light} object in this Room or not.
     *             If true, there is a light in this room. If false, there isn't.
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
}