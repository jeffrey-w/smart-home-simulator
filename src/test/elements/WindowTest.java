package test.elements;

import main.model.elements.Window;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WindowTest {

    // Testing window.setObstructed (blocking window use case)
    @Test
    void testIsBlocked() {
        Window window = new Window(true, true);

        // Window should return that it is blocked since it was initialized as obstructed
        assertTrue(window.isBlocked());

        // Window should return that it is not blocked
        window.setBlocked(false);
        assertFalse(window.isBlocked());
    }

}