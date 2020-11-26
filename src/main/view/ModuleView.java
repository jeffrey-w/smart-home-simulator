package main.view;

import main.model.Action;
import main.model.Module;

import javax.swing.*;
import java.awt.*;

public class ModuleView extends JPanel {

    private static final int NUM_ROWS = 2;

    final DefaultListModel<String> itemsModel = new DefaultListModel<>();
    final DefaultListModel<Action> actionsModel = new DefaultListModel<>();
    final JList<String> items = new JList<>(itemsModel);
    final JList<Action> actions = new JList<>(actionsModel);

    public ModuleView(Module module) {
        JScrollPane itemsPane = new JScrollPane(items);
        JScrollPane actionPane = new JScrollPane(actions);
        setLayout(new GridLayout(NUM_ROWS, 1));
        add(itemsPane);
        add(actionPane);
        for (String item : module.getItems()) {
            itemsModel.addElement(item);
        }
        items.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (items.getSelectedIndex() == -1) {
                    actionsModel.removeAllElements();
                } else {
                    actionsModel.removeAllElements();
                    for (Action action : module.getActionsFor(items.getSelectedValue())) {
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
