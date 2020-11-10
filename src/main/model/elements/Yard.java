package main.model.elements;

/**
 * A {@code Yard} is considered as being all the space excluding the contents of the {@code House}.
 * Placing people outside the {@code House} puts them in the {@code Yard}.
 *
 * @author Ayman Shehri
 */
public class Yard extends Place {

    // Singleton object
    private static volatile Yard yard;

    private Light light = new Light();

    /**
     * Get the {@code Yard} of a {@code House}
     *
     * @return The {@code Yard} object
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

    /**
     * Turns this {@code Yard}'s {@code Light} on of off depending on the specified {@code on} value.
     * @param on the specified on value
     */
    public void setLightOn(boolean on) {
        light.setOn(on);
    }

}
