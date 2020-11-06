package main.model.elements;

import java.util.Arrays;
import java.util.Objects;
import main.model.parameters.permissions.Action;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
public class Room extends Place {

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
     * runs a routine if there is a person added to the "Room'
     *
     * @return message depending on context
     */
    public String addRoutine(){
        // turn on the lights if there is someone in the room and if light is set to autoMode
        for (Light light : this.lights) {
            if (light.isAutoMode()) {
                if(light.isOn() == false){ // only turn on light if it was off
                    String msg = light.manipulate(Action.TOGGLE_LIGHT);
                    return msg;
                }
            }
        }

        // if there is no operations done
        return "no operations done";
    }

    /**
     * runs a routine if there is a person removed from the "Room'
     *
     * @return message depending on context
     */
    public String removeRoutine() {
        // turn off the lights if there is no one in the room
        if (this.getNumPeople() == 0) {
            // turn on the lights if there is someone in the room and if light is set to autoMode
            for (Light light : this.lights) {
                if (light.isAutoMode()) {
                    if (light.isOn() == true) { // only turn on light if it was on
                        String msg = light.manipulate(Action.TOGGLE_LIGHT);
                        return msg;
                    }
                }
            }
        }

        // if there is no operations done
        return "no operations done";
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

    static Random random = ThreadLocalRandom.current(); // TODO delete this

    public int getNumberOfOpenDoors() {
//        int count = 0;
//        for (Door door : doors) {
//            if (door.isOpen()) {
//                count++;
//            }
//        }
//        return count;
        return random.nextInt(10);
    }

    public int getNumberOfLockedDoors() {
//        int count = 0;
//        for (Door door : doors) {
//            if (door.isLocked()) {
//                count++;
//            }
//        }
//        return count;
        return random.nextInt(10);
    }

    public int getNumberOfLightsOn() {
//        int count = 0;
//        for (Light light : lights) {
//            if (light.isOn()) {
//                count++;
//            }
//        }
//        return count;
        return random.nextInt(10);
    }

    public int getNumberOfWindowsOpen() {
//        int count = 0;
//        for (Window window : windows) {
//            if (window.isOpen()) {
//                count++;
//            }
//        }
//        return count;
        return random.nextInt(10);
    }

    public int getNumberOfWindowsBlocked() {
//        int count = 0;
//        for (Window window : windows) {
//            if (window.isObstructed()) { // TODO rename blocked to obstructed or vice versa
//                count++;
//            }
//        }
//        return count;
        return random.nextInt(10);
    }
}