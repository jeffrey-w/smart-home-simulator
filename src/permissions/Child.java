package permissions;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Child extends Person {

    private static final Set<Action> ALLOWED = EnumSet.copyOf(
            Arrays.stream(Action.values()).filter(Action::isChildPermissible).collect(Collectors.toSet()));

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
