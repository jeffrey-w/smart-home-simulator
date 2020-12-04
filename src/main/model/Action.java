package main.model;

import main.model.elements.Door;
import main.model.elements.House;
import main.model.elements.Light;
import main.model.elements.Window;
import main.model.parameters.Clock;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;

import java.time.LocalTime;
import java.util.Set;

/**
 * An {@code Action} describes an attempt on the part of an actor to change a simulation, which requires {@code
 * Permission}.
 *
 * @author Jeff Wilgus
 * @author Philippe Vo
 * @see Permission
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
            try {
                door.setLocked(!door.isLocked());
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
            return door.isLocked() ? "Door has been locked." : "Door has been unlocked";
        }

        @Override
        public String toString() {
            return "Lock/Unlock Door";
        }

    },

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
            try {
                door.setOpen(!door.isOpen());
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
            return door.isOpen() ? "Door has been opened." : "Door has been closed";
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
            try {
                window.setOpen(!window.isOpen());
            } catch (IllegalStateException e) {
                return e.getMessage();
            }
            return window.isOpen() ? "Window has been opened." : "Window has been closed";
        }

        @Override
        public String toString() {
            return "Open/Close Window";
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
            window.setBlocked(!window.isBlocked());
            return window.isBlocked() ? "Window has been blocked." : "Window has been unblocked";
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
            if (house.hasObstructedWindow() && !parameters.isAwayMode()) {
                return "There are blocked windows in the house. Please unblock them before setting away mode.";
            }
            if (house.getNumberOfPeople() > 0 && !parameters.isAwayMode()) {
                return "Away mode can only be set when no one is home";
            }
            house.closeOpenables();
            parameters.setAwayMode(!parameters.isAwayMode());
            return parameters.isAwayMode() ? "Away mode has been turned on" : "Away mode has been turned off";
        }

        @Override
        public String toString() {
            return "Turn Away Mode On/Off";
        }
    },

    SET_AWAY_MODE_LIGHTS {
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
            MultiValueManipulable multiValueManipulable = (MultiValueManipulable) manipulable;
            @SuppressWarnings("unchecked") // We catch ClassCastExceptions upstream
            Set<String> locations = (Set<String>) multiValueManipulable.getValue();
            for (String location : house.getLocations()) {
                house.getRoom(location).setAwayLight(locations.contains(location));
            }
            parameters.setAwayLightStart((LocalTime) multiValueManipulable.getValueAt(0).getValue());
            parameters.setAwayLightEnd((LocalTime) multiValueManipulable.getValueAt(1).getValue());
            return "Away light interval updated";
        }

        @Override
        public String toString() {
            return "Set Away Mode Lights";
        }
    },

    SET_AWAY_MODE_DELAY {
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
            @SuppressWarnings("unchecked")
            ValueManipulable<Integer> valueManipulable = (ValueManipulable<Integer>) manipulable;
            parameters.setAwayDelay(valueManipulable.getValue() * Clock.SECONDS_PER_MILLISECOND);
            return "Away mode delay set for " + valueManipulable.getValue() + " seconds.";
        }

        @Override
        public String toString() {
            return "Set Away Mode Delay";
        }
    };

    static final String[] PERMISSIONS = new String[] {
            "Parent",
            "Child",
            "Guest",
            "Stranger"
    };

    /**
     * @return A summary of the permissibility of this {@code Action} by each {@code Permission} level
     */
    public boolean[] isPermissibleBy(Parameters parameters) {
        boolean[] isPermissible = new boolean[PERMISSIONS.length];
        for (int i = 0; i < isPermissible.length; i++) {
            isPermissible[i] = parameters.getPermissions().get(PERMISSIONS[i]).allowed().contains(this);
        }
        return isPermissible;
    }

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
