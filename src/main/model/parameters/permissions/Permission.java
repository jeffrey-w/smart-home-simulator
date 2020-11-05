package main.model.parameters.permissions;

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
     * @return the specified {@code Action}
     * @throws IllegalArgumentException if {@code action} is disallowed at this {@code Permission} level
     * @throws NullPointerException if {@code action} is {@code null}
     */
    Action authorize(Action action);

}
