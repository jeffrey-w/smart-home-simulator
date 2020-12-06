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

    /**
     * Functionality related to heating.
     */
    public static final Module SHH = (new ModuleBuilder())
            .addCommandFor("Zones", Action.MANAGE_TEMPERATURE_CONTROL_ZONES)
            .addCommandFor("Temperature", Action.READ_TEMPERATURES)
            .addCommandFor("Temperature", Action.CHANGE_TEMPERATURE)
            .addCommandFor("Temperature", Action.SET_DEFAULT_TEMPERATURE)
            .addName("SHH")
            .build();

    private final String name;
    private final Map<String, Set<Action>> commands;

    /**
     * Constructs a new {@code Module} with the specified {@code name}.
     *
     * @param name The specified name
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _, ]) and whitespace
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
     * [a-z, A-Z, 0-9, _, ]) and whitespae
     * @throws NullPointerException If the specified {@code action} is {@code null}
     */
    public void addCommand(String item, Action action) {
        Set<Action> actions = commands.getOrDefault(validateName(item), new HashSet<>());
        actions.add(Objects.requireNonNull(action));
        commands.putIfAbsent(item, actions);
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
     * [a-z, A-Z, 0-9, _, ]) and whitespace
     */
    public Set<Action> getActionsFor(String item) {
        return Collections.unmodifiableSet(commands.get(validateName(item)));
    }

}
