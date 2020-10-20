package main.model.parameters.permissions;

import java.util.EnumSet;
import java.util.Set;

/**
 * The {@code ParentPermission} class specifies the {@code Action}s that a parent actor is allowed to take in a
 * simulation.
 *
 * @author Jeff Wilgus
 */
public class ParentPermission extends AbstractPermission {

    // A parent can perform all actions.
    private static final Set<Action> ALLOWED = EnumSet.allOf(Action.class);

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
