package main.model.elements;

/**
 * A {@code Yard} is the place that is considered to be outside the house. Placing people outside the house puts them in
 * the yard.
 *
 * @author Ayman Shehri
 */
public class Yard extends Place {

    // Singleton object
    private static Yard yard = null;

    /**
     * Get the yard of a house
     *
     * @return the Yard object
     */
    public static Yard getInstance() {
        if (yard == null) {
            yard = new Yard();
        }
        return yard;
    }
}
