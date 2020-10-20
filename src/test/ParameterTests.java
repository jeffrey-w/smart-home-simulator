package test;

import parameters.Parameters;
import permissions.Permission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ParameterTests {

    private static final String TEST_ROLE = "Test";
    private static final Permission TEST_PERMISSION = action -> {
    };

    Parameters parameters;

    @BeforeEach
    void setup() {
        parameters = new Parameters();
    }

    @Test
    void testAddProfile() {
        assertThrows(IllegalArgumentException.class, () -> parameters.addActor("invalid role", TEST_PERMISSION));
        assertThrows(NullPointerException.class, () -> parameters.addActor(TEST_ROLE, null));
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        assertFalse(parameters.getActors().isEmpty());
    }

    @Test
    void testEditProfile() {
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        parameters.addActor(TEST_ROLE, action -> {
        });
        assertEquals(parameters.getActors().size(), 1);
    }

    @Test
    void testRemoveProfile() {
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        parameters.removeActor(TEST_ROLE);
        assertTrue(parameters.getActors().isEmpty());
    }

    @Test
    void testSetDateAndTime() {
        assertNotNull(parameters.getDate());
        assertThrows(NullPointerException.class, () -> parameters.setDate(null));
        parameters.setDate(Date.from(Instant.EPOCH));
        assertEquals(parameters.getDate(), Date.from(Instant.EPOCH));
    }

    @Test
    void testLoginWithProfile() {
        assertNull(parameters.getPermission());
        parameters.setPermission(TEST_PERMISSION);
        assertNotNull(parameters.getPermission());
    }

    @Test
    void testSetLocation() {
        assertNull(parameters.getLocation());
        assertThrows(IllegalArgumentException.class, () -> parameters.setLocation("invalid location"));
        parameters.setLocation("test");
        assertNotNull(parameters.getLocation());
    }

    @Test
    void testSetTemperature() throws Exception {
        Parameters parameters = new Parameters();

        // temperatures
        int min_temp = -200;
        int max_temp = 200;
        int temp = 50;

        // invalid temperature tests -> should throw an error and the temperature should stay at default
        //  Block of code to try
        assertThrows(IllegalArgumentException.class, () -> parameters.setTemperature(min_temp));
        assertEquals(parameters.getTemperature(), 15);

        assertThrows(IllegalArgumentException.class, () -> parameters.setTemperature(max_temp));
        assertEquals(parameters.getTemperature(), 15);

        // valid temperature test
        parameters.setTemperature(temp);
        assertEquals(parameters.getTemperature(), 50);
    }
}
