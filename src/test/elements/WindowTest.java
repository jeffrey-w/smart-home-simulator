package test.elements;

import main.model.Action;
import main.model.elements.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WindowTest {

    Window window;

    @BeforeEach
    void setup() {
        window = new Window(false, false);
    }

    // Testing window.setObstructed (blocking window use case)
    @Test
    void testIsBlocked() {
        Window window_ = new Window(true, true);

        // Window should return that it is blocked since it was initialized as obstructed

        assertTrue(window_.isBlocked());

        // Window should return that it is not blocked
        window_.setBlocked(false);
        assertFalse(window_.isBlocked());

    }

    void testManipulate(){
        // try to open window while blocked
        window.manipulate(Action.TOGGLE_BLOCK_WINDOW, null, null); // block the window
        window.manipulate(Action.TOGGLE_WINDOW, null, null);
        assertFalse(window.isOpen()); // cant open if blocked

        // try to open window while unblocked
        window.manipulate(Action.TOGGLE_BLOCK_WINDOW, null, null); // unblock the window
        window.manipulate(Action.TOGGLE_WINDOW, null, null); // open the window
        assertTrue(window.isOpen()); // can open if blocked

        // try to close window while unblocked
        window.manipulate(Action.TOGGLE_WINDOW, null, null); // open the window
        assertFalse(window.isOpen()); // can close if blocked

        // try to close window while blocked
        window.manipulate(Action.TOGGLE_WINDOW, null, null); // open the window
        window.manipulate(Action.TOGGLE_BLOCK_WINDOW, null, null); // block the window
        window.manipulate(Action.TOGGLE_WINDOW, null, null); // close the window
        assertFalse(window.isOpen()); // cant close if blocked
    }
}