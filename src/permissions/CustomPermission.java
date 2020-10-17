package permissions;

import java.util.EnumSet;
import java.util.Set;

import static util.NameValidator.validateName;

public class CustomPermission extends Person {

    private final String name;
    private final Set<Action> allowed;

    public CustomPermission(String name, String... actions) {
        super(name);
        this.name = validateName(name);
        allowed = EnumSet.noneOf(Action.class);
        for (String action : actions) {
            allowed.add(Action.valueOf(action)); // TODO handle IllegalArgumentException
        }
    }

    @Override
    public Set<Action> allowed() {
        return allowed;
    }

    @Override
    public String toString() {
        return name;
    }
}
