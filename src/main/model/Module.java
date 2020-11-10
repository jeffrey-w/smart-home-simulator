package main.model;

import main.util.ModuleBuilder;

import java.util.*;

import static main.util.NameValidator.validateName;

/**
 * The {@code Module} class represents a bundle of functionality that a user can make use of in a smart home
 * simulation.
 *
 * @author Jeff Wilgus
 */
public final class Module {

    /**
     * Core functionality for a smart home simulator.
     */
    public static final Module SHC = (new ModuleBuilder())
            .addCommandFor("Doors", Action.TOGGLE_DOOR)
            .addCommandFor("Doors", Action.TOGGLE_LOCK_DOOR)
            .addCommandFor("Lights", Action.TOGGLE_LIGHT)
            .addCommandFor("Lights", Action.TOGGLE_AUTO_LIGHT)
            .addCommandFor("Windows", Action.TOGGLE_WINDOW)
            .addCommandFor("Windows", Action.TOGGLE_BLOCK_WINDOW)
            .addName("SHC")
            .build();

    /**
     * Functionality related to security.
     */
    public static final Module SHP = (new ModuleBuilder())
            .addCommandFor("Away Mode", Action.SET_AWAY_MODE)
            .addCommandFor("Away Mode", Action.SET_AWAY_MODE_DELAY)
            .addCommandFor("Away Mode", Action.SET_AWAY_MODE_LIGHTS)
            .addName("SHP")
            .build();

    private final String name;
    private final Map<String, Set<Action>> commands;

    /**
     * Constructs a new {@code Module} with the specified {@code name}.
     *
     * @param name The specified name
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _, ])
     */
    public Module(String name) {
        this.name = validateName(name);
        commands = new HashMap<>();
    }

    /**
     * Adds the specified {@code action} to the specified {@code item} of this {@code Module}.
     *
     * @param item the specified item
     * @param action the specified {@code Action}
     * @throws IllegalArgumentException If the specified {@code item} is not a non-empty string of word characters(i.e.
     * [a-z, A-Z, 0-9, _, ])
     * @throws NullPointerException If the specified {@code action} is {@code null}
     */
    public void addCommand(String item, Action action) {
        Set<Action> actions = commands.getOrDefault(validateName(item), new HashSet<>());
        actions.add(Objects.requireNonNull(action));
        commands.putIfAbsent(item, actions);
    }

    /**
     * Adds the specified {@code action} to the specified {@code item} of this {@code Module}.
     *
     * @param item the specified item
     * @param actions the specified {@code Action}
     * @throws IllegalArgumentException If the specified {@code item} is not a non-empty string of word characters(i.e.
     * [a-z, A-Z, 0-9, _, ])
     * @throws NullPointerException If {@code actions} is {@code null} or any of the {@code Action}s therein are {@code
     * null}
     */
    public void addCommands(String item, Collection<Action> actions) {
        Set<Action> actionSet = commands.getOrDefault(validateName(item), new HashSet<>());
        actionSet.addAll(actions);
        commands.putIfAbsent(item, actionSet);
    }

    /**
     * @return The name of this {@code Module}
     */
    public String getName() {
        return name;
    }

    /**
     * @return The items that this {@code Module} provides functionality for
     */
    public Set<String> getItems() {
        return Collections.unmodifiableSet(commands.keySet());
    }

    /**
     * Provides the {@code Action}s related to the specified {@code item}
     *
     * @param item the specified item
     * @return the {@code Action}s that may performed on the specified {@code item}
     * @throws IllegalArgumentException If the specified {@code item} is not a non-empty string of word characters(i.e.
     * [a-z, A-Z, 0-9, _, ])
     */
    public Set<Action> getActionsFor(String item) {
        return Collections.unmodifiableSet(commands.get(validateName(item)));
    }

}
