package main.model.elements;

import java.util.Arrays;

/**
 * A {@code Room} is comprised of many house main.model.elements, such as {@code Door}s, {@code Light}s, {@code
 * Window}s.
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
     * @return All doors contained within this {@code Room}
     */
    public Door[] getDoors() {
        return this.doors;
    }

    /**
     * @return All lights contained within this {@code Room}
     */
    public Light[] getLights() {
        return this.lights;
    }

    /**
     * @return All windows contained within this {@code Room}
     */
    public Window[] getWindows() {
        return this.windows;
    }

    /**
     * Setter to change the temperature of this {@code Room}
     *
     * @param temp The new temperature
     */
    public void setTemperature(int temp) {
        this.temperature = temp;
    }

    /**
     * Sets the {@code obstructed} status of the {@code Window} at the specified {@code wall} to that specified.
     *
     * @param wall The specified wall
     * @param obstructed The specified obstructed status
     * @throws IllegalArgumentException if the specified {@code wall} does not have a {@code Window}
     */
    public void setObstructed(int wall, boolean obstructed) {
        try {
            windows[wall].setObstructed(obstructed);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("There is no window on that wall");
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Room)) {
            return false;
        }
        Room room = (Room) obj;
        return (temperature == room.temperature) && Arrays.equals(doors, room.doors) && Arrays
                .equals(lights, room.lights) && Arrays.equals(windows, room.windows);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + temperature;
        result = prime * result + Arrays.hashCode(doors);
        result = prime * result + Arrays.hashCode(lights);
        result = prime * result + Arrays.hashCode(windows);
        return result;
    }

}