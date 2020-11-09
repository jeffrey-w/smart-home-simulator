package main.model.elements;

/**
 * A {@code Yard} is considered as being all the space excluding the contents of the {@code House}.
 * Placing people outside the {@code House} puts them in the {@code Yard}.
 *
 * @author Ayman Shehri
 */
public class Yard extends Place {

    // Singleton object
    private static Yard yard = null;

    /**
     * Get the {@code Yard} of a {@code House}
     *
     * @return The {@code Yard} object
     */
    public static Yard getInstance() {
        if (yard == null) {
            yard = new Yard();
        }
        return yard;
    }

    /**
     * Runs a routine if there is a person removed from the {@code Yard}
     *
     * @return The contextual message
     */
    public String addRoutine(){
        // Empty for now
        return "No operation performed.";
    }

    /**
     * Runs a routine if there is a person removed from the {@code Yard}
     *
     * @return The contextual message
     */
    public String removeRoutine(){
        // Empty for now
        return "No operation performed.";
    }
}
