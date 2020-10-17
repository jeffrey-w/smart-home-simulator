package permissions;

import java.util.EnumSet;
import java.util.Set;

/**
 * The {@code StrangerPermission} class specifies the {@code Action}s that a stranger actor is allowed to take in a
 * simulation.
 *
 * @author Jeff Wilgus
 */
public class StrangerPermission extends AbstractPermission {

    // A stranger can perform no actions.
    private static final Set<Action> ALLOWED = EnumSet.noneOf(Action.class);

    public Stranger(String name) {
        super(name);
    }

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
