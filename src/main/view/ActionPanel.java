package main.view;

import main.model.Module;
import main.model.Action;

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

    List<JList<Action>> actions = new LinkedList<>();

    public void addModule(Module module) {
        ModuleView view = new ModuleView(module);
        actions.add(view.actions);
        addTab(module.getName(), view);
    }

    /**
     * Provides the {@code House} item currently selected by this {@code ActionPanel}.
     *
     * @return The selected {@code House} item
     */
    public String getSelectedItem() {
        return ((ModuleView)getSelectedComponent()).items.getSelectedValue();
    }

    /**
     * Provides the {@code Action} currently selected by this {@code ActionPanel}.
     *
     * @return The selected {@code Action}
     */
    public Action getSelectedAction() {
        return ((ModuleView)getSelectedComponent()).actions.getSelectedValue();
    }

}
