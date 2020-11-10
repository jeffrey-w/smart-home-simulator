package test.elements;

import main.model.elements.House;
import main.util.HouseReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class HouseTest {

    private House house;

    @BeforeEach
    void setup() {
        house = (new HouseReader(new File("./src/test/io/houseLayoutTest.json"))).readHouse();
    }

    @Test
    void testCloseOpenables() {
        assertDoesNotThrow(() -> house.closeOpenables());
        house.getRoom("living_room").getWindows()[0].setOpen(true);
        house.getRoom("living_room").getWindows()[0].setBlocked(true);
        assertThrows(IllegalStateException.class, () -> house.closeOpenables());
    }

    @Test
    void testToggleLights() {
        assertEquals(0, house.getRoom("living_room").getNumberOfLightsOn());
        house.getRoom("living_room").toggleLights(true);
        assertEquals(2, house.getRoom("living_room").getNumberOfLightsOn());
    }

}
