package main.controller;

import main.model.Action;
import main.model.Module;
import main.model.ValueManipulable;
import main.model.parameters.Parameters;
import main.view.ItemChooser;
import main.view.ModuleView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CoreModuleController extends AbstractModuleController implements ActionListener {

    public CoreModuleController(Controller parent, ModuleView view) {
        super(parent, Module.SHC, view);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (parent.canAct()) {
            Parameters parameters = parent.getParameters();
            Action action = parent.getSelectedAction();
            if (action.equals(Action.TOGGLE_AUTO_LIGHT)) {
                performCommand(new ValueManipulable<>(!parameters.isAutoLight()), action);
                parent.toggleAutoLight();
            } else {
                ItemChooser chooser = ItemChooser.of(parent.getItemsForSelection());
                chooser.addActionListener(f -> {
                    performCommand(chooser.getSelectedItem(), action);
                    chooser.dispose();
                });
                chooser.pack();
                chooser.setLocationRelativeTo(parent.getDashboard());
                chooser.setVisible(true);
            }
            parent.redrawHouse();
        }
    }

}
