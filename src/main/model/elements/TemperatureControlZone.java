package main.model.elements;

import java.util.HashMap;
import java.util.Map;

import static main.util.NameValidator.validateName;

public class TemperatureControlZone {

    private static double DEFAULT_ROOM_TEMPERATURE = 15.00;
    private final Map<String, Room> rooms;
    private double temperature;

    public TemperatureControlZone() {
        this.rooms = new HashMap<>();
        this.temperature = DEFAULT_ROOM_TEMPERATURE;
    }

    public Map<String, Room> getRooms() {
        return this.rooms;
    }

    public void addRoom(String zone, Room room) {
        rooms.putIfAbsent(validateName(zone), room);
    }

    public void removeRoom(String zone, Room room) {
        rooms.remove(zone, room);
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
        for(Room room : rooms.values()) {
            room.setTemperature(temperature);
        }
    }
}
