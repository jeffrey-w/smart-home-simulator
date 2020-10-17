package elements;

import permissions.Permission;

import java.util.ArrayList;

/**
 * A Yard is the place that is considered to be outside the house. Placing people outside the house puts them in the yard.
 * @author Ayman Shehri
 */
public class Yard extends Place{
    //singleton object
    private static Yard yard = null;

    /**
     * get the yard of the house
     * @return the Yard object
     */
    public static Yard getInstance(){
        if (yard == null){
            yard = new Yard();
        }
        return yard;
    }
}
