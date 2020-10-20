package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code ActionPanel} class provides the UI main.model.elements to choose a type of item and the action to perform
 * on it during a simulation.
 *
 * @author Jeff Wilgus
 */
public class ActionPanel extends JPanel {

    static MouseListener WINDOW_ACTION_LISTENER; // TODO make this a member?
    private static final Map<String, String[]> ACTIONS = new HashMap<>();

    static {
        ACTIONS.put("Windows", new String[] {"Open/Close", "Obstruct"});
        ACTIONS.put("Thermostat", new String[] {"Set Temperature"});
    }

    DefaultListModel<String> actionsModel = new DefaultListModel<>();
    JList<String> items = new JList<>(ACTIONS.keySet().toArray(new String[0]));
    JList<String> actions = new JList<>(actionsModel);

    /**
     * Constructs a new {@code ActionPanel} object.
     */
    public ActionPanel() {
        JPanel top = new JPanel(new BorderLayout());
        JPanel bottom = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        add(top, BorderLayout.NORTH);
        add(bottom, BorderLayout.SOUTH);
        top.add(new JLabel("Items"), BorderLayout.NORTH);
        top.add(new JScrollPane(items));
        bottom.add(new JLabel("Actions"), BorderLayout.NORTH);
        bottom.add(new JScrollPane(actions));
        items.setPreferredSize(new Dimension(576 - 32, 215)); // TODO avoid magic constants
        actions.setPreferredSize(new Dimension(576 - 32, 215));
        items.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (items.getSelectedIndex() == -1) {
                    actionsModel.removeAllElements();
                } else {
                    actionsModel.removeAllElements();
                    for (String action : ACTIONS.get(items.getSelectedValue())) {
                        actionsModel.addElement(action);
                    }
                    switch (items.getSelectedValue()) {
                        case "Windows":
                            actions.addMouseListener(WINDOW_ACTION_LISTENER);
                            break;
                        // TODO other event handlers
                    }
                }
            }
        });
    }

}
