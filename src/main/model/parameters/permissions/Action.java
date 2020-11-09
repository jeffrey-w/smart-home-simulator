package main.model.parameters.permissions;

import main.model.elements.Door;
import main.model.elements.Window;
import main.model.elements.Light;
import main.model.elements.Manipulable;

/**
 * An {@code Action} describes an attempt on the part of an actor to change a simulation, which requires {@code
 * Permission}.
 *
 * @author Jeff Wilgus
 * @author Philippe Vo
 * @see Permission
 *
 * FIXME       : Maybe it would be better to have the logic of
 * FIXME cont. : "not being able to open a window because its blocked inside window.setOpen() itself ? "
 */
public enum Action {

    CHANGE_TEMPERATURE {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return false;
        }

        @Override
        public String doAction(Manipulable manipulable) {
            return "in progress";
        }

        @Override
        public String toString() {
            return "Change Temperature";
        }
    },

    TOGGLE_LOCK_DOOR {
        @Override
        public boolean isChildPermissible() {
            return true;
        }

        @Override
        public boolean isGuestPermissible() {
            return false;
        }

        @Override
        public String doAction(Manipulable manipulable) {
            Door door = (Door) manipulable;
            boolean requestedState = !door.isLocked();
            if (requestedState == true && door.isOpen()) {
                return "Please close this door first.";
            } else {
                door.setLocked(!door.isLocked());
                String message = door.isLocked() ? "Door has been locked." :  "Door has been unlocked";
                return message;
            }
        }

        @Override
        public String toString() {
            return "Toggle Lock Door";
        }

    },

    // toggle open/close door
    TOGGLE_DOOR {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return true;
        }

        @Override
        public String doAction(Manipulable manipulable) {
            Door door = (Door) manipulable;
            boolean requestedState = !door.isOpen();
            if(requestedState == true && door.isLocked()){
                return "Please unlock this door first.";
            }
            else{
                door.setOpen(!door.isOpen());
                String message = door.isOpen() ? "Door has been opened." :  "Door has been closed";
                return message;
            }
        }

        @Override
        public String toString() {
            return "Toggle Door";
        }
    },

    TOGGLE_WINDOW {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return true;
        }

        @Override
        public String doAction(Manipulable manipulable) {
            Window window = (Window) manipulable;
            if(window.isObstructed()){
                return "Please unblock this window first.";
            }
            else{
                window.setOpen(!window.isOpen());
                String message = window.isOpen() ? "Window has been opened." :  "Window has been closed";
                return message;
            }
        }

        @Override
        public String toString() {
            return "Open Window";
        }

    },

    TOGGLE_LIGHT {
        @Override
        public boolean isChildPermissible() {
            return true;
        }

        @Override
        public boolean isGuestPermissible() {
            return true;
        }

        @Override
        public String toString() {
            return "Turn On Light";
        }

        @Override
        public String doAction(Manipulable manipulable) {
            Light light = (Light) manipulable;
            light.setOn(!light.isOn());
            String message = light.isOn() ? "Light has been opened." :  "Light has been closed";
            return message;
            }
    },

    TOGGLE_BLOCK_WINDOW {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return false;
        }

        @Override
        public String toString() {
            return "Block Window";
        }
        @Override
        public String doAction(Manipulable manipulable) {
                Window window = (Window) manipulable;
                window.setObstructed(!window.isObstructed());
                String message = window.isObstructed() ? "Window has been blocked." :  "Window has been unblocked";
                return message;
                }
    };

    /**
     * @return {@code true} if this {@code Action} is allowed to be taken by a child.
     */
    public abstract boolean isChildPermissible();

    /**
     * @return {@code true} if this {@code Action} is allowed to be taken by a guest.
     */
    public abstract boolean isGuestPermissible();

    /**
     * Performs this {@code Action} on the specified {@code manipulable}.
     *
     * @param manipulable The specified {@code Manipulable}
     * @return A description of the result of performing this {@code Action} on the specified {@code manipulable}
     * @throws ClassCastException If this {@code Action} cannot be performed on the type of item the specified {@code
     * manipulable} is
     */
    public abstract String doAction(Manipulable manipulable);

    @Override
    public abstract String toString();

}
