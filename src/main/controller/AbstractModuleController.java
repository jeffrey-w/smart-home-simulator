package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.parameters.Parameters;
import main.view.Dashboard;
import main.model.Module;
import main.view.ModuleView;

import java.util.Objects;

public abstract class AbstractModuleController {

    final Controller parent;
    final Module module;
    final ModuleView view;

    /**
     * Constructs a new {@code AbstractModuleController} with the specified {@code parent}, {@code module}, and {@code
     * view}.
     *
     * @param parent The {@code Controller} to which this {@code AbstractModuleController} is subordinate to
     * @param module The {@code module} that this {@code AbstractModuleController} controls
     * @param view The {@code ModuleView} that this {@code AbstractModuleController} controls
     * @throws NullPointerException If the specified {@code parent}, {@code module}, or {@code view} is {@code null}
     */
    public AbstractModuleController(Controller parent, Module module, ModuleView view) {
        this.parent = Objects.requireNonNull(parent);
        this.module = Objects.requireNonNull(module);
        this.view = Objects.requireNonNull(view);
    }

    void performCommand(Manipulable manipulable, Action action) {
        Parameters parameters = parent.getParameters();
        try {
            parent.sendToConsole(
                    manipulable.manipulate(parameters.getPermission().authorize(action), parameters, parent.getHouse()),
                    Dashboard.MessageType.NORMAL);
        } catch (Exception e) {
            parent.sendToConsole(e.getMessage(), Dashboard.MessageType.ERROR);
        }
    }

}
