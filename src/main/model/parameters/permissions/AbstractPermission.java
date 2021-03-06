package main.model.parameters.permissions;

import main.model.Action;
import main.util.PermissionDeniedException;

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
            throw new PermissionDeniedException(
                    "This action is not permissible with the level of permission you currently have.");
        }
        return action;
    }

    @Override
    public void allow(Action action) {
        allowed().add(action);
    }

    @Override
    public void disallow(Action action) {
        allowed().remove(action);
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
