package main.model.elements;

/**
 * A {@code Garage} is considered an extra room of the {@code House}.
 * People in the {@code Garage} are still considered as being inside the {@code House}.
 *
 * @author Émilie Martin
 */
public class Garage extends Place {

    // Singleton object
    private static Garage garage = null;

    /**
     * Get the {@code Garage} of a {@code House}
     *
     * @return The {@code Garage} object
     */
    public static Garage getInstance() {
        synchronized (Garage.class) {
            if (garage == null) {
                synchronized (Garage.class) {
                    garage = new Garage();
                }
            }
        }
        return garage;
    }
    
}
