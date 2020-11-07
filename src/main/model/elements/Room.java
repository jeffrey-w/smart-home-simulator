package main.model.elements;

import java.util.Arrays;
import java.util.Objects;

/**
 * A {@code Room} is comprised of many {@code House} elements, such as {@code Door}s, {@code Light}s, and {@code
 * Window}s.
 *
 * @author Philippe Vo
 * @author Émilie Martin
 * @see Door
 * @see Light
 * @see Window
 */
public class Room extends Place { // TODO needs comments

    private static final int DEFAULT_ROOM_TEMPERATURE = 25;

    private int temperature;
    private final Door[] doors;
    private final Light[] lights;
    private final Window[] windows;

    /**
     * Constructs a {@code Room} with the given {@code doors}, {@code lights}, and {@code windows}.
     *
     * @param doors A collection of {@code Door}s in this {@code Room}
     * @param lights A collection of {@code Light}s in this {@code Room}
     * @param windows A collection of {@code Window}s in this {@code Room}
     */
    public Room(Door[] doors, Light[] lights, Window[] windows) {
        this.temperature = DEFAULT_ROOM_TEMPERATURE;
        this.doors = Objects.requireNonNull(doors); // TODO consider making defensive copies in a future release
        this.lights = Objects.requireNonNull(lights);
        this.windows = Objects.requireNonNull(windows);
    }

    /**
     * @return All {@code Door}s contained within this {@code Room}
     */
    public Door[] getDoors() {
        return this.doors;
    }

    /**
     * @return All {@code Light}s contained within this {@code Room}
     */
    public Light[] getLights() {
        return this.lights;
    }

    /**
     * @return All {@code Window}s contained within this {@code Room}
     */
    public Window[] getWindows() {
        return this.windows;
    }

    /**
     * Sets the internal {@code temperature} of this {@code Room} to that specified.
     *
     * @param temperature The specified temperature
     */
    public void setTemperature(int temperature) { // TODO do bounds checking
        this.temperature = temperature;
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

    public int getNumberOfOpenDoors() {
        int count = 0;
        for (Door door : doors) {
            if (door != null && door.isOpen()) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfLockedDoors() {
        int count = 0;
        for (Door door : doors) {
            if (door != null && door.isLocked()) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfLightsOn() {
        int count = 0;
        for (Light light : lights) {
            if (light != null && light.isOn()) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfWindowsOpen() {
        int count = 0;
        for (Window window : windows) {
            if (window != null &&  window.isOpen()) {
                count++;
            }
        }
        return count;
    }

    public int getNumberOfWindowsBlocked() {
        int count = 0;
        for (Window window : windows) {
            if (window != null && window.isObstructed()) { // TODO rename blocked to obstructed or vice versa
                count++;
            }
        }
        return count;
    }

    public boolean isOccupied() {
        return getNumberOfPeople() > 0;
    }

    public void toggleLights(boolean flag) {
        for (Light light : lights) {
            if (light != null) {
                light.setOn(flag);
            }
        }
    }
}