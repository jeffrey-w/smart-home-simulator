package test.elements;

import main.model.Action;
import main.model.elements.Light;
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
        light.manipulate(Action.TOGGLE_LIGHT, null ,null);
        assertTrue(light.isOn());

        // turn off the light
        light.manipulate(Action.TOGGLE_LIGHT, null, null);
        assertFalse(light.isOn());
    }

}