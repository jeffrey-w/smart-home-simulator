package main.model.parameters.permissions;

import main.model.Action;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code GuestPermission} class specifies the {@code Action}s that a guest actor is allowed to take in a
 * simulation.
 *
 * @author Jeff Wilgus
 */
public class GuestPermission extends AbstractPermission {

    // Filter the set of all Actions on the Action.isGuestPermissible predicate.
    private static final Set<Action> ALLOWED = EnumSet.copyOf(
        Arrays.stream(Action.values()).filter(Action::isGuestPermissible).collect(Collectors.toSet())
    );

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
