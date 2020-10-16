// imports
package elements;

class HeatingZone {
    // variables
    private int temperature = 0;
    private Room[] rooms = {}; // FIXME

    // constructor
    public HeatingZone(int temperature, Room[] rooms) {
        this.temperature = temperature;
        this.rooms = rooms;
    }

    // methods
    // get/set
    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public Room[] getRooms() {
        return this.rooms;
    }
}