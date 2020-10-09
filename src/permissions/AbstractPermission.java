package permissions;

import users.User;

import java.util.Objects;
import java.util.Set;

public abstract class AbstractPermission implements Permission {

    @Override
    public void authorize(Action action) {
        if (!allowed().contains(Objects.requireNonNull(action))) {
            throw new IllegalArgumentException("You do not have permission to perform that action.");
        }
    }

    public abstract Set<Action> allowed();

    /**
     * An {@code Action} specifies an attempt on the part of a {@code User} perform a change on the SHS system that requires {@code Permission}.
     *
     * @author Jeff Wilgus
     * @see Permission
     * @see User
     */
    public enum Action {

        OPEN_DOOR {
            @Override
            public boolean isChildPermissible() {
                return false;
            }

            @Override
            public boolean isGuestPermissible() {
                return false;
            }
        };

        public abstract boolean isChildPermissible();
        public abstract boolean isGuestPermissible();

    }
}
