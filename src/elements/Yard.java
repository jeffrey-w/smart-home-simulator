package elements;

import permissions.Permission;

import java.util.ArrayList;

public class Yard extends Place{
    //singleton object
    private static Yard yard = null;

    public static Yard getInstance(){
        if (yard == null){
            yard = new Yard();
        }
        return yard;
    }
}
