package main.model.parameters.permissions;

import main.model.Action;

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
     * @throws IllegalArgumentException If {@code action} is disallowed at this {@code Permission} level
     * @throws NullPointerException If {@code action} is {@code null}
     */
    Action authorize(Action action);

    /**
     * Provides the {@code Action}s that this {@code Permission} level is allowed to perform.
     * @return The set of this {@code Permission}'s allowed {@code Action}s
     */
    Set<Action> allowed();

    /**
     * Allows user to add an {@code Action} to their list of permissible actions.
     * Applied to a {@code Permission}, it is applied to all other permissions of the same level.
     *      e.g.: adding the ToggleDoor action to a child-level Permission grants that permission to all child-level users
     *
     * @param action The {@code Action} to be added to the users' permissions
     */
    void addPermission(Action action);

    /**
     * Allows user to remove an {@code action} from their list of permissible actions.
     * Applied to a {@code Permission}, it is applied to all other permissions of the same level.
     *      e.g.: removing the ToggleDoor action to a child-level Permission removes that permission for all child-level users
     *
     * @param action The {@code Action} to be removed from the users' permissions
     */
    void removePermission(Action action);

}
