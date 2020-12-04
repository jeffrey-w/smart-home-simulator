package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.MultiValueManipulable;
import main.model.ValueManipulable;
import main.view.Dashboard;
import main.view.ModuleView;

import javax.swing.*;

/**
 * The {@code HeatingModuleController} class provides services regarding delivering HVAC-related
 * (heating, ventilation, air conditioning) of the smart home functionality. This includes operations such as
 * creating temperature control zones (for heating and cooling), assigning rooms to those zones, and changing the
 * temperature of the rooms within a zone so that all temperatures of that room are uniform.
 *
 * @author Émilie Martin
 */
public class HeatingModuleController extends AbstractModuleController {

    private static final Manipulable EMPTY_MANIPULABLE = new ValueManipulable<>(new Object());

    /**
     * Constructs a new {@code HeatingModuleController} object with the specified {@code parent} and {@code view}.
     *
     * @param parent The {@code Controller} to which this {@code HeatingModuleController} is subordinate to
     * @param view The {@code ModuleView} that this {@code HeatingModuleController} controls
     * @throws NullPointerException If the specified {@code parent} or {@code view} is {@code null}
     */
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
