package main.view;

import main.model.Action;
import main.model.Module;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * The {@code ActionPanel} class provides the UI elements to choose a type of item and the action to perform on it
 * during a simulation.
 *
 * @author Jeff Wilgus
 */
public class ActionPanel extends JTabbedPane {

    final List<JList<Action>> actions = new LinkedList<>();

    /**
     * Adds a {@code module} to the {@code ActionPanel}
     *
     * @param module The module to be added
     * @return The {@code ModuleView}
     */
    public ModuleView addModule(Module module) {
        ModuleView view = new ModuleView(module);
        actions.add(view.actions);
        addTab(module.getName(), view);

        return view;
    }

}
