package elements;

/**
 * A {@code Room} is comprised of many house elements, such as {@code Door}s, {@code Light}s, {@code Window}s.
 *
 * @author Philippe Vo
 * @author Émilie Martin
 * @see Door
 * @see Light
 * @see Window
 */
public class Room extends Place {

    private static final int DEFAULT_ROOM_TEMPERATURE = 25;

    private int temperature;
    private final Door[] doors;
    private final Light[] lights;
    private final Window[] windows;

    /**
     * Constructs a Room with the given doors, lights, and windows.
     *
     * @param doors An array of all doors contained within the room (location + lock state)
     * @param lights An array of all lights contained within the room (presence + on state)
     * @param windows An array of all windows contained within the room (location + obstruction state)
     */
    public Room(Door[] doors, Light[] lights, Window[] windows) {
        this.temperature = DEFAULT_ROOM_TEMPERATURE;
        this.doors = doors;
        this.lights = lights;
        this.windows = windows;
    }

    /**
     * Getter that returns the doors of a room
     *
     * @return All doors contained within a room
     */
    public Door[] getDoors() {
        return this.doors;
    }

    /**
     * Getter that returns the lights in a room
     *
     * @return All lights contained within a room
     */
    public Light[] getLights() {
        return this.lights;
    }

    /**
     * Getter that returns the windows of a room
     *
     * @return All windows contained within a room
     */
    public Window[] getWindows() {
        return this.windows;
    }

    /**
     * Setter to change the temperature of the room
     *
     * @param temp The new temperature
     */
    public void setTemperature(int temp) {
        this.temperature = temp;
    }

    /**
     * Sets the {@code obstructed} status of the {@code Window} at the specified {@code wall} to that specified.
     *
     * @param wall the specified wall
     * @param obstructed the specified obstructed status
     * @throws IllegalArgumentException if the specified {@code wall} does not have a {@code Window}
     */
    public void setObstructed(int wall, boolean obstructed) {
        try {
            windows[wall].setObstructed(obstructed);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("There is no window on that wall");
        }
    }

}