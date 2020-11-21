package main.controller;

import main.model.Module;
import main.model.elements.House;
import main.model.parameters.Parameters;
import main.view.Dashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code Controller} class provides the interface between runtime simulation objects and the UI elements to
 * manipulate those objects. It serves as the entry point into the program.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class Controller {

    // This is the entry point into the program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Controller controller = new Controller();
            controller.dashboard.pack();
            controller.dashboard.setVisible(true);
            controller.dashboard.setLocationRelativeTo(null);
        });
    }

    private House house;
    private final Parameters parameters = new Parameters();
    private final Map<String, ModuleController> modules = new HashMap<>();
    private final Dashboard dashboard = new Dashboard();

    /**
     * Constructs a new {@code Controller} object.
     */
    public Controller() {
        ParameterController parameterController = new ParameterController(this);
        addModules();
        startClockDisplayUpdate();
        dashboard.setTemperature(String.valueOf(parameters.getTemperature()));
        dashboard.setDate(parameters.getDate());
        dashboard.addSimulationListener(new SimulationListener());
        dashboard.addLoadHouseListener(parameterController.new LoadHouseListener());
        dashboard.addEditProfilesListener(parameterController.new ManageProfilesListener());
        dashboard.addManageProfilesListener(parameterController.new EditProfileListener());
        dashboard.addPersistPermissionListener(parameterController.new PersistPermissionListener());
        dashboard.addPermissionListener(parameterController.new PermissionListener());
        dashboard.addEditPermissionListener(parameterController.new EditPermissionsListener());
        dashboard.addTemperatureListener(parameterController.new TemperatureListener());
        dashboard.addDateListener(parameterController.new DateListener());
        dashboard.addTimeXListener(parameterController.new TimeXListener());
        dashboard.addTimeUpdateListener(parameterController.new TimeUpdateListener());
        dashboard.addActionSelectionListener(new ActionSelectionListener());
        dashboard.drawHouse(house);
    }

    private void addModules() {
        modules.put(Module.SHC.getName(), new CoreModuleController(this, dashboard.addModule(Module.SHC)));
        modules.put(Module.SHP.getName(), new SecurityModuleController(this, dashboard.addModule(Module.SHP)));
    }

    private void startClockDisplayUpdate() {
        ActionListener updateClockListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dashboard.setTime(parameters.getClockTime());
            }
        };

        javax.swing.Timer timerClock = new javax.swing.Timer(1000, updateClockListener);
        timerClock.start();
    }

    /*
     * Utility methods for subordinate controllers.
     */

    Parameters getParameters() {
        return parameters;
    }

    House getHouse() {
        return house;
    }

    void setHouse(House house) {
        this.house = house; // TODO null check?
    }

    Dashboard getDashboard() {
        return dashboard;
    }

    CoreModuleController getCoreModuleController() {
        return ((CoreModuleController) modules.get(Module.SHC.getName()));
    }

    SecurityModuleController getSecurityModuleController() {
        return ((SecurityModuleController) modules.get(Module.SHP.getName()));
    }

    void sendToConsole(String message, Dashboard.MessageType type) {
        dashboard.sendToConsole(message, type, true);
    }

    void redrawHouse() {
        for (String location : house.getLocations()) {
            dashboard.updateRoom(location, house.getRoom(location));
        }
        dashboard.redrawHouse();
    }

    /*
     * Event handlers.
     */
    class SimulationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (house != null) {
                parameters.setOn(((JToggleButton) e.getSource()).isSelected());
                dashboard.toggleOnButton();
                dashboard.showStates(parameters.isOn());
                getCoreModuleController().toggleAutoLight();
                redrawHouse();
                sendToConsole("Simulation has " + (parameters.isOn() ? "begun." : "ended."),
                        Dashboard.MessageType.NORMAL);
                if (parameters.isOn() && parameters.isAwayMode() && house.isOccupied()) {
                    getSecurityModuleController().startAwayModeCountdown();
                }
            } else {
                sendToConsole("Please load a house to start the simulation.", Dashboard.MessageType.ERROR);
                ((JToggleButton) e.getSource()).setSelected(false);
            }
        }

    }

    class ActionSelectionListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {
                modules.get(dashboard.getSelectedModule()).control();
            }
        }

    }

}
