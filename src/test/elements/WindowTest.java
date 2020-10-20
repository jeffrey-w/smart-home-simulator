package test;

import elements.Window;
import org.junit.jupiter.api.Test;
import util.Bearing;

import static org.junit.jupiter.api.Assertions.*;

class WindowTest {

    // testing window.setObstructed + window.isObstructedString (blocking window use case)
    @Test
    void testIsBlocked() {
        Window window = new Window(true, true, Bearing.EAST);

        // window should return that it is blocked
        window.setObstructed(true);
        assertEquals(window.isObstructedString(), true);

        // window should return that it is blocked
        window.setObstructed(false);
        assertEquals(window.isObstructedString(), false);
    }
}