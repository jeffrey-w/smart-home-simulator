package permissions;

import controler.ISource;

import java.util.Objects;
import java.util.Set;

public abstract class Person implements Permission, ISource {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void authorize(Action action) {
        if (!allowed().contains(Objects.requireNonNull(action))) {
            throw new IllegalArgumentException("You do not have permission to perform that action.");
        }
    }

    public abstract Set<Action> allowed();

}
