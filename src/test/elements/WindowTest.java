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

        // window should return that it is blocked since it was initialized as obstructed
        assertEquals(window.isObstructed(), true);

        // window should return that it is not blocked
        window.setObstructed(false);
        assertEquals(window.isObstructed(), false);
    }
}