package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.ValueManipulable;
import main.model.elements.Room;
import main.model.elements.Yard;
import main.model.parameters.Parameters;
import main.view.Dashboard;
import main.view.ItemChooser;
import main.view.ModuleView;

/**
 * The {@code CoreModuleController} class provides services for delivering core smart home functionality. This includes
 * operations such as opening and closing {@code Door}s, {@code Light}s, and {@code Window}s, and toggling auto light
 * mode.
 *
 * @author Jeff Wilgus
 */
public class CoreModuleController extends AbstractModuleController {

    /**
     * Constructs a new {@code CoreModuleController} object with the specified {@code parent} and {@code view}.
     *
     * @param parent The {@code Controller} to which this {@code CoreModuleController} is subordinate to
     * @param view The {@code ModuleView} that this {@code CoreModuleController} controls
     * @throws NullPointerException If the specified {@code parent} or {@code view} is {@code null}
     */
    public CoreModuleController(Controller parent, ModuleView view) {
        super(parent, view);
    }

    @Override
    public void control() {
        if (canAct()) {
            Parameters parameters = parent.getParameters();
            Action action = view.getSelectedAction();
            if (action.equals(Action.TOGGLE_AUTO_LIGHT)) {
                performActionOn(action, new ValueManipulable<>(!parameters.isAutoLight()));
                toggleAutoLight();
            } else {
                if (action.equals(Action.TOGGLE_BLOCK_WINDOW)) {
                    parent.setMonitorWindows(true);
                }

                ItemChooser chooser = ItemChooser.of(getItemsForSelection());
                chooser.addActionListener(f -> {
                    performActionOn(action, chooser.getSelectedItem());
                    chooser.dispose();
                });

                chooser.pack();
                chooser.setLocationRelativeTo(parent.getDashboard());
                chooser.setVisible(true);
            }
            parent.redrawHouse();
        } else {
            String message = "Please select a permission and location to choose an action.";
            if (parent.getHouse() == null) {
                message += " You must first load a house to select a location.";
            }
            parent.sendToConsole(message, Dashboard.MessageType.ERROR);
        }
    }

    /**
     * Iterates over the {@code Room}s in the {@code House} controlled by this {@code CoreModuleController}, turning on
     * and off the {@code Light}s as per occupancy in each.
     */
    public void toggleAutoLight() {
        if (parent.getParameters().isAutoLight()) {
            for (Room room : parent.getHouse()) {
                room.toggleLights(room.isOccupied());
            }
            if (Yard.getInstance().isOccupied()) {
                Yard.getInstance().setLightOn(true);
            }
        }
    }

    private boolean canAct() {
        return !(parent.getParameters().getPermission() == null || parent.getParameters().getLocation() == null);
    }

    private Manipulable[] getItemsForSelection() {
        Room room = parent.getHouse().getRoom(parent.getParameters().getLocation());
        switch (view.getSelectedItem()) {
            case "Doors":
                return room.getDoors();
            case "Lights":
                return room.getLights();
            case "Windows":
                return room.getWindows();
            default:
                throw new AssertionError(); // Defensive measure; this should never happen.
        }
    }

}
