package elements;

import permissions.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static util.NameValidator.validateName;

/**
 * A Room is comprised of many house elements, such as {@code Door}, {@code Light}, {@code Window}.
 *
 * @author Philippe Vo
 * @author Émilie Martin
 */
public class Room {
    private String name;
    private Door[] doors;
    private Light[] lights;
    private Window[] windows;
    private Map<String, Permission> people;

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
        this.doors = doors;
        this.lights = lights;
        this.windows = windows;
        people = new HashMap<>();
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

    /**
     * Add a person in a room
     *
     * @param name The name of the person/user to be added to the room
     * @param permission The permission to add this person to the room
     */
    public void addPerson(final String name, final Permission permission) {
        people.put(validateName(name), Objects.requireNonNull(permission));
    }

    /**
     * Remove a person from a room
     *
     * @param name The name of the person/user to remove from the list
     */
    public void removePerson(final String name) {
        // TODO: Do we need to consider people with the same name located in the same room?
        people.remove(name);
    }
}