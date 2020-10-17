package permissions;

import java.util.Objects;
import java.util.Set;

/**
 * The {@code AbstractPermission} class provides a minimal implementation of the {@code Permission} interface.
 *
 * @author Jeff Wilgus
 */
public abstract class AbstractPermission implements Permission {

    /**
     * A collection of default {@code Permission} levels.
     */
    public static final Permission[] PERMISSIONS = new Permission[] { // TODO rethink this
            new ParentPermission(),
            new ChildPermission(),
            new GuestPermission(),
            new StrangerPermission()
    };

    @Override
    public void authorize(Action action) {
        if (!allowed().contains(Objects.requireNonNull(action))) {
            throw new IllegalArgumentException("You do not have permission to perform that action.");
        }
    }

    /**
     *
     * @return the set of {@code Action}s that this {@code Permission} level is allowed to perform
     */
    public abstract Set<Action> allowed();

    @Override
    public String toString() {
        String name = getClass().getSimpleName();
        return name.substring(0, name.indexOf("Permission"));
    }

}
