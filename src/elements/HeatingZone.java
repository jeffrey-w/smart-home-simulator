package elements;

/**
 * A heating zone is defined as being a group of rooms, defined by the user.
 * The user can modify the scope of the heating zone as well as its temperature.
 *
 * @author Philippe Vo
 * @author Émilie Martin
 */
class HeatingZone {

    private int temperature;
    private Room[] rooms;

    /**
     * Defines a heating zone with a given temperature encompassing a set of rooms
     *
     * @param temperature The given temperature of the heating zone
     * @param rooms The set of rooms contained within the heating zone
     */
    public HeatingZone(int temperature, Room[] rooms) {
        this.temperature = temperature;
        this.rooms = rooms;
    }

    /**
     * Getter to obtain the temperature of the heating zone
     *
     * @return The temperature of the heating zone
     */
    public int getTemperature() {
        return this.temperature;
    }

    /**
     * Setter to change the temperature of a given heating zone
     *
     * @param temperature The new temperature of the heating zone
     */
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    /**
     * Getter to obtain the rooms affected by the heating zone
     *
     * @return The array of rooms contained within the heating zone
     */
    public Room[] getRooms() {
        return this.rooms;
    }

    /**
     * Setter to change the rooms encompassed by the heating zone
     *
     * @param rooms The new array of rooms affected by the heating zone
     */
    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }
}