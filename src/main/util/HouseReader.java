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
import java.util.*;

/**
 * The main.util.HouseReader class parses through a house layout JSON file. Its function {@link #readHouse()} returns a
 * {@code House} object to be displayed to the user.
 *
 * @author Émilie Martin
 * @author Jeff Wilgus
 */
public class HouseReader extends JPanel {

    private static final JSONParser PARSER = new JSONParser();

    JSONObject layoutFile;

    /**
     * Constructs a {@code main.util.HouseReader} object, which accepts a {@code File} to read. The file refers to the
     * house layout and assumes it has the right format.
     *
     * @param file The file to be read
     */
    public HouseReader(File file) {
        try (FileReader reader = new FileReader(file)) {
            layoutFile = (JSONObject) PARSER.parse(reader);
        } catch (FileNotFoundException fnfe) {
            System.out.println("The file you are looking for cannot be found.");
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

    /**
     * This function parses through the house layout, builds and returns a Door[]
     *
     * @param doorObj The Door object contained within the house layout. Of the following form:
     *                {
     *                  "location": Boolean[](4), >> each element of the array represents a wall (N-E-S-W)
     *                  "locked": Boolean[](4) >> each elements corresponds to the state of a door
     *                }
     *                The position of the Boolean in either array represents the same door. As in:
     *                {
     *                  "location": [true, false, false],
     *                  "locked": [false, false, true]
     *                }
     *               Wherein the North wall has one unlocked door and the East wall has one locked door.
     * @return A Door[] encompassing all doors found in a room
     */
    private Door[] parseDoors(JSONObject doorObj) {
        JSONArray doorLocArr = (JSONArray) doorObj.get("location");
        JSONArray doorLockArr = (JSONArray) doorObj.get("locked");

        Iterator<Boolean> doorLocationIterator = doorLocArr.iterator();
        Iterator<Boolean> lockedIterator = doorLockArr.iterator();

        ArrayList<Door> doorAL = new ArrayList<>();

        // TODO: double-check that if a door location is set to FALSE, then its locked boolean should automatically be FALSE
        //       (or mention something about its value not mattering)
        while (doorLocationIterator.hasNext() && lockedIterator.hasNext()) {
            doorAL.add(new Door(doorLocationIterator.next(), lockedIterator.next()));
        }

        // Convert ArrayList<>to Door[]
        Door[] doorArr = new Door[doorAL.size()];
        doorArr = doorAL.toArray(doorArr);

        return doorArr;
    }

    /**
     * This function parses through the house layout, builds and returns a Light[]
     *
     * @param lightObj The Light object contained within the house layout. Of the following form:
     *                 {
     *                   "location": Boolean[](),
     *                   "on": Boolean[]()
     *                 }
     *                 The position of the Boolean in either array represents the same light. As in:
     *                 {
     *                   "location": [true, true, true, true, true],
     *                   "on": [false, false, true, false, true]
     *                 }
     *                 Wherein the room contains 5 lights, of which two are turned on.
     * @return A Light[] encompassing all lights in a room
     */
    private Light[] parseLights(JSONObject lightObj) {
        JSONArray lightLocArr = (JSONArray) lightObj.get("present");
        JSONArray lightStateArr = (JSONArray) lightObj.get("on");

        Iterator<Boolean> lightLocationIterator = lightLocArr.iterator();
        Iterator<Boolean> stateIterator = lightStateArr.iterator();

        ArrayList<Light> lightAL = new ArrayList<>();

        // TODO: do we need to be concerned with the contents of the Boolean[] present array
        //       or just take the size and worry about the Boolean[] on array? take care about presence too?
        while (lightLocationIterator.hasNext() && stateIterator.hasNext()) {
            lightAL.add(new Light(lightLocationIterator.next(), stateIterator.next()));
        }

        // Convert ArrayList<> to Light[]
        Light[] lightArr = new Light[lightAL.size()];
        lightArr = lightAL.toArray(lightArr);

        return lightArr;
    }

    /**
     * This function parses through the house layout, builds and returns a Window[]
     *
     * @param windowObj The Window object contained within the house layout. Of the following form:
     *                {
     *                  "location": Boolean[](4), >> each element of the array represents a wall (N-E-S-W)
     *                  "obstructed": Boolean[](4) >> each elements corresponds to the obstruction of a window
     *                }
     *                The position of the Boolean in either array represents the same window. As in:
     *                {
     *                  "location": [true, false, false],
     *                  "obstructed": [false, false, true]
     *                }
     *                Wherein the North wall has one unobstructed window and the East wall has one obstructed window.
     * @return A Window[] encompassing all windows found in a room
     */
    private Window[] parseWindows(JSONObject windowObj) {
        JSONArray windowLocArr = (JSONArray) windowObj.get("location");
        JSONArray windowObstrArr = (JSONArray) windowObj.get("obstructed");

        Iterator<Boolean> windowLocationIterator = windowLocArr.iterator();
        Iterator<Boolean> obstructedIterator = windowObstrArr.iterator();
        Iterator<Wall> bearings = Arrays.asList(Wall.values()).iterator();

        ArrayList<Window> windowAL = new ArrayList<>();

        // TODO: double-check that if a window location is set to FALSE, then its obstructed boolean should automatically be FALSE
        //       (or mention something about its value not mattering)
        while (windowLocationIterator.hasNext() && obstructedIterator.hasNext()) {
            windowAL.add(new Window(windowLocationIterator.next(), obstructedIterator.next(), bearings.next()));
        }

        // Convert ArrayList<> to Window[]
        Window[] windowArr = new Window[windowAL.size()];
        windowArr = windowAL.toArray(windowArr);

        return windowArr;
    }

}