package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.MultiValueManipulable;
import main.model.ValueManipulable;
import main.view.Dashboard;
import main.view.ModuleView;

import javax.swing.*;

public class HeatingModuleController extends AbstractModuleController {

    private static final Manipulable EMPTY_MANIPULABLE = new ValueManipulable<>(new Object());

    public HeatingModuleController(Controller parent, ModuleView view) {
        super(parent, view);
    }

    @Override
    public void control() {
        if (canAct()) {
            switch (view.getSelectedAction()) {
                case CHANGE_TEMPERATURE:
                    MultiValueManipulable manipulable = new MultiValueManipulable(parent.getParameters().getLocation());
                    try {
                        manipulable.addValue(Integer.parseInt(JOptionPane
                                .showInputDialog(parent.getDashboard(), "Enter a temperature.", "Change Temperature",
                                        JOptionPane.PLAIN_MESSAGE)));
                    } catch (NumberFormatException e) {
                        parent.sendToConsole("Please enter an integer.", Dashboard.MessageType.ERROR);
                        return;
                    }
                    performActionOn(Action.CHANGE_TEMPERATURE, manipulable);
                    break;
                case READ_TEMPERATURES:
                    performActionOn(Action.READ_TEMPERATURES, EMPTY_MANIPULABLE);
                    break;
                case SET_DEFAULT_TEMPERATURE:
                    MultiValueManipulable defTempManipulable = new MultiValueManipulable(parent.getParameters().getTemperature()); // FIXME : dont really need the getTemp ?
                    try {
                        int defWinterTempZone = Integer.parseInt(JOptionPane
                                .showInputDialog(parent.getDashboard(), "Enter a default winter temperature.", "Change Default Winter Temperature",
                                        JOptionPane.PLAIN_MESSAGE));
                        int defSummerTempZone = Integer.parseInt(JOptionPane
                                .showInputDialog(parent.getDashboard(), "Enter a default summer temperature.", "Change Default Summer Temperature",
                                        JOptionPane.PLAIN_MESSAGE));
                        defTempManipulable.addValue(defWinterTempZone);
                        defTempManipulable.addValue(defSummerTempZone);
                    } catch (NumberFormatException e) {
                        parent.sendToConsole("Please enter an integer.", Dashboard.MessageType.ERROR);
                        return;
                    }

                    performActionOn(Action.SET_DEFAULT_TEMPERATURE, defTempManipulable);
                    break;
            }
        } else {
            String message = "Please select a permission and location to choose an action.";
            if (parent.getHouse() == null) {
                message += " You must first load a house to select a location.";
            }
            parent.sendToConsole(message, Dashboard.MessageType.ERROR);
        }
    }

    private boolean canAct() {
        return !(parent.getParameters().getPermission() == null || parent.getParameters().getLocation() == null);
    }

}
