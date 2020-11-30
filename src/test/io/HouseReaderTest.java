package test.io;

import main.model.elements.*;
import main.util.HouseReader;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HouseReaderTest {

    final HouseReader houseReader = new HouseReader(new File("src/test/io/houseLayoutTest.json"));

    @Test
    void testReadHouse() {
        // Build living room
        Door livingRoomDoor1 = new Door();
        Door livingRoomDoor2 = new Door();
        Door[] livingRoomDoors = new Door[] {null, livingRoomDoor1, null, livingRoomDoor2};

        Light livingRoomLight1 = new Light();
        Light livingRoomLight2 = new Light();
        Light[] livingRoomLights = new Light[] {livingRoomLight1, livingRoomLight2, null, null};

        Window livingRoomWindow1 = new Window();
        Window livingRoomWindow2 = new Window();
        Window[] livingRoomWindows = new Window[] {livingRoomWindow1, null, null, livingRoomWindow2};

        // Build kitchen
        Door kitchenDoor1 = new Door();
        Door[] kitchenDoors = new Door[] {null, null, null, kitchenDoor1};

        Light kitchenLight1 = new Light();
        Light kitchenLight2 = new Light();
        Light kitchenLight3 = new Light();
        Light[] kitchenLights = new Light[] {kitchenLight1, kitchenLight2, kitchenLight3, null};

        Window kitchenWindow1 = new Window();
        Window kitchenWindow2 = new Window();
        Window[] kitchenWindows = new Window[] {kitchenWindow1, kitchenWindow2, null, null};

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
