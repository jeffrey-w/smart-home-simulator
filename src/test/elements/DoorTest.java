package elements;

import main.model.elements.Door;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    Door door;

    @BeforeEach
    void setup() {
        door = new Door(false, false);
    }

    @Test
    void testManipulate(){
        /*
        opened | locked
        0,0 = true
        0,1 = true
        1,0 = true
        1,1 = false
         */
        // opened | locked = 0,0 = true
        assertTrue(!(door.isOpen() && door.isLocked()));

        // opened | locked = 0,1 = true
        door.manipulate(Action.TOGGLE_DOOR);
        assertTrue(!(door.isOpen() && door.isLocked()));

        // opened | locked = 0,1 = true
        door.manipulate(Action.TOGGLE_DOOR);
        door.manipulate(Action.TOGGLE_LOCK_DOOR);
        assertTrue(!(door.isOpen() && door.isLocked()));

        // opened | locked = 1,1 = true
        door.manipulate(Action.TOGGLE_DOOR);
        assertFalse(door.isOpen()); // the door should still be closed since we cannot open a locked door
    }
}