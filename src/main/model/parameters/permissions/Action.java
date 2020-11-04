package main.model.parameters.permissions;

import main.model.elements.Door;
import main.model.elements.Manipulable;

/**
 * An {@code Action} describes an attempt on the part of an actor to change a simulation, which requires {@code
 * Permission}.
 *
 * @author Jeff Wilgus
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
        public String toString() {
            return "Change Temperature";
        }

    },

    LOCK_DOOR {
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
            if (door.isOpen()) {
                return "Please close this door first.";
            } else {
                door.setLocked(!door.isLocked());
                return "Door has been locked.";
            }
        }

        @Override
        public String toString() {
            return "Lock Door";
        }

    },

    OPEN_DOOR {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return true;
        }

        @Override
        public String toString() {
            return "Open Door";
        }

    },

    OPEN_WINDOW {
        @Override
        public boolean isChildPermissible() {
            return false;
        }

        @Override
        public boolean isGuestPermissible() {
            return true;
        }

        @Override
        public String toString() {
            return "Open Window";
        }

    },

    TURN_ON_LIGHT {
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

    },

    BLOCK_WINDOW {
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
