package parameters;

import permissions.Permission;

import java.time.Instant;
import java.util.*;

import static util.NameValidator.validateName;

/**
 * The {@code Parameters} class specifies those simulation attributes that are under direct control of a user and not an
 * actor in the simulation. Such attributes include the Users own {@code Permission} level in the simulation, the number
 * and {@code Permission}s of other actors, the room they occupy, and the current date and temperature.
 *
 * @author Jeff Wilgus
 */
public class Parameters {

    /**
     * The temperature the application is initialized to upon start up.
     */
    public static final int DEFAULT_TEMPERATURE = 15;
    public static final int MIN_TEMPERATURE = -100;
    public static final int MAX_TEMPERATURE = 100;

    private Permission permission;
    private final Map<String, Permission> actors;
    private String location;
    private Date date;
    private int temperature;
    private boolean on;

    /**
     * Constructs a new {@code Parameters} object.
     */
    public Parameters() {
        permission = null;
        actors = new HashMap<>();
        location = null;
        date = Date.from(Instant.now());
        temperature = DEFAULT_TEMPERATURE;
        on = false;
    }

    /**
     * Adds a new actor to the simulation with the specified {@code role} and {@code permission}.
     *
     * @param role an unique identifier
     * @param permission the {@code Permission} level of the newly added actor
     * @throws IllegalArgumentException if the specified {@code role} is not a non-empty string of word characters (i.e.
     * [a-z, A-z, 0-9, _])
     * @throws NullPointerException if the specified {@code permission} is {@code null}
     */
    public void addActor(String role, Permission permission) {
        actors.put(validateName(role), Objects.requireNonNull(permission));
    }

    /**
     * Removes the actor with the specified {@code role} from the simulation. If no actor with the specified {@code
     * role} exists in the simulation, this method has no effect.
     *
     * @param role the unique identifier of the actor to be removed
     */
    public void removeActor(String role) {
        actors.remove(role);
    }

    /**
     * @return the {@code Permission} level of the user controlling the simulation
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * @return the unique identifiers of the actors added to the simulation
     */
    public Set<String> getActors() {
        return Collections.unmodifiableSet(actors.keySet());
    }

    /**
     * @return the location of the user controlling the simulation
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the current date set by the user
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the current temperature set by the user
     */
    public int getTemperature() {
        return temperature;
    }

    /**
     * @return {@code true} if the user has started the simulation
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Sets the {@code permission} level of the user to that specified. A {@code null} {@code Permission} is permitted.
     *
     * @param permission the specified {@code Permission} level
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Sets the {@code location} of the user to that specified. A {@code null} location is permitted.
     *
     * @param location the specified location
     * @throws IllegalArgumentException if the specified {@code location} is not a non-empty string of word characters
     * (i.e. [a-z, A-z, 0-9, _])
     */
    public void setLocation(String location) {
        this.location = location == null ? null : validateName(location);
    }

    /**
     * Sets the current {@code date} of the simulation to that specified.
     *
     * @param date the specified date
     * @throws NullPointerException if the specified {@code date} is {@code null}
     */
    public void setDate(Date date) {
        this.date = Objects.requireNonNull(date);
    }

    /**
     * Sets the current {@code temperature} of the simulation to that specified.
     *
     * @param temperature the specified temperature
     * @throws IllegalArgumentException if the specified {@code temperature} is above {@value #MAX_TEMPERATURE} or below
     * {@value #MIN_TEMPERATURE}
     */
    public void setTemperature(int temperature) {
        if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException("You've specified an invalid temperature.");
        }
        this.temperature = temperature;
    }

    /**
     * Sets the current state of the simulation to that specified.
     *
     * @param on the specified state of the simulation
     */
    public void setOn(boolean on) {
        this.on = on;
    }

}
