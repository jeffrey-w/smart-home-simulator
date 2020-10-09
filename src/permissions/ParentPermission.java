package permissions;

import java.util.EnumSet;
import java.util.Set;

public class ParentPermission extends AbstractPermission {

    private static final Set<Action> ALLOWED = EnumSet.allOf(Action.class);

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
