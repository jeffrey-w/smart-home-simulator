package main.model.parameters.permissions;

import main.model.Action;
import main.util.PermissionDeniedException;

import java.util.Set;

/**
 * The {@code Permission} interface describes an authorization level that allows for actors to make certain changes to a
 * simulation, and prevents them from making others.
 *
 * @author Jeff Wilgus
 * @see Action
 */
public interface Permission {

    /**
     * Allows the specified {@code action} to occur if and only if this {@code Permission} level is authorized to do
     * so.
     *
     * @param action The specified {@code Action}
     * @return The specified {@code Action}
     * @throws NullPointerException If {@code action} is {@code null}
     * @throws PermissionDeniedException If {@code action} is disallowed at this {@code Permission} level
     */
    Action authorize(Action action);

    /**
     * Provides the {@code Action}s that this {@code Permission} level is allowed to perform.
     *
     * @return The set of this {@code Permission}'s allowed {@code Action}s
     */
    Set<Action> allowed();

    /**
     * Adds the specified {@code action} to this {@code Permission} level's permissible {@code Action}s.
     * Applied to a {@code Permission}, it is applied to all other permissions of the same level.
     *      e.g.: adding the ToggleDoor action to a child-level Permission grants that permission to all child-level users
     *
     * @param action The {@code Action} to be added to this {@code Permission}
     */
    void allow(Action action);

    /**
     * Removes the specified {@code action} from this {@code Permission} level's permissible {@code Actions}.
     * Applied to a {@code Permission}, it is applied to all other permissions of the same level.
     *      e.g.: removing the ToggleDoor action to a child-level Permission removes that permission for all child-level users
     *
     * @param action The {@code Action} to be removed from this {@code Permission}
     */
    void disallow(Action action);

}
