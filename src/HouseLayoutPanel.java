import java.io.FileReader;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HouseLayoutPanel extends JPanel {

    JLabel layoutName = new JLabel();
    JLabel numRooms = new JLabel();
    ArrayList<String[]> rooms = new ArrayList<>();
    ArrayList<String> room_names = new ArrayList<>();
    ArrayList<String> room_coordinates = new ArrayList<>();

    // Each array element counts for a wall (NESW) and the boolean indicates whether or not there is an element on that wall
    ArrayList<Boolean[]> windows = new ArrayList<>();
    ArrayList<Boolean[]> doors = new ArrayList<>();

    HouseLayoutPanel() {
        setPreferredSize(new Dimension(256, 512));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); // TODO need better layout

        add(layoutName);
        add(numRooms);

        setLayoutName("simulation1");
        setNumRooms("2");
    }

    void setRoom (String roomName, int roomCoordX, int roomCoordY, Boolean[] windows, Boolean[] doors) {
        setRoomName(roomName);
        setRoomCoordinates(roomCoordX, roomCoordY);
        setRoomWindows(windows[0], windows[1], windows[2], windows[3]);
        setRoomDoors(doors[0], doors[1], doors[2], doors[3]);
    }

    void setLayoutName (String layoutName) {
        this.layoutName.setText(layoutName);
    }

    void setNumRooms (String num) {
        this.numRooms.setText(num);
    }

    void setRoomName (String roomName) {
        this.room_names.add(roomName);
    }

    void setRoomCoordinates (int x, int y) {
        String coordinates = "x-coordinates"+x+"/"+"y-coordinates"+y;
        this.room_coordinates.add(coordinates);
    }

    void setRoomWindows (boolean windowN, boolean windowE, boolean windowS, boolean windowW) {
        Boolean [] windowLocation = new Boolean[] {windowN, windowE, windowS, windowW};
        this.windows.add(windowLocation);
    }

    void setRoomDoors (boolean doorN, boolean doorE, boolean doorS, boolean doorW) {
        Boolean [] doorLocation = new Boolean[] {doorN, doorE, doorS, doorW};
        this.doors.add(doorLocation);
    }

}
