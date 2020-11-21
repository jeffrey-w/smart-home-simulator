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

public class CoreModuleController extends AbstractModuleController {

    public CoreModuleController(Controller parent, ModuleView view) {
        super(parent, view);
    }

    @Override
    public void control() {
        if (canAct()) {
            Parameters parameters = parent.getParameters();
            Action action = view.getSelectedAction();
            if (action.equals(Action.TOGGLE_AUTO_LIGHT)) {
                performCommand(new ValueManipulable<>(!parameters.isAutoLight()), action);
                toggleAutoLight();
            } else {
                ItemChooser chooser = ItemChooser.of(getItemsForSelection());
                chooser.addActionListener(f -> {
                    performCommand(chooser.getSelectedItem(), action);
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

    @Override
    public boolean canAct() {
        return !(parent.getParameters().getPermission() == null || parent.getParameters().getLocation() == null);
    }

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
