package main;

import main.model.elements.Door;
import main.model.parameters.permissions.Action;

public class TestMain {

    // Driver code
    public static void main(String[] args)
    {
        Door door = new Door();
        door.setLocked(false); // manually unlock the door -> ie. door lock == false
        door.setOpen(false); // manually unlock the door -> ie. door lock == false
        String msg1 = door.manipulate(Action.TOGGLE_WINDOW); // pass the action to lock the door -> ie. door lock == true
        String msg2 = door.manipulate(Action.TOGGLE_DOOR); // pass the action to lock the door -> ie. door lock == true
        System.out.println("Door is locked : " + door.isLocked());
        System.out.println(msg1);
        System.out.println(msg2);
    }
}