package elements;

import main.model.elements.Light;
import main.model.parameters.permissions.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LightTest {

    Light light;

    @BeforeEach
    void setup() {
        light = new Light(false);
    }

    @Test
    void testManipulate(){
       // turn on the light
        light.manipulate(Action.TOGGLE_LIGHT);
        assertTrue(light.isOn());

        // turn off the light
        light.manipulate(Action.TOGGLE_LIGHT);
        assertFalse(light.isOn());
    }

    @Test
    void testAutoMode(){
        // turn on the light auto mode
        light.setAutoMode(true);
        assertTrue(light.isAutoMode());

        // turn off the light auto mode
        light.setAutoMode(false);
        assertFalse(light.isAutoMode());
    }
}