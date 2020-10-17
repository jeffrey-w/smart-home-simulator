package elements;

/**
 * A Room is comprised of many house elements, such as {@code Door}, {@code Light}, {@code Window}.
 *
 * @author Philippe Vo
 * @author Émilie Martin
 */
public class Room extends Place{
    private String name;
    private int temperature;
    private Door[] doors;
    private Light[] lights;
    private Window[] windows;

    private final int DEFAULT_ROOM_TEMPERATURE = 25;

    /**
     * Contructs a Room with the given name, doors, lights, and windows
     *
     * @param roomName The name of the room
     * @param doors An array of all doors contained within the room (location + lock state)
     * @param lights An array of all lights contained within the room (presence + on state)
     * @param windows An array of all windows contained within the room (location + obstruction state)
     */
    public Room(String roomName, Door[] doors, Light[] lights, Window[] windows) {
        this.name = roomName;
        this.temperature = DEFAULT_ROOM_TEMPERATURE;
        this.doors = doors;
        this.lights = lights;
        this.windows = windows;
    }

    /**
     * Setter to change the temperature of the room
     * @param temp The new temperature
     */
    public void setTemperature(int temp) {
        this.temperature = temp;
    }

    /**
     * Getter that returns the name of the room
     * @return The room name
     */
    public String getLocation() {
        return this.name;
    }

    /**
     * Getter that returns the doors of a room
     * @return All doors contained within a room
     */
    public Door[] getDoors() {
        return this.doors;
    }

    /**
     * Getter that returns the lights in a room
     * @return All lights contained within a room
     */
    public Light[] getLights() {
        return this.lights;
    }

    /**
     * Getting that returns the windows of a room
     * @return All windows contained within a room
     */
    public Window[] getWindows() {
        return this.windows;
    }
}