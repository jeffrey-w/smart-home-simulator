package test.elements;

import main.model.elements.Wall;
import main.model.elements.Window;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WindowTest {

    // testing window.setObstructed + window.isObstructedString (blocking window use case)
    @Test
    void testIsBlocked() {
        Window window = new Window(true, true, Wall.EAST);

        // window should return that it is blocked since it was initialized as obstructed
        assertTrue(window.isObstructed());

        // window should return that it is not blocked
        window.setObstructed(false);
        assertFalse(window.isObstructed());
    }

}