package main.util;

import main.model.Module;
import main.model.Action;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static main.util.NameValidator.validateName;

public class ModuleBuilder {

    private static class CommandEntry implements Map.Entry<String, Action> {

        String key;
        Action value;

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

    public ModuleBuilder() {
        entries = new LinkedList<>();
    }

    public ModuleBuilder addName(String name) {
        this.name = validateName(name);
        return this;
    }

    public ModuleBuilder addCommandFor(String item, Action command) {
        entries.add(new CommandEntry(item, command));
        return this;
    }

    public Module build() {
        Module module = new Module(name); // TODO throws NullPointerException
        for (Map.Entry<String, Action> entry : entries) {
            module.addCommand(entry.getKey(), entry.getValue());
        }
        return module;
    }

}
