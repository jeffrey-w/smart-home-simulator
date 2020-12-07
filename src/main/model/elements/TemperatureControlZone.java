package main.model.elements;

import java.util.*;

/**
 * A {@code TemperatureControlZone} contains many rooms and can adjust the temperature of all contained rooms. The user
 * can add and remove rooms from a {@code TemperatureControlZone}.
 *
 * @author Émilie Martin
 * @author Ayman Shehri
 */
public class TemperatureControlZone {

    private static final double DEFAULT_DESIRED_TEMPERATURE = 25.00;
    private static final int NUM_PERIODS = 3;

    private static int validatePeriod(int period) {
        if (period < 0 || period >= NUM_PERIODS) {
            throw new IllegalArgumentException("That is not a valid period.");
        }
        return period;
    }

    private final Set<String> rooms;
    private final Map<String, Double> overridden;
    private final Double[] desiredTemperature;

    /**
     * Constructs a {@code TemperatureControlZone} with no rooms.
     */
    public TemperatureControlZone() {
        rooms = new HashSet<>();
        overridden = new HashMap<>();
        desiredTemperature = new Double[] {DEFAULT_DESIRED_TEMPERATURE, null, null};
    }

    /**
     * @return The rooms contained within this {@code TemperatureControlZone}
     */
    public Set<String> getRooms() {
        return this.rooms;
    }

    /**
     * Adds a {@code room} to this {@code TemperatureControlZone}.
     *
     * @param room The {@code Room} to be added to the specified {@code TemperatureControlZone}
     */
    public void addRoom(String room) {
        rooms.add(room);
    }

    /**
     * Removes a {@code room} from this {@code TemperatureControlZone}.
     *
     * @param room The {@code Room} to be removed from the specified {@code TemperatureControlZone}
     */
    public void removeRoom(String room) {
        rooms.remove(room);
    }

    /**
     * @param period The current period
     * @return The ideal temperature of this {@code TemperatureControlZone}
     */
    public double getDesiredTemperatureFor(String room, int period) {
        Double desiredTemperature = overridden.get(room);
        while (desiredTemperature == null) {
            desiredTemperature = this.desiredTemperature[period--];
        }
        return desiredTemperature;
    }

    /**
     * Sets the temperature of a period to that specified
     *
     * @param period The period to be changed
     * @param temp The new desired temperature
     */
    public void setPeriodTemp(int period, double temp) {
        desiredTemperature[validatePeriod(period)] = temp;
    }

    /**
     * Signifies that the desired temperature for the specified {@code room} should be overridden to the specified
     * {@code temp}.
     *
     * @param room The specified {@code Room}
     * @param temp The specified temperature
     * @throws NoSuchElementException If the specified {@code room} is not in this {@code TemperatureControlZone}
     */
    public void overrideTempFor(String room, double temp) {
        if (!rooms.contains(room)) {
            throw new NoSuchElementException("That room is not in this zone.");
        }
        overridden.put(room, temp);
    }

    /**
     * Determines whether or not the specified {@code room} has had its desired temperature overridden.
     *
     * @param room The specified {@code Room}
     * @return {@code true} if the specified {@code room} has had its desired temperature overridden
     * @throws NoSuchElementException If the specified {@code room} does not belong to this {@code
     * TemperatureControlZone}
     */
    public boolean isOverridden(String room) {
        if (!rooms.contains(room)) {
            throw new NoSuchElementException("That room is not in this zone.");
        }
        return overridden.containsKey(room);
    }

    /**
     * Provides the desired temperatures for this {@code TemperatureControlZone} for the different periods in the day.
     * @return The desired temperatures for this {@code TemperatureControlZone}
     */
    public Double[] getDesiredTemperatures() {
        return desiredTemperature;
    }
}
