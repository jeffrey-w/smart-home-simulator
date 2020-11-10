package main.model.parameters;

import main.model.Manipulable;
import main.model.elements.Room;
import main.model.parameters.permissions.Permission;
import main.model.tools.Clock;
import main.model.parameters.permissions.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.time.LocalTime;
import java.util.*;

import static main.util.NameValidator.validateName;

/**
 * The {@code Parameters} class specifies those simulation attributes that are under direct control of a user and not an
 * actor in the simulation. Such attributes include the User's own {@code Permission} level in the simulation, the
 * number and {@code Permission}s of other actors, the {@code Room} they occupy, and the current temperature and date.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 * @see Permission
 * @see Room
 */
public class Parameters {

    /**
     * The temperature the application is initialized to upon start up.
     */
    public static final int DEFAULT_TEMPERATURE = 15;
    public static final int MIN_TEMPERATURE = -100;
    public static final int MAX_TEMPERATURE = 100;

    /**
     * The default values for away light beginning and end.
     */
    public static final LocalTime DEFAULT_AWAY_LIGHT_START = LocalTime.of(18, 0);
    public static final LocalTime DEFAULT_AWAY_LIGHT_END = LocalTime.of(0, 0);

    public static final int DEFAULT_TIMEX = 1;
    public static final int MIN_TIMEX = 1;
    public static final int MAX_TIMEX = 999;

    public static final int DEFAULT_HOURS = 0;
    public static final int MIN_HOURS = 0;
    public static final int MAX_HOURS = 23;

    public static final int DEFAULT_MINS = 0;
    public static final int MIN_MINS = 0;
    public static final int MAX_MINS = 59;

    public static final int DEFAULT_SECS = 0;
    public static final int MIN_SECS = 0;
    public static final int MAX_SECS = 59;

    // starting the clock
    private static final Clock clock = new Clock();

    private Permission permission;
    private final Map<String, Permission> actors;
    private String location;
    private Date date;
    private int temperature;
    private boolean on;
    private boolean autoLight;
    private AwayMode awayMode;
    private int[] clockTime;

    private Map<String, Permission> permissions;

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
        awayMode = new AwayMode();
        // setting the time and time multiplier
        startUpdateClock(); // start the listener to update time given the clock time
        clockTime = clock.getClockTime();
        permissions = new HashMap<>();
        fillPermissionMap();
    }

    /**
     * Populates the Permission HashMap with all permission types.
     */
    private void fillPermissionMap() {
        permissions.put("Parent", new ParentPermission());
        permissions.put("Child", new ChildPermission());
        permissions.put("Guest", new GuestPermission());
        permissions.put("Stranger", new StrangerPermission());
    }

    /**
     * @param type The specified permission type
     * @return The permission of the specified type
     */
    public Permission getPermissionOf(String type) {
        return(permissions.get(type));
    }

    /**
     * Adds a new actor to the simulation with the specified {@code name} and {@code permission}.
     *
     * @param name A unique identifier
     * @param permission The {@code Permission} level of the newly added actor
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _])
     * @throws NullPointerException If the specified {@code permission} is {@code null}
     */
    public void addActor(String name, Permission permission) {
        actors.put(validateName(name), Objects.requireNonNull(permission, "Please select a permission level."));
    }

    /**
     * Removes the actor with the specified {@code name} from the simulation. If no actor with the specified {@code
     * name} exists in the simulation, this method has no effect.
     *
     * @param name The unique identifier of the actor to be removed
     */
    public void removeActor(String name) {
        actors.remove(name);
    }

    /**
     * @return The {@code Permission} level of the user controlling the simulation
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * @return The unique identifiers of the actors added to the simulation
     */
    public Set<String> getActorsIdentifier() {
        return Collections.unmodifiableSet(actors.keySet());
    }

    /**
     * @return The List of the actors added to the simulation along with their permission
     */
    public Map<String, Permission> getActors() {
        return actors;
    }

    /**
     * @return The location of the user controlling the simulation
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return The current date set by the user
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return The current temperature set by the user
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
     * @param permission The specified {@code Permission} level
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Sets the {@code location} of the user to that specified. A {@code null} location is permitted.
     *
     * @param location The specified location
     * @throws IllegalArgumentException If the specified {@code location} is not a non-empty string of word characters
     * (i.e. [a-z, A-Z, 0-9, _])
     */
    public void setLocation(String location) {
        this.location = location == null ? null : validateName(location);
    }

    /**
     * Sets the current {@code date} of the simulation to that specified.
     *
     * @param date The specified date
     * @throws NullPointerException If the specified {@code date} is {@code null}
     */
    public void setDate(Date date) {
        this.date = Objects.requireNonNull(date);
    }

    /**
     * Sets the current {@code temperature} of the simulation to that specified.
     *
     * @param temperature The specified temperature
     * @throws IllegalArgumentException If the specified {@code temperature} is above {@value #MAX_TEMPERATURE} or below
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
     * @param on The specified state of the simulation
     */
    public void setOn(boolean on) {
        this.on = on;
    }

    /**
     * Provides the {@code Permission} level of the specified {@code actor}.
     *
     * @param actor The specified actor
     * @return The {@code Permission} level of the specified {@code actor} or {@code null} if the specified {@code
     * actor} does not exist
     */
    public Permission permissionOf(String actor) {
        return actors.get(actor);
    }

    /**
     * <<<<<<< HEAD
     *
     * @return The {@code AwayMode} of this {@code Parameters}
     */
    public Manipulable getAwayMode() {
        return awayMode;
    }

    /**
     * ======= >>>>>>> origin/dev2-pv-clock Sets the current actors of the simulation to that specified. Used when
     * loading the list of actors from a file
     *
     * @param actors The loaded or specified actors of the simulation
     */
    public void setActors(HashMap<String, Permission> actors) {
        this.actors.clear();
        for (Map.Entry<String, Permission> item : actors.entrySet()) {
            this.actors.put(validateName(item.getKey()), item.getValue());
        }
    }

    /**
     * @return The AwayMode state: {@code true} if on, else {@code false}
     */
    public boolean isAwayMode() {
        return awayMode.getAwayMode();
    }

    /**
     * Set AwayMode to specified value.
     *
     * @param on The new AwayMode state
     */
    public void setAwayMode(boolean on) {
        awayMode.setAwayMode(on);
    }

    /**
     * @return The AwayMode delay (in seconds)
     */
    public int getAwayDelay() {
        return awayMode.getAwayModeDelay();
    }

    /**
     * Set AwayMode delay to specified value.
     *
     * @param delay The new AwayMode delay
     */
    public void setAwayDelay(int delay) {
        awayMode.setAwayModeDelay(delay);
    }

    /**
     * @return {@code true} if auto light mode is turned on
     */
    public boolean isAutoLight() {
        return autoLight;
    }

    /**
     * Sets the auto light mode status of these {@code Parameters} to that specified.
     *
     * @param autoLight The specified auto light mode status
     */
    public void setAutoLight(boolean autoLight) {
        this.autoLight = autoLight;
    }

    /**
     * @return The time
     */
    public int[] getClockTime() {
        return clockTime;
    }

    /**
     * Set time multiplier
     *
     * @param clockTimeMultiplier is the time we want to multiply by
     */
    public void setClockTimeMultiplier(int clockTimeMultiplier) {
        clock.setMultiplier(clockTimeMultiplier);
    }

    /**
     * Set time
     *
     * @param time is the time we want to set
     */
    public void setTime(int[] time) {
        clock.updateTime(time);
    }

    // start the clock time listener (basically there is 2 threads, one for the clock and one to retreive the time
    // from that clock)
    public void startUpdateClock() {
        ActionListener updateClockListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clockTime = clock.getClockTime();
            }
        };

        javax.swing.Timer timerClock = new javax.swing.Timer(1000, updateClockListener);
        timerClock.start();
    }

    /**
     *
     * @return The {@code Permission} levels available to the simulation
     */
    public Map<String, Permission> getPermissions() {
        return permissions;
    }

    /**
     * Sets the {@code permission} levels available to the simulation to that specified.
     *
     * @param permissions The specified {@code Permission} levels
     * @throws NullPointerException if the specified {@code permission}s are {@code null}
     */
    public void setPermissions(Map<String, Permission> permissions) {
        this.permissions = Objects.requireNonNull(permissions); // TODO need more validation
    }
}

