package main.util;

import main.model.elements.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The main.util.HouseReader class parses through a house layout JSON file. Its function {@link #readHouse()} returns a
 * {@code House} object to be displayed to the user.
 *
 * @author Émilie Martin
 * @author Jeff Wilgus
 */
public class HouseReader extends JPanel {

    private static final int NUMBER_OF_WALLS = 4;
    private static final JSONParser PARSER = new JSONParser();

    JSONObject layoutFile;

    /**
     * Constructs a {@code HouseReader} object, which accepts a {@code File} to read. The file refers to the house
     * layout and assumes it has the right format.
     *
     * @param file The file to be read
     */
    public HouseReader(File file) {
        try (FileReader reader = new FileReader(file)) {
            layoutFile = (JSONObject) PARSER.parse(reader);
        } catch (FileNotFoundException fnfe) {
            System.err.println("The file you are looking for cannot be found.");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * The redHouse() function parses through the given file and creates a {@code House} object. It builds {@code
     * Window} objects, {@code Light} objects, and {@code Door} objects, that all belong to the {@code Room} object.
     *
     * @return A {@code House} object
     */
    public House readHouse() {
        House house = new House();
        Map<String, JSONArray> connections = new HashMap<>();
        JSONArray roomsList = (JSONArray) layoutFile.get("rooms");

        for (Object o : roomsList) {

            JSONObject roomObj = (JSONObject) o;
            String roomName = (String) roomObj.get("id");

            // Parse through doors
            JSONObject doors = (JSONObject) roomObj.get("doors");
            Door[] roomDoors = parseDoors(doors);

            // Parse through lights
            JSONObject lights = (JSONObject) roomObj.get("lights");
            Light[] roomLights = parseLights(lights);

            // Parse through windows
            JSONObject windows = (JSONObject) roomObj.get("windows");
            Window[] roomWindows = parseWindows(windows);

            house.addRoom(new Room(roomDoors, roomLights, roomWindows), roomName);

            // Get connected rooms
            connections.put(roomName, (JSONArray) roomObj.get("connections"));
        }

        // Add connections
        for (String room : connections.keySet()) {
            if (connections.get(room) != null) {
                for (Object connection : connections.get(room)) {
                    house.addConnection(room, (String) connection);
                }
            }
        }

        house.setRoot((String) layoutFile.get("root"));

        return house;
    }

    /*
     * This function parses through the house layout, builds and returns a Door[]
     *
     * @param doorObj The Door object contained within the house layout. Of the following form:
     *      {
     *          "present": Boolean[](4) >> each element of the array represents a wall (N-E-S-W)
     *      }
     * The position of the Boolean in either array represents the same door. As in:
     *      {
     *          "present": [true, false, false, true]
     *      }
     * Wherein the North and West walls have doors.
     * @return A Door[] encompassing all doors found in a room
     */
    private Door[] parseDoors(JSONObject doorObj) {
        int index = 0;
        Door[] doors = new Door[NUMBER_OF_WALLS];
        for (Object isPresent : (JSONArray) doorObj.get("location")) {
            try {
                doors[index++] = ((boolean) isPresent) ? new Door() : null;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(
                        "The file you've selected specifies an invalid number of doors. There can be a maximum of "
                                + "four.");
            }
        }
        return doors;
    }

    /*
     * This function parses through the house layout, builds and returns a Light[]
     *
     * @param lightObj The Light object contained within the house layout. Of the following form:
     *      {
     *          "present": Boolean[](4) >> each element of the array represents a wall (N-E-S-W)
     *      }
     * The position of the Boolean in either array represents the same light. As in:
     *      {
     *          "present": [true, false, false, true]
     *      }
     * Wherein the North and West walls have Lights.
     * @return A Light[] encompassing all doors found in a room
     */
    private Light[] parseLights(JSONObject lightObj) {
        int index = 0;
        Light[] lights = new Light[NUMBER_OF_WALLS];
        for (Object isPresent : (JSONArray) lightObj.get("location")) {
            try {
                lights[index++] = ((boolean) isPresent) ? new Light() : null;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(
                        "The file you've selected specifies an invalid number of lights. There can be a maximum of "
                                + "four.");
            }
        }
        return lights;
    }

    /*
     * This function parses through the house layout, builds and returns a Window[]
     *
     * @param windowObj The Window object contained within the house layout. Of the following form:
     *      {
     *          "present": Boolean[](4) >> each element of the array represents a wall (N-E-S-W)
     *      }
     * The position of the Boolean in either array represents the same Window. As in:
     *      {
     *          "present": [true, false, false, true]
     *      }
     * Wherein the North and West walls have Windows.
     * @return A Window[] encompassing all doors found in a room
     */
    private Window[] parseWindows(JSONObject windowObj) {
        int index = 0;
        Window[] windows = new Window[NUMBER_OF_WALLS];
        for (Object isPresent : (JSONArray) windowObj.get("location")) {
            try {
                windows[index++] = ((boolean) isPresent) ? new Window() : null;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException(
                        "The file you've selected specifies an invalid number of windows. There can be a maximum of "
                                + "four.");
            }
        }
        return windows;
    }

}
