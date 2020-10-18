package elements;

import permissions.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static util.NameValidator.validateName;

/**
 * A place that a person can occupy in the simulation
 * @author Ayman Shehri
 */
public abstract class Place {
    private Map<String, Permission> people;

    /**
     * A Constructor that sets a new HashMap of the people who occupy this place.
     */
    public Place() {
        people = new HashMap<>();
    }

    /**
     * Add a person in a place
     *
     * @param name The name of the person/user to be added to the room
     * @param permission The permission to add this person to the room
     */
    public void addPerson(final String name, final Permission permission) {
        people.put(validateName(name), Objects.requireNonNull(permission));
    }

    /**
     * Remove a person from a place
     *
     * @param name The name of the person/user to remove from the list
     */
    public void removePerson(final String name) {
        // TODO: Do we need to consider people with the same name located in the same room?
        people.remove(name);
    }
}
