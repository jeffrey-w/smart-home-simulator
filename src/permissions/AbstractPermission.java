package permissions;

import java.util.Objects;
import java.util.Set;

public abstract class AbstractPermission implements Permission {

    @Override
    public void authorize(Action action) {
        if (!allowed().contains(Objects.requireNonNull(action))) {
            throw new IllegalArgumentException("You do not have permission to perform that action.");
        }
    }

    public abstract Set<Action> allowed();

    @Override
    public String toString() {
        String name = getClass().getSimpleName();
        return name.substring(0, name.indexOf("Permission"));
    }

}
