package permissions;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The {@code ChildPermission} class specifies the {@code Action}s that a child actor is allowed to take in a
 * simulation.
 *
 * @author Jeff Wilgus
 */
public class ChildPermission extends AbstractPermission {

    // Filter the array containing all Actions on the Action.isChildPermissible predicate.
    private static final Set<Action> ALLOWED = EnumSet.copyOf(
            Arrays.stream(Action.values()).filter(Action::isChildPermissible).collect(Collectors.toSet()));

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
