package main.view;

import main.model.parameters.permissions.Action;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ActionPanel} class provides the UI elements to choose a type of item and the action to perform on it
 * during a simulation.
 *
 * @author Jeff Wilgus
 */
public class ActionPanel extends JPanel { // TODO use JSplitPlane

    private static final Map<String, Action[]> ACTIONS = new HashMap<>();

    static {
        ACTIONS.put("Windows", new Action[] {Action.OPEN_WINDOW, Action.BLOCK_WINDOW});
    }

    DefaultListModel<Action> actionsModel = new DefaultListModel<>();
    JList<String> items = new JList<>(ACTIONS.keySet().toArray(new String[0]));
    JList<Action> actions = new JList<>(actionsModel);

    /**
     * Constructs a new {@code ActionPanel} object.
     */
    public ActionPanel() {
        JPanel top = new JPanel(new BorderLayout()), bottom = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        add(top);
        add(bottom, BorderLayout.SOUTH);
        top.add(new JLabel("Items"), BorderLayout.NORTH);
        top.add(items);
        bottom.add(new JLabel("Actions"), BorderLayout.NORTH);
        bottom.add(actions);
        items.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (items.getSelectedIndex() == -1) {
                    actionsModel.removeAllElements();
                } else {
                    actionsModel.removeAllElements();
                    for (Action action : ACTIONS.get(items.getSelectedValue())) {
                        actionsModel.addElement(action);
                    }
                }
            }
        });
    }

    public String getSelectedItem() {
        return items.getSelectedValue();
    }

    public Action getSelectedAction() {
        return actions.getSelectedValue();
    }

}
