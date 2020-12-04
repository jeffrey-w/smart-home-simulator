package test.parameters;

import main.model.Action;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ParameterTest {

    private static final String TEST_ROLE = "Test";
    private static final Permission TEST_PERMISSION = new Permission() {
        @Override
        public Action authorize(final Action action) {
            return action;
        }

        @Override
        public Set<Action> allowed() {
            return null;
        }

        @Override
        public void allow(final Action action) {

        }

        @Override
        public void disallow(final Action action) {

        }
    };

    Parameters parameters;

    @BeforeEach
    void setup() {
        parameters = new Parameters();
    }

    @Test
    void testAddProfile() {
        assertThrows(IllegalArgumentException.class, () -> parameters.addActor("~", TEST_PERMISSION));
        assertThrows(NullPointerException.class, () -> parameters.addActor(TEST_ROLE, null));
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        assertFalse(parameters.getActorsIdentifier().isEmpty());
    }

    @Test
    void testEditProfile() {
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        assertEquals(parameters.getActorsIdentifier().size(), 1);
    }

    @Test
    void testRemoveProfile() {
        parameters.addActor(TEST_ROLE, TEST_PERMISSION);
        parameters.removeActor(TEST_ROLE);
        assertTrue(parameters.getActorsIdentifier().isEmpty());
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
        assertThrows(IllegalArgumentException.class, () -> parameters.setLocation("~"));
        parameters.setLocation("test");
        assertNotNull(parameters.getLocation());
    }

    @Test
    void testSetTemperature() {
        Parameters parameters = new Parameters();

        // Test temperatures
        double min_temp = -200;
        double max_temp = 200;
        double temp = 50;

        // Invalid temperature tests -> should throw an error and the temperature should stay at default
        assertThrows(IllegalArgumentException.class, () -> parameters.setExternalTemperature(min_temp));
        assertEquals(parameters.getExternalTemperature(), 15);

        assertThrows(IllegalArgumentException.class, () -> parameters.setExternalTemperature(max_temp));
        assertEquals(parameters.getExternalTemperature(), 15);

        // Valid temperature test
        parameters.setExternalTemperature(temp);
        assertEquals(parameters.getExternalTemperature(), 50);
    }

    @Test
    void testSetAutoLight() {
        assertFalse(parameters.isAutoLight());
        parameters.setAutoLight(true);
        assertTrue(parameters.isAutoLight());
    }

    @Test
    void testSetAwayMode() {
        assertFalse(parameters.isAwayMode());
        parameters.setAwayMode(true);
        assertTrue(parameters.isAwayMode());
    }

    @Test
    void testSetAwayDelay() {
        assertEquals(10_000, parameters.getAwayDelay());
        parameters.setAwayDelay(1000);
        assertEquals(1000, parameters.getAwayDelay());
    }

}
