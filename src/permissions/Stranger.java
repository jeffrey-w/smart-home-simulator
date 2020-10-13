package permissions;

import java.util.EnumSet;
import java.util.Set;

public class Stranger extends Person {

    private static final Set<Action> ALLOWED = EnumSet.noneOf(Action.class);

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
