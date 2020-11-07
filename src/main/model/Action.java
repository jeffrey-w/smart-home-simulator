package main.model;

import main.model.elements.Door;
import main.model.elements.House;
import main.model.elements.Light;
import main.model.elements.Window;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;

/**
 * An {@code Action} describes an attempt on the part of an actor to change a simulation, which requires {@code
 * Permission}.
 *
 * @author Jeff Wilgus
 * @author Philippe Vo
 * @see Permission
 */
// FIXME: Maybe it would be better to have the logic of FIXME cont. : "not being able to open a window because its blocked inside window.setOpen() itself?
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
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            return null; // TODO
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
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            Door door = (Door) manipulable;
            if (door.isOpen()) {
                return "Please close this door first.";
            } else {
                door.setLocked(!door.isLocked());
                return door.isLocked() ? "Door has been locked." : "Door has been unlocked";
            }
        }

        @Override
        public String toString() {
            return "Lock/Unlock Door";
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
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            Door door = (Door) manipulable;
            if (!door.isOpen() && door.isLocked()) {
                return "Please unlock this door first.";
            } else {
                door.setOpen(!door.isOpen());
                return door.isOpen() ? "Door has been opened." : "Door has been closed";
            }
        }

        @Override
        public String toString() {
            return "Open/Close Door";
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
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            Window window = (Window) manipulable;
            if (window.isObstructed()) {
                return "Please unblock this window first.";
            } else {
                window.setOpen(!window.isOpen());
                return window.isOpen() ? "Window has been opened." : "Window has been closed";
            }
        }

        @Override
        public String toString() {
            return "Open/Close Window";
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
            return "Turn On/Off Light";
        }

        @Override
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            Light light = (Light) manipulable;
            light.setOn(!light.isOn());
            return light.isOn() ? "Light has been opened." : "Light has been closed";
        }
    },

    TOGGLE_AUTO_LIGHT {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return false;
        }

        @Override
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            parameters.setAutoLight(!parameters.isAutoLight());
            return parameters.isAutoLight() ? "Turned on auto light mode." : "Turned off auto light mode.";
        }

        @Override
        public String toString() {
            return "Turn On/Off Auto Light";
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
            return "Block/Unblock Window";
        }

        @Override
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            Window window = (Window) manipulable;
            window.setObstructed(!window.isObstructed());
            return window.isObstructed() ? "Window has been blocked." : "Window has been unblocked";
        }

    },

    SET_AWAY_MODE {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return false;
        }

        @Override
        public String doAction(Manipulable manipulable, Parameters parameters, House house) {
            if (house.hasObstructedWindow() && !parameters.getAwayMode()) {
                return "There are blocked windows in the house. Please unblock them before setting away mode.";
            }
            if (house.getNumberOfPeople() > 0 && !parameters.getAwayMode()) {
                return "Away mode can only be set when no one is home";
            }
            parameters.setAwayMode(!parameters.getAwayMode());
            return parameters.getAwayMode() ? "Away mode has been turned on" : "Away mode has been turned off";
        }

        @Override
        public String toString() {
            return "Turn Away Mode On/Off";
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
     * Performs this {@code Action} on the specified {@code manipulable} in the context of the specified {@code
     * parameters} and {@code house}.
     *
     * @param manipulable The specified {@code Manipulable}
     * @param parameters The specified {@code Parameters}
     * @param house The specified {@code House}
     * @return A description of the result of performing this {@code Action} on the specified {@code manipulable}
     * @throws ClassCastException If this {@code Action} cannot be performed on the type of item the specified {@code
     * manipulable} is
     */
    public abstract String doAction(Manipulable manipulable, Parameters parameters, House house);

    @Override
    public abstract String toString();

}
