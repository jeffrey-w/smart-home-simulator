package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.parameters.Parameters;
import main.util.PermissionDeniedException;
import main.view.Dashboard;
import main.view.ModuleView;

import java.util.Objects;

/**
 * The {@code AbstractModuleController} class provides a minimal implementation of the {@code ModuleController}
 * interface.
 *
 * @author Jeff Wilgus
 */
public abstract class AbstractModuleController implements ModuleController {

    final Controller parent;
    final ModuleView view;

    /**
     * Constructs a new {@code AbstractModuleController} with the specified {@code parent} and {@code view}.
     *
     * @param parent The {@code Controller} to which this {@code AbstractModuleController} is subordinate to
     * @param view The {@code ModuleView} that this {@code AbstractModuleController} controls
     * @throws NullPointerException If the specified {@code parent} or {@code view} is {@code null}
     */
    public AbstractModuleController(Controller parent, ModuleView view) {
        this.parent = Objects.requireNonNull(parent);
        this.view = Objects.requireNonNull(view);
    }

    void performActionOn(Action action, Manipulable manipulable) {
        Parameters parameters = parent.getParameters();
        try {
            parent.sendToConsole(
                    manipulable.manipulate(parameters.getPermission().authorize(action), parameters, parent.getHouse()),
                    Dashboard.MessageType.NORMAL);
        } catch (PermissionDeniedException e) {
            parent.sendToConsole(e.getMessage(), Dashboard.MessageType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
