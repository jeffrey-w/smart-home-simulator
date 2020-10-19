package src.elements;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WindowTest {
    @Test
    void isBlocked() {
        Window window = new Window(true, true);
        window.getProximitySensor().setObstacle(true);
        Boolean blockedStatus = window.isBlocked();
        assertEquals(blockedStatus, true);
    }
}