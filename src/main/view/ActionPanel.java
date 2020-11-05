package main.view;

import main.model.parameters.permissions.Action;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ActionPanel} class provides the UI elements to choose a type of item and the action to perform on it
 * during a simulation.
 *
 * @author Jeff Wilgus
 */
public class ActionPanel extends JPanel {

    private static final int NUM_ROWS = 2;
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
        JScrollPane itemPane = new JScrollPane(items);
        JScrollPane actionPane = new JScrollPane(actions);
        setLayout(new GridLayout(NUM_ROWS, 1));
        itemPane.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "Items"));
        actionPane.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), "Actions"));
        add(itemPane);
        add(actionPane);
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

    /**
     * Provides the {@code House} item currently selected by this {@code ActionPanel}.
     *
     * @return The selected {@code House} item
     */
    public String getSelectedItem() {
        return items.getSelectedValue();
    }

    /**
     * Provides the {@code Action} currently selected by this {@code ActionPanel}.
     *
     * @return The selected {@code Action}
     */
    public Action getSelectedAction() {
        return actions.getSelectedValue();
    }

}
