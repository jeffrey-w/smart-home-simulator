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
     * @throws IllegalArgumentException if the specified {@code name} is not a non-empty string of word
     *         characters (i.e. [a-z, A-Z, 0-9, _])
     * @throws NullPointerException if the specified {@code permission} is {@code null}
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

}
