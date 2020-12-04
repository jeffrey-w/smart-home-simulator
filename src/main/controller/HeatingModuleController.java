package main.controller;

import main.model.elements.Room;
import main.view.Dashboard;
import main.view.ModuleView;


/**
 * The {@code HeatingModuleController} class provides services regarding delivering HVAC-related
 * (heating, ventilation, air conditioning) of the smart home functionality. This includes operations such as
 * creating temperature control zones (for heating and cooling), assigning rooms to those zones, and changing the
 * temperature of the rooms within a zone so that all temperatures of that room are uniform.
 *
 * @author Émilie Martin
 */
public class HeatingModuleController extends AbstractModuleController {

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
        if(canAct()) {
            // SMTH
        } else {
            parent.sendToConsole("Please select a permission to choose an action.", Dashboard.MessageType.ERROR);
        }
    }

    private boolean canAct() {
        return !(parent.getParameters().getPermission() == null || parent.getParameters().getLocation() == null);
    }

}
