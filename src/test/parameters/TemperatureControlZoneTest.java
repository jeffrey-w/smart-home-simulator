package test.parameters;

import main.model.elements.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TemperatureControlZoneTest {

    private TemperatureControlZone zone;

    @BeforeEach
    void setup() {
        zone = new TemperatureControlZone();
    }

    @Test
    void getRooms() {
        assertTrue(zone.getRooms().isEmpty());
        zone.addRoom(null);
        assertFalse(zone.getRooms().isEmpty());
    }

    @Test
    void addRoom() {
        assertDoesNotThrow(() -> zone.addRoom(null));
    }

    @Test
    void removeRoom() {
        assertDoesNotThrow(() -> zone.removeRoom(null));
    }

    @Test
    void getDesiredTemp() {
        zone.addRoom(null);
        assertEquals(25.0, zone.getDesiredTemperatureFor(null, 2));
        assertEquals(25.0, zone.getDesiredTemperatureFor(null, 1));
        assertEquals(25.0, zone.getDesiredTemperatureFor(null, 0));
        zone.overrideTempFor(null, 20.0);
        assertEquals(20.0, zone.getDesiredTemperatureFor(null, 2));
        assertEquals(20.0, zone.getDesiredTemperatureFor(null, 1));
        assertEquals(20.0, zone.getDesiredTemperatureFor(null, 0));
    }

    @Test
    void setPeriodTemp() {
        zone.addRoom(null);
        zone.setPeriodTemp(0, 20.0);
        assertEquals(20.0, zone.getDesiredTemperatureFor(null, 0));
    }

    @Test
    void overrideTempFor() {
        assertThrows(NoSuchElementException.class, () -> zone.overrideTempFor(null, 20.0));
    }

    @Test
    void isOverridden() {
        assertThrows(NoSuchElementException.class, () -> zone.isOverridden(null));
        zone.addRoom(null);
        zone.overrideTempFor(null, 20.0);
        assertTrue(zone.isOverridden(null));
    }

}
