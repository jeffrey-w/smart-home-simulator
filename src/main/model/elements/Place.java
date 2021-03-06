package main.model.elements;

import main.model.parameters.permissions.Permission;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static main.util.NameValidator.validateName;

/**
 * A place that a person can occupy in the simulation
 *
 * @author Ayman Shehri
 * @see Room
 * @see Yard
 */
public abstract class Place {

    private final Map<String, Permission> people;

    /**
     * Constructs a new {@code Place} object.
     */
    public Place() {
        people = new HashMap<>();
    }

    /**
     * Adds a person to this {@code Place}.
     *
     * @param name The name of the person/user to be added
     * @param permission The {@code Permission} level of the added person
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word
     *         characters (i.e. [a-z, A-Z, 0-9, _]) and whitespace
     * @throws NullPointerException If the specified {@code permission} is {@code null}
     */
    public void addPerson(String name, Permission permission) {
        people.put(validateName(name), Objects.requireNonNull(permission, "Please select a permission level."));
    }

    /**
     * Remove the specified {@code person} from this {@code Place}
     *
     * @param person The specified person
     * @return {@code true} if the specified {@code person} was removed from this {@code Place}.
     */
    public boolean removePerson(String person) {
        return people.remove(person) != null;
    }

    /**
     *
     * @return The number of people in this {@code Place}
     */
    public int getNumberOfPeople() {
        return people.size();
    }

    /**
     * Determines whether or not this {@code Place} has any people in it.
     *
     * @return {@code true} if any people are in this {@code Place}
     */
    public boolean isOccupied() {
        return getNumberOfPeople() > 0;
    }
}
