package main;

import org.tinylog.Logger;

import main.model.elements.Door;
import main.model.elements.Window;
import main.model.elements.Light;
import main.model.elements.Room;
import main.model.parameters.permissions.ParentPermission;
import main.model.parameters.permissions.Action;

//
public class TestMain {

    // Driver code
    public static void main(String[] args)
    {
        Door door = new Door();
        door.setLocked(false); // manually unlock the door -> ie. door lock == false
        door.setOpen(false); // manually unlock the door -> ie. door lock == false
//        String msg1 = door.manipulate(Action.TOGGLE_WINDOW); // pass the action to lock the door -> ie. door lock == true
//        String msg2 = door.manipulate(Action.TOGGLE_DOOR); // pass the action to lock the door -> ie. door lock == true
//        System.out.println("Door is locked : " + door.isLocked());
//        System.out.println(msg1);
//        System.out.println(msg2);
//
//        Window window = new Window();
//        window.setOpen(false);
//        window.setObstructed(true);
//        String msg = window.manipulate(Action.TOGGLE_WINDOW); // pass the action to lock the door -> ie. door lock == true
//        System.out.println(msg);

        Window window = new Window();
        Light light = new Light();
        light.setOn(true);
        light.setAutoMode(true);
        Door doors[] = new Door[]{door};
        Window windows[] = new Window[]{window};
        Light lights[] = new Light[]{light};
        Room room = new Room(doors,lights,windows);
        ParentPermission perm = new ParentPermission();
        room.addPerson("Nam", perm);

        Logger.info("example log");

    }
}