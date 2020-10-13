package permissions;

import java.util.EnumSet;
import java.util.Set;

import static util.NameValidator.validateName;

public class CustomPermission extends Person {

    private final String name;
    private final Set<Action> allowed;

    public CustomPermission(String name, String... actions) {
        this.name = validateName(name);
        allowed = EnumSet.noneOf(Action.class);
        for (String action : actions) {
            allowed.add(Action.valueOf(action)); // TODO handle IllegalArgumentException
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public Set<Action> allowed() {
        return allowed;
    }
}
