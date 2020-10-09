package permissions;

import java.util.EnumSet;
import java.util.Set;
import java.util.regex.Pattern;

public class CustomPermission extends AbstractPermission {

    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+$");

    private static String validateName(String name) {
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("That is not a valid permission name.");
        }
        return name;
    }

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
