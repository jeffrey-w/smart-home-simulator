package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ActionPanel extends JPanel {

    Map<String, String[]> ACTIONS =
            Map.of("Windows", new String[] {"Open/Close", "Obstruct"}, "Thermostat", new String[] {"Set Temperature"});

    DefaultListModel<String> actionsModel = new DefaultListModel<>();
    JList<String> items = new JList<>(ACTIONS.keySet().toArray(new String[0]));
    JList<String> actions = new JList<>(actionsModel);

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
                }
            }
        });
    }

}
