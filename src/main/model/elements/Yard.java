package main.model.elements;

/**
 * A {@code Yard} is the place that is considered to be outside the house. Placing people outside the house puts them in
 * the yard.
 *
 * @author Ayman Shehri
 */
public class Yard extends Place {

    // Singleton object
    private static volatile Yard yard;

    /**
     * Get the {@code Yard} of a {@code House}
     *
     * @return the Yard object
     */
    public static Yard getInstance() {
        if (yard == null) {
            synchronized (Yard.class) {
                if (yard == null) {
                    synchronized (Yard.class) {
                        yard = new Yard();
                    }
                }
            }
        }
        return yard;
    }

}
