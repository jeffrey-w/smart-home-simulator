package permissions;

import java.util.EnumSet;
import java.util.Set;

public class Parent extends Person {

    private static final Set<Action> ALLOWED = EnumSet.allOf(Action.class);

    public Parent(String name) {
        super(name);
    }

    @Override
    public Set<Action> allowed() {
        return ALLOWED;
    }

}
