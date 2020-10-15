import HouseElements.Door;
import HouseElements.Window;

public class Room {
    private String id;
    private Window[] windows;
    private Door[] doors;

    public Room(String id, Window[] windows, Door[] doors) {
        this.id = id;
        this.windows = windows;
        this.doors = doors;
    }
}
