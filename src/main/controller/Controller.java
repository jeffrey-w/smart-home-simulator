package main.controller;

import main.model.elements.House;
import main.model.elements.Manipulable;
import main.model.elements.Room;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import main.util.HouseReader;
import main.util.JSONFilter;
import main.view.*;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.*;

/**
 * The {@code Controller} class provides the interface between runtime simulation objects and the UI elements to
 * manipulate those objects. It serves as the entry point into the program.
 *
 * @author Jeff Wilgus
 */
public class Controller {

    private static final JSONFilter JSON_FILTER = new JSONFilter();
    private static final Set<String> CANCEL_KEYWORDS = new HashSet<>();
    private static final Map<String, ActionListener> SUBROUTINES = new HashMap<>();

    static {
        CANCEL_KEYWORDS.add("YES");
        CANCEL_KEYWORDS.add("Y");
    }

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
    private final Dashboard dashboard = new Dashboard();

    /**
     * Constructs a new {@code Controller} object.
     */
    public Controller() {
        dashboard.setTemperature(String.valueOf(parameters.getTemperature()));
        dashboard.setDate(parameters.getDate());
        dashboard.addSimulationListener(new SimulationListener());
        dashboard.addLoadHouseListener(new LoadHouseListener());
        dashboard.addEditProfilesListener(new ManageProfilesListener());
        dashboard.addManageProfilesListener(new EditProfileListener());
        dashboard.addPermissionListener(new PermissionListener());
        dashboard.addTemperatureListener(new TemperatureListener());
        dashboard.addDateListener(new DateListener());
        dashboard.addActionSelectionListener(new ActionSelectionListener());
        dashboard.drawHouse(house);
    }

    /*
     * Below are various event handlers that transform input from the user into data that can be manipulated by the data
     * model of a simulation
     */

    class SimulationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (house != null) {
                parameters.setOn(((JToggleButton) e.getSource()).isSelected());
                dashboard.toggleOnButton();
                dashboard.showStates(parameters.isOn());
                dashboard.redrawHouse();
            } else {
                dashboard.sendToConsole("Please load a house to start the simulation.", Dashboard.MessageType.ERROR, true);
            }
        }

    }

    class LoadHouseListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(JSON_FILTER);
            if (chooser.showOpenDialog(dashboard) == JFileChooser.APPROVE_OPTION) {
                HouseReader reader = new HouseReader(chooser.getSelectedFile());
                house = reader.readHouse();
                dashboard.addLocationListener(null);
                dashboard.activateLocations(house.getLocations());
                dashboard.addLocationListener(new LocationListener());
                dashboard.drawHouse(house);
            }
        }

    }

    class ManageProfilesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ProfileViewer viewer = dashboard.getProfileViewer();
            viewer.clear();
            viewer.populateList(parameters.getActors());
            viewer.pack();
            viewer.setLocationRelativeTo(dashboard);
            viewer.setVisible(true);
        }

    }

    class EditProfileListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String actionCommand = e.getActionCommand();
            ProfileViewer viewer = dashboard.getProfileViewer();
            switch (actionCommand) {
                case "Add":
                case "Edit": {
                    ProfileEditor editor = new ProfileEditor(viewer.getSelectedValue(), house != null);
                    editor.setPermission(parameters.permissionOf(editor.getRole()));
                    if (house != null) {
                        editor.addLocations(house.getLocations());
                        if (editor.getRole() != null && house.contains(editor.getRole())) {
                            editor.selectLocation(house.locationOf(editor.getRole()));
                        }
                    }
                    editor.addActionListener(f -> {
                        String name = editor.getRole();
                        Permission permission = editor.getSelectedPermission();
                        String location = editor.getSelectedLocation();
                        try {
                            parameters.addActor(name, permission);
                            if (location != null) { // Assume that house is non-null since location field is enabled.
                                house.addPerson(name, permission, location);
                                dashboard.sendToConsole(name + " entered " + location + ".",
                                        Dashboard.MessageType.NORMAL, true);
                                if (parameters.isOn()) {
                                    dashboard.updateRoom(location, house.getRoom(location));
                                    if (parameters.getAwayMode()) {
                                        startAwayModeCountdown();
                                    }
                                }
                                updateHouseIfOn(location);
                            } else {
                                if (house != null && house.removePerson(name)) {
                                    dashboard.sendToConsole(name + " exited the house.", Dashboard.MessageType.NORMAL, true);
                                }
                            }
                            if (!viewer.containsProfile(name)) {
                                viewer.addProfile(name);
                            }
                            editor.dispose();
                        } catch (Exception exception) {
                            dashboard.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR, true);
                        }
                    });
                    editor.pack();
                    editor.setLocationRelativeTo(viewer);
                    editor.setVisible(true);
                    break;
                }
                case "Remove": {
                    parameters.removeActor(viewer.getSelectedValue());
                    if (house != null) {
                        house.removePerson(viewer.getSelectedValue());
                        updateHouseIfOn(house.locationOf(viewer.getSelectedValue()));
                    }
                    viewer.removeProfile(viewer.getSelectedValue());
                    break;
                }
                default:
                    throw new AssertionError(); // Defensive measure; this should never happen.
            }
        }

    }

    class PermissionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            Permission permission = dashboard.getPermissionInput();
            String location = parameters.getLocation();
            parameters.setPermission(permission);
            dashboard.setPermission(permission.toString());
            if (house != null && location != null) {
                try {
                    house.addPerson("user", permission, location);
                } catch (Exception exception) {
                    parameters.setLocation(null);
                    house.removePerson("user");
                    dashboard.setLocation((String) null);
                }
            }
        }

    }

    class LocationListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String location = dashboard.getLocationInput();
            parameters.setLocation(location);
            dashboard.setLocation(location);
            try {
                house.addPerson("user", parameters.getPermission(), location);
                dashboard.sendToConsole("You have entered " + location + ".", Dashboard.MessageType.NORMAL, true);
            } catch (NoSuchElementException exception) {
                dashboard.sendToConsole("You have removed yourself from the house.", Dashboard.MessageType.NORMAL, true);
            } catch (Exception exception) {
                dashboard.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR, true);
            }
            updateHouseIfOn(location);
        }

    }

    private void updateHouseIfOn(String location) {
        if (parameters.isOn()) {
            dashboard.updateRoom(location, house.getRoom(location));
            if (parameters.getAwayMode()) {
                startAwayModeCountdown();
            }
        }
        dashboard.redrawHouse();
    }

    class TemperatureListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            int temperature = dashboard.getTemperatureInput();
            parameters.setTemperature(temperature);
            dashboard.setTemperature(String.valueOf(temperature));
        }

    }

    class DateListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            Date date = dashboard.getDateInput();
            parameters.setDate(date);
            dashboard.setDate(date);
        }

    }

    class ActionSelectionListener extends MouseAdapter {

        @Override
        public void mouseClicked(final MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {
                if (canAct()) {
                    ActionPanel actionPanel = dashboard.getActions();
                    if (SUBROUTINES.containsKey(actionPanel.getSelectedItem())) {
                        SUBROUTINES.get(actionPanel.getSelectedItem())
                                .actionPerformed(
                                        new ActionEvent(e.getSource(), e.getID(), actionPanel.getSelectedItem()));
                    } else {
                        ItemChooser chooser = ItemChooser.of(getItems(actionPanel.getSelectedItem()));
                        chooser.addActionListener(f -> {
                            try {
                                dashboard.sendToConsole(chooser.getSelectedItem().manipulate(
                                        parameters.getPermission().authorize(actionPanel.getSelectedAction())),
                                        Dashboard.MessageType.NORMAL, true);
                                updateHouseIfOn(parameters.getLocation());
                            } catch (IllegalArgumentException exception) {
                                dashboard.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR, true);
                            }
                            chooser.dispose();
                        });
                        chooser.pack();
                        chooser.setLocationRelativeTo(dashboard);
                        chooser.setVisible(true);
                    }
                } else {
                    String message = "Please select a permission and location to choose an action.";
                    if (house == null) {
                        message += " You must first load a house to select a location.";
                    }
                    dashboard.sendToConsole(message, Dashboard.MessageType.ERROR, true);
                }
            }
        }
    }

    private boolean canAct() {
        return !(parameters.getPermission() == null || parameters.getLocation() == null);
    }

    private Manipulable[] getItems(String type) {
        Room location = house.getRoom(parameters.getLocation());
        switch (type) {
            case "Doors":
                return location.getDoors();
            case "Lights":
                return location.getLights();
            case "Windows":
                return location.getWindows();
            default:
                throw new AssertionError(); // Defensive measure; this should never happen.
        }
    }

    private void startAwayModeCountdown() {
        final boolean[] cancel = {false, false};
        Timer timer = new Timer(parameters.getAwayDelay(), e -> {
            dashboard.addConsoleListener(null, null);
            if (cancel[0]) {
                dashboard.sendToConsole("Crisis averted!", Dashboard.MessageType.WARNING, true);
            } else {
                dashboard.sendToConsole(
                        (cancel[1] ? "" : "\n") + "Intruder detected, the authorities have been alerted!",
                        Dashboard.MessageType.WARNING, true);
            }
        });
        dashboard.addConsoleListener(new KeyAdapter() {

            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (CANCEL_KEYWORDS.contains(dashboard.getLastConsoleMessage())) {
                        cancel[0] = true;
                        timer.setInitialDelay(0);
                        timer.restart();
                    }
                    cancel[1] = true;
                }
            }

        }, "Potential break in, do you want to disable the alarm [y/N]?");
        timer.setRepeats(false);
        timer.start();
    }

}
