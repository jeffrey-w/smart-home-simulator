package main.model.elements;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * A {@code TemperatureControlZone} contains many {@code Room}s and can adjust the temperature of all contained {@code Room}s.
 * The user can add and remove {@code Room}s from a {@code TemperatureControlZone}.
 *
 * @author Émilie Martin
 * @author Ayman Shehri
 */
public class TemperatureControlZone {

    private static double DEFAULT_DESIRED_TEMPERATURE = 15.00;
    private final Set<Room> rooms;
    private static double[] desiredTemperature;
    private static boolean[] activePeriods;


    /**
     * Constructs a {@code TemperatureControlZone} with no {@code Room}s.
     */
    public TemperatureControlZone() {
        this.rooms = Collections.newSetFromMap(new IdentityHashMap<>());
        desiredTemperature = new double[]{DEFAULT_DESIRED_TEMPERATURE, DEFAULT_DESIRED_TEMPERATURE, DEFAULT_DESIRED_TEMPERATURE};
        activePeriods = new boolean[]{true, false, false};
    }

    /**
     * @return The {@code Room}s contained within this {@code TemperatureControlZone}
     */
    public Set<Room> getRooms() {
        return this.rooms;
    }

    /**
     * Adds a {@code Room} to this {@code TemperatureControlZone}.
     *
     * @param zone The {@code TemperatureControlZone} to be modified
     * @param room The {@code Room} to be added to the specified {@code TemperatureControlZone}
     */
    public void addRoom(String zone, Room room) {
        rooms.add(room);
    }

    /**
     * Removes a {@code Room} from this {@code TemperatureControlZone}.
     *
     * @param room The {@code Room} to be removed from the specified {@code TemperatureControlZone}
     */
    public void removeRoom(Room room) {
        rooms.remove(room);
    }

    /**
     * @param period The current period
     * @return The ideal temperature of the room
     */
    public double getDesiredTemperature(int period) {
        if (activePeriods[period]) {
            return desiredTemperature[period];
        } else {
            //return the temperature for previous period
            return getDesiredTemperature(period - 1);
        }
    }

    /**
     * Sets the temperature of a chosen {@code Room} to that specified
     *
     * @param room        The specified {@code Room}
     * @param temperature The new temperature of the {@code Room}
     */
    public void setRoomTemperature(Room room, double temperature) {
        if (rooms.contains(room)) {
            room.setTemperature(temperature);
        }
    }

    /**
     * Sets the temperature of a period to that specified
     *
     * @param period The period to be changed
     * @param temp The new desired temperature
     */
    public void setPeriodTemp(int period, double temp) {
        desiredTemperature[period] = temp;
    }

    /**
     * Gets the desired temperature for the specified period
     *
     * @param period the period in question
     * @return The desired temperature for the specified period
     */
    public double getPeriodTemp(int period) {
        return desiredTemperature[period];
    }

    /**
     * Sets the status of a period to that specified
     *
     * @param period The period to be changed
     * @param isActive The status to be set
     */
    public void setPeriodStatus(int period, boolean isActive) {
        activePeriods[period] = isActive;
    }

    /**
     * Gets the set temperature for all periods
     *
     * @return An array that holds the desired temperature for all periods
     */
    public double[] getAllPeriodsTemp() {
        return desiredTemperature;
    }

    /**
     * Sets the desired temperature of all periods
     *
     * @param first The desired temperature for the first period
     * @param second The desired temperature for the second period
     * @param third The desired temperature for the third period
     */
    public void setAllPeriodsTemp(double first, double second, double third) {
        desiredTemperature[0] = first;
        desiredTemperature[1] = second;
        desiredTemperature[2] = third;
    }

}
