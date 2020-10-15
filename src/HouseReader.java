import org.json.simple.parser.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileReader;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

import HouseElements.Door;
import HouseElements.Window;

public class HouseReader extends JPanel {

    String layoutName;
    ArrayList<Room> rooms = new ArrayList<>();
    Room[] houseRooms;

    House HouseReader() {
        try {
            FileReader reader = new FileReader("assets/houseLayout.json");
            Object layoutFile = new JSONParser().parse(reader);

            JSONObject houseLayout = (JSONObject) layoutFile;
            JSONArray roomsList = (JSONArray) houseLayout.get("rooms");

            layoutName = (String) houseLayout.get("name");

            for(int i=0; i < roomsList.size(); i++) {
                JSONObject roomObj = (JSONObject) roomsList.get(i);

                String roomName = (String) roomObj.get("id");

                // Parse through windows
                JSONObject windows = (JSONObject) roomObj.get("windows");
                Window[] roomWindows = parseWindows(windows);

                // Parse through doors
                JSONObject doors = (JSONObject) roomObj.get("doors");
                Door[] roomDoors = parseDoors(doors);

                addRoom(roomName, roomWindows, roomDoors);

                // Convert
                houseRooms = new Room[rooms.size()];
                houseRooms = rooms.toArray(houseRooms);
            }
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return(new House(layoutName, houseRooms));
    }

    Window[] parseWindows(JSONObject windowObj) {
        JSONArray windowLocArr = (JSONArray) windowObj.get("location");
        JSONArray windowObstrArr = (JSONArray) windowObj.get("obstructed");

        Iterator<Boolean> windowLocationIterator = windowLocArr.iterator();
        Iterator<Boolean> obstructedIterator = windowObstrArr.iterator();

        ArrayList<Window> windowAL = new ArrayList<>();

        while (windowLocationIterator.hasNext() && obstructedIterator.hasNext()) {
            windowAL.add(new Window(windowLocationIterator.next(), obstructedIterator.next()));
        }

        // Convert ArrayList<> to Window[]
        Window[] windowArr = new Window[windowAL.size()];
        windowArr = windowAL.toArray(windowArr);

        return windowArr;
    }

    Door[] parseDoors(JSONObject doorObj) {
        JSONArray doorLocArr = (JSONArray) doorObj.get("location");
        JSONArray doorLockArr = (JSONArray) doorObj.get("locked");

        Iterator<Boolean> doorLocationIterator = doorLocArr.iterator();
        Iterator<Boolean> lockedIterator = doorLockArr.iterator();

        ArrayList<Door> doorAL = new ArrayList<>();

        while (doorLocationIterator.hasNext() && lockedIterator.hasNext()) {
            doorAL.add(new Door(doorLocationIterator.next(), lockedIterator.next()));
        }

        // Convert ArrayList<>to Door[]
        Door[] doorArr = new Door[doorAL.size()];
        doorArr = doorAL.toArray(doorArr);

        return doorArr;
    }

    void addRoom(String name, Window[] windows, Door[] doors) {
        rooms.add(new Room(name, windows, doors));
    }

}
