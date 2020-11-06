package main.model;

import main.model.parameters.permissions.Action;
import main.util.ModuleBuilder;

import java.util.*;

import static main.util.NameValidator.validateName;

public final class Module {

    public static final Module SHC = (new ModuleBuilder())
            .addCommandFor("Doors", Action.TOGGLE_DOOR)
            .addCommandFor("Doors", Action.TOGGLE_LOCK_DOOR)
            .addCommandFor("Lights", Action.TOGGLE_LIGHT)
            .addCommandFor("Windows", Action.TOGGLE_WINDOW)
            .addCommandFor("Windows", Action.TOGGLE_BLOCK_WINDOW)
            .addName("SHC")
            .build();

    public static final Module SHP = (new ModuleBuilder())
            //.addCommandFor("Away Mode", Action.SET_AWAY_MODE) TODO
            //.addCommandFor("Lights", Action.SET_AWAY_MODE_LIGHTS)
            .addName("SHP")
            .build();

    private final String name;
    private final Map<String, Set<Action>> commands;


    public Module(String name) {
        this.name = validateName(name);
        commands = new HashMap<>();
    }

    public void addCommand(String item, Action action) {
        Set<Action> actions = commands.getOrDefault(item, new HashSet<>()); // TODO validate item
        actions.add(Objects.requireNonNull(action));
        commands.putIfAbsent(item, actions);
    }

    public void addCommands(String item, Collection<Action> actions) {
        Set<Action> actionSet = commands.getOrDefault(item, new HashSet<>()); // TODO validate item
        actionSet.addAll(actions);
        commands.putIfAbsent(item, actionSet);
    }

    public String getName() {
        return name;
    }

    public Set<String> getItems() {
        return Collections.unmodifiableSet(commands.keySet());
    }

    public Set<Action> getActionsFor(String item) {
        return Collections.unmodifiableSet(commands.get(item)); // TODO validate item
    }

}
