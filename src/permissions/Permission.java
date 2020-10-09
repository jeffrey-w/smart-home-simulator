package permissions;

import users.User;

/**
 * The {@code Permission} interface describes an authorization level that allows for {@code User}s to make certain changes to the SHS system, and prevents them from making others.
 *
 * @author Jeff Wilgus
 * @see User
 */
public interface Permission {

    /**
     * Allows the specified {@code action} to occur if and only if this {@code Permission} level is authorized to do so.
     * @param action the specified {@code Action}
     * @throws IllegalArgumentException if {@code action} is disallowed at this {@code Permission} level
     * @throws NullPointerException if {@code action} is {@code null}
     */
    void authorize(AbstractPermission.Action action);

}
