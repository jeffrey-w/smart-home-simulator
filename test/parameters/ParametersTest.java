package parameters;

import elements.Window;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParametersTest {

    @Test
    void setTemperature() throws Exception {
        Parameters parameters = new Parameters();

        // temperatures
        int min_temp = -200;
        int max_temp = 200;
        int temp = 50;

        // invalid temperature tests -> should throw an error and the temperature should stay at default
        //  Block of code to try
        assertThrows(IllegalArgumentException.class, () -> parameters.setTemperature(min_temp));
        assertEquals(parameters.getTemperature(), 15);

        assertThrows(IllegalArgumentException.class, () -> parameters.setTemperature(max_temp));
        assertEquals(parameters.getTemperature(), 15);

        // valid temperature test
        parameters.setTemperature(temp);
        assertEquals(parameters.getTemperature(), 50);
    }
}