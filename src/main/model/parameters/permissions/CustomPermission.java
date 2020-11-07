package main.model.parameters.permissions;

import main.model.Action;

import java.util.EnumSet;
import java.util.Set;

import static main.util.NameValidator.validateName;

/**
 * The {@code CustomPermission} class represents a set of allowed {@code Action}s that is not captured by one of the
 * default {@code Permission} levels.
 *
 * @author Jeff Wilgus
 */
public class CustomPermission extends AbstractPermission {

    private final String name;
    private final Set<Action> allowed;

    /**
     * Constructs a new {@code CustomPermission} object.
     *
     * @param name The name of this {@code CustomPermission}
     * @param actions A list of the {@Actions} allowed by this {@code Permission} level
     * @throws IllegalArgumentException if any of the specified {@code actions} is not one specified by {@code Action}
     */
    public CustomPermission(String name, String... actions) {
        this.name = validateName(name);
        allowed = EnumSet.noneOf(Action.class);
        for (String action : actions) {
            allowed.add(Action.valueOf(action));
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
