package main.model.parameters;

import main.model.Manipulable;
import main.model.elements.Room;
import main.model.elements.TemperatureControlZone;
import main.model.parameters.permissions.*;

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
    public static final double DEFAULT_TEMPERATURE = 15;
    public static final double MIN_TEMPERATURE = -100;
    public static final double MAX_TEMPERATURE = 100;
    public static final double DEFAULT_WINTER_TEMPERATURE = 25;
    public static final double DEFAULT_SUMMER_TEMPERATURE = 15;

    /**
     * The default values for away light beginning and end.
     */

    public static final int DEFAULT_TIMEX = 1;
    public static final int MIN_TIMEX = 1;
    public static final int MAX_TIMEX = 999;

    private Permission permission;
    private final Map<String, Permission> actors;
    private String location;
    private Date date;
    private double externalTemperature;
    private double defWinterTemp;
    private double defSummerTemp;
    private boolean on;
    private boolean autoLight;
    private final AwayMode awayMode;
    private Map<String, Permission> permissions;
    private final Clock clock = new Clock();
    private final Map<String, TemperatureControlZone> zones;

    /**
     * Constructs a new {@code Parameters} object.
     */
    public Parameters() {
        permission = null;
        actors = new HashMap<>();
        location = null;
        date = Date.from(Instant.now());
        externalTemperature = DEFAULT_TEMPERATURE;
        defWinterTemp = DEFAULT_WINTER_TEMPERATURE;
        defSummerTemp = DEFAULT_SUMMER_TEMPERATURE;
        on = false;
        awayMode = new AwayMode();
        permissions = new HashMap<>();
        zones = new HashMap<>();
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
        return (permissions.get(type));
    }

    /**
     * Adds a new actor to the simulation with the specified {@code name} and {@code permission}.
     *
     * @param name A unique identifier
     * @param permission The {@code Permission} level of the newly added actor
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _]) and whitespace
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
     * @return The current external temperature (everything outside the {@code House} set by the user
     */
    public double getExternalTemperature() {
        return externalTemperature;
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
     * (i.e. [a-z, A-Z, 0-9, _]) and whitespace
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
     * Sets the current {@code externalTemperature} of the simulation to that specified.
     *
     * @param temperature The newly specified external temperature
     * @throws IllegalArgumentException If the specified {@code temperature} is above {@value #MAX_TEMPERATURE} or below
     * {@value #MIN_TEMPERATURE}
     */
    public void setExternalTemperature(double temperature) {
        if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
            throw new IllegalArgumentException("You've specified an invalid temperature.");
        }
        this.externalTemperature = temperature;
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
     * @return The {@code AwayMode} of this {@code Parameters}
     */
    public Manipulable getAwayMode() {
        return awayMode;
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
        return clock.getTime();
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
        clock.setTime(time);
    }

    /**
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
        this.permissions = Objects.requireNonNull(permissions);
    }

    /**
     * @return The start of away light mode
     */
    public LocalTime getAwayLightStart() {
        return awayMode.getDefaultAwayLightStart();
    }

    /**
     * Sets the {@code start} of away light mode to the time specified
     *
     * @param start the specified start of away light mode
     * @throws NullPointerException if the specified {@code start} is {@code null}
     */
    public void setAwayLightStart(LocalTime start) {
        awayMode.setAwayLightStart(start);
    }

    /**
     * @return The end of away light mode
     */
    public LocalTime getAwayLightEnd() {
        return awayMode.getAwayLightEnd();
    }

    /**
     * Sets the {@code end} of away light mode to the time specified
     *
     * @param end the specified end of away light mode
     * @throws NullPointerException if the specified {@code end} is {@code null}
     */
    public void setAwayLightEnd(LocalTime end) {
        awayMode.setAwayLightEnd(end);
    }

    /**
     * Adds a {@code TemperatureControlZone}
     *
     * @param id The {@code TemperatureControlZone} identifier
     * @return The newly added {@code TemperatureControlZone}
     * @throws IllegalArgumentException If the specified {@code id} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _]) and whitespace, or if another {@code TemperatureControlZone} by that name already exists
     * @throws NullPointerException If the specified {@code id} is {@code} null
     */
    public TemperatureControlZone addZone(String id) {
        if (zones.containsKey(validateName(id))) {
            throw new IllegalArgumentException("A zone with that name already exists.");
        }
        TemperatureControlZone zone = new TemperatureControlZone();
        zones.putIfAbsent(validateName(id), zone);
        return zone;
    }

    /**
     * Removes a @code TemperatureControlZone}
     *
     * @param id The {@code TemperatureControlZone} identifier
     */
    public void removeZone(String id) {
        zones.remove(id);
    }

    /**
     * @param room The inquired {@code room}
     * @return The {@code TemperatureControlZone} that contains the given {@code room}
     */
    public TemperatureControlZone getTemperatureControlZone(String room) {
        for (TemperatureControlZone zone : zones.values()) {
            if (zone.getRooms().contains(room)) {
                return zone;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * Determines whether or not the specified {@code location} has had its desired temperature explicitly set instead
     * of relying on the one specified by the {@code TemperatureControlZone} it is in.
     *
     * @param location The specified location
     * @return {@code true} if the specified {@code location} has had its temperature overridden
     */
    public boolean isTemperatureOverridden(String location) {
        try {
            return getTemperatureControlZone(location).isOverridden(location);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * @return The default {@code Home} temperature during winter
     */
    public double getDefaultWinterTemperature() {
        return defWinterTemp;
    }

    /**
     * @return The default {@code Home} temperature during summer
     */
    public double getDefaultSummerTemperature() {
        return defSummerTemp;
    }

    /**
     * Sets the default {@code Home} temperature during winter to the specified value.
     *
     * @param temp The specified value
     */
    public void setDefaultWinterTemperature(double temp) {
        defWinterTemp = temp;
    }

    /**
     * Sets the default {@code Home} temperature during summer to the specified value.
     *
     * @param temp The specified value
     */
    public void setDefaultSummerTemperature(double temp) {
        defSummerTemp = temp;
    }

    /**
     * @return The speed of this {@code Parameters}' {@code Clock}
     */
    public int getTimeMultiplier() {
        return clock.getMultiplier();
    }

    /**
     * @return The names of the {@code TemperatureControlZone}s that belong to these {@code Parameters}
     */
    public Collection<String> getZones() {
        return zones.keySet();
    }

    /**
     * Provides the {@code TemperatureControlZone} with the specified {@code id}, or {@code null} if no such zone exists
     * in these {@code Parameters}
     *
     * @param id The specified id
     * @return The {@code TemperatureControlZone} with the specified {@code id}
     */
    public TemperatureControlZone getZone(String id) {
        return zones.get(id);
    }

}