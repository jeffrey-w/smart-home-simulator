// imports
package src.elements;

class House {
    // variables
    private String location = "default";
    private Room[] rooms = {}; // FIXME
    private HouseLayout houseLayout = null; // FIXME

    // constructor
    House(String location, Room[] rooms, HouseLayout houseLayout) {
        this.location = location;
        this.rooms = rooms;
        this.houseLayout = houseLayout;
    }

    // methods
    // get/set
    public String getLocation() {
        return location;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public HouseLayout getHouseLayout() {
        return houseLayout;
    }
}