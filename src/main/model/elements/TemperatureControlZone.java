package main.model.elements;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * A {@code TemperatureControlZone} contains many {@code Room}s and can adjust the temperature of all contained {@code Room}s.
 * The user can add and remove {@code Room}s from a {@code TemperatureControlZone}.
 *
 * @author Émilie Martin
 */
public class TemperatureControlZone {

    private static double DEFAULT_DESIRED_TEMPERATURE = 15.00;
    private final Set<Room> rooms;
    private double desiredTemperature;

    /**
     * Constructs a {@code TemperatureControlZone} with no {@code Room}s.
     */
    public TemperatureControlZone() {
        this.rooms = Collections.newSetFromMap(new IdentityHashMap<>());
        this.desiredTemperature = DEFAULT_DESIRED_TEMPERATURE;
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
     * @return The ideal temperature of the room
     */
    public double getDesiredTemperature() {
        return this.desiredTemperature;
    }

    /**
     * Sets the temperature of a chosen {@code Room} to that specified
     *
     * @param room The specified {@code Room}
     * @param temperature The new temperature of the {@code Room}
     */
    public void setRoomTemperature(Room room, double temperature) {
        if(rooms.contains(room)) {
            room.setTemperature(temperature);
        }
    }

}
