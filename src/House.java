import java.util.ArrayList;

public class House {
    private String houseName;
    private Room[] rooms;

    public House(String name, Room[] rooms) {
        this.houseName = name;
        this.rooms = rooms;
    }

    void setHouseName(String name) {
        this.houseName = name;
    }

    void deleteHouse() {
        this.houseName = "";
        this.rooms = null;
    }
}
