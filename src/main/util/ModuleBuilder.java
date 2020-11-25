package main.util;

import main.model.Action;
import main.model.Module;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static main.util.NameValidator.validateName;

/**
 * The {@code ModuleBuilder} class is a utility for easily creating new {@code Module}s. It provides a fluent API for
 * chained calls to methods that allow clients to compose bundles of functionality in an incremental way.
 *
 * @author Jeff Wilgus
 * @see Module
 */
public class ModuleBuilder {

    private static class CommandEntry implements Map.Entry<String, Action> {

        final String key;
        final Action value;

        CommandEntry(String key, Action value) {
            this.key = validateName(key);
            this.value = Objects.requireNonNull(value);
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public Action getValue() {
            return value;
        }

        @Override
        public Action setValue(Action value) {
            throw new UnsupportedOperationException();
        }
    }

    private String name;
    private final List<Map.Entry<String, Action>> entries;

    /**
     * Constructs a new {@code ModuleBuilder} object.
     */
    public ModuleBuilder() {
        entries = new LinkedList<>();
    }

    /**
     * Specifies the {@code name} to be used by this {@code ModuleBuilder} when building a new {@code Module}.
     *
     * @param name The specified name
     * @return This {@code ModuleBuilder}
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _, ])
     */
    public ModuleBuilder addName(String name) {
        this.name = validateName(name);
        return this;
    }

    /**
     * Specifies a new simulator function related to the specified {@code item} involving the specified {@code command}
     * to be used by this {@code ModuleBuilder} when building a new {@code Module}.
     *
     * @param item The specified item
     * @param command The specified command
     * @return This {@code ModuleBuilder}
     * @throws IllegalArgumentException If the specified {@code name} is not a non-empty string of word characters (i.e.
     * [a-z, A-Z, 0-9, _, ])
     * @throws NullPointerException If the specified {@code command} is {@code null}
     */
    public ModuleBuilder addCommandFor(String item, Action command) {
        entries.add(new CommandEntry(item, command));
        return this;
    }

    /**
     * Provides a new {@code Module} composed of the functionality specified earlier with calls to {@link
     * #addName(String)} and {@link #addCommandFor(String, Action)}.
     *
     * @return A new {@code Module}
     */
    public Module build() {
        Module module = new Module(name);
        for (Map.Entry<String, Action> entry : entries) {
            module.addCommand(entry.getKey(), entry.getValue());
        }
        return module;
    }

}
