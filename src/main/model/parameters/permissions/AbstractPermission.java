package main.model.parameters.permissions;

import main.model.Action;

import java.util.Objects;

/**
 * The {@code AbstractPermission} class provides a minimal implementation of the {@code Permission} interface.
 *
 * @author Jeff Wilgus
 */
public abstract class AbstractPermission implements Permission {

    @Override
    public Action authorize(Action action) {
        if (!allowed().contains(Objects.requireNonNull(action))) {
            throw new IllegalArgumentException("You do not have permission to perform that action.");
        }
        return action;
    }

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
