package test;

import elements.*;
import util.HouseReader;
import util.Bearing;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class HouseReaderTests {

    HouseReader houseReader = new HouseReader(new File("src/test/houseLayoutTest.json"));

    @Test
    void testReadHouse() {
        // Build living room
        Door livingRoomDoor1 = new Door(false, false);
        Door livingRoomDoor2 = new Door(true, false);
        Door livingRoomDoor3 = new Door(false, false);
        Door livingRoomDoor4 = new Door(true, false);
        Door[] livingRoomDoors = new Door[]{livingRoomDoor1, livingRoomDoor2, livingRoomDoor3, livingRoomDoor4};

        Light livingRoomLight1 = new Light(true, true);
        Light livingRoomLight2 = new Light(true, true);
        Light[] livingRoomLights = new Light[]{livingRoomLight1, livingRoomLight2};

        Window livingRoomWindow1 = new Window(true, false, Bearing.NORTH);
        Window livingRoomWindow2 = new Window(false, false, Bearing.EAST);
        Window livingRoomWindow3 = new Window(false, false, Bearing.SOUTH);
        Window livingRoomWindow4 = new Window(true, false, Bearing.WEST);
        Window[] livingRoomWindows = new Window[]{livingRoomWindow1, livingRoomWindow2, livingRoomWindow3, livingRoomWindow4};

        // Build kitchen
        Door kitchenDoor1 = new Door(false, false);
        Door kitchenDoor2 = new Door(false, false);
        Door kitchenDoor3 = new Door(false, false);
        Door kitchenDoor4 = new Door(true, false);
        Door[] kitchenDoors = new Door[]{kitchenDoor1, kitchenDoor2, kitchenDoor3, kitchenDoor4};

        Light kitchenLight1 = new Light(true, true);
        Light kitchenLight2 = new Light(true, false);
        Light kitchenLight3 = new Light(true, true);
        Light[] kitchenLights = new Light[]{kitchenLight1, kitchenLight2, kitchenLight3};

        Window kitchenWindow1 = new Window(true, false, Bearing.NORTH);
        Window kitchenWindow2 = new Window(true, true, Bearing.EAST);
        Window kitchenWindow3 = new Window(false,false, Bearing.SOUTH);
        Window kitchenWindow4 = new Window(false,false, Bearing.WEST);
        Window[] kitchenWindows = new Window[]{kitchenWindow1, kitchenWindow2, kitchenWindow3, kitchenWindow4};

        Room livingRoom = new Room(livingRoomDoors, livingRoomLights, livingRoomWindows);
        Room kitchen = new Room(kitchenDoors, kitchenLights, kitchenWindows);

        // Build expected house
        House expectedHouse = new House();
        expectedHouse.addRoom(livingRoom, "living_room");
        expectedHouse.addRoom(kitchen, "kitchen");

        assertNotNull(houseReader.readHouse());
        assertEquals(houseReader.readHouse(), expectedHouse);
    }
}
