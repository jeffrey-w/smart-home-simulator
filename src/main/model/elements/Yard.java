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
     * Get the {@code Yard} of a {@code House}
     *
     * @return the Yard object
     */
    public static Yard getInstance() {
        if (yard == null) {
            yard = new Yard();
        }
        return yard;
    }

    /**
     * runs a routine if there is a person removed from the "Yard'
     *
     * @return message depending on context
     */
    public String addRoutine(){
        // empty for now
        return "no operations done";
    }

    /**
     * runs a routine if there is a person removed from the "Yard'
     *
     * @return message depending on context
     */
    public String removeRoutine(){
        // empty for now
        return "no operations done";
    }
}
