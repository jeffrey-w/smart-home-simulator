package main.model.parameters.permissions;

import java.util.Objects;
import java.util.Set;

/**
 * The {@code AbstractPermission} class provides a minimal implementation of the {@code Permission} interface.
 *
 * @author Jeff Wilgus
 */
public abstract class AbstractPermission implements Permission {

    @Override
    public void authorize(Action action) {
        if (!allowed().contains(Objects.requireNonNull(action))) {
            throw new IllegalArgumentException("You do not have permission to perform that action.");
        }
    }

    /**
     * @return The set of {@code Action}s that this {@code Permission} level is allowed to perform
     */
    public abstract Set<Action> allowed();

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof AbstractPermission)) {
            return false;
        }
        return allowed().equals(((AbstractPermission) obj).allowed());
    }

    @Override
    public int hashCode() {
        return allowed().hashCode();
    }

    @Override
    public String toString() {
        String name = getClass().getSimpleName();
        return name.substring(0, name.indexOf("Permission"));
    }

}
