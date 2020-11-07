package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.elements.House;
import main.model.elements.Room;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import main.util.HouseReader;
import main.util.JSONFilter;
import main.view.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The {@code Controller} class provides the interface between runtime simulation objects and the UI elements to
 * manipulate those objects. It serves as the entry point into the program.
 *
 * @author Jeff Wilgus
 */
public class Controller {
    private static final JSONFilter JSON_FILTER = new JSONFilter();

    private static final Set<String> CANCEL_KEYWORDS = new HashSet<>();

    static {
        CANCEL_KEYWORDS.add("Y");
        CANCEL_KEYWORDS.add("YES");
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
                toggleAutoLight();
                redrawHouse();
                sendToConsole("Simulation has " + (parameters.isOn() ? "begun." : "ended."),
                        Dashboard.MessageType.NORMAL);
                if (parameters.isOn() && parameters.isAwayMode()) {
                    startAwayModeCountdown();
                }
            } else {
                sendToConsole("Please load a house to start the simulation.", Dashboard.MessageType.ERROR);
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
                                sendToConsole(name + " has entered the " + location + ".",
                                        Dashboard.MessageType.NORMAL);
                                toggleAutoLight();
                                redrawHouse();
                                if (parameters.isOn() && parameters.isAwayMode()) {
                                    startAwayModeCountdown();
                                }
                            } else {
                                if (house != null && house.removePerson(name)) {
                                    sendToConsole(name + " has exited the house.", Dashboard.MessageType.NORMAL);
                                }
                            }
                            if (!viewer.containsProfile(name)) {
                                viewer.addProfile(name);
                            }
                            editor.dispose();
                        } catch (Exception exception) {
                            sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR);
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
                        sendToConsole(viewer.getSelectedValue() + " has exited the house.",
                                Dashboard.MessageType.NORMAL);
                        toggleAutoLight();
                        redrawHouse();
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
                sendToConsole("You have entered the " + location + ".", Dashboard.MessageType.NORMAL);
                if (parameters.isOn() && parameters.isAwayMode()) {
                    startAwayModeCountdown();
                }
            } catch (NoSuchElementException exception) {
                sendToConsole("You have exited the house.", Dashboard.MessageType.NORMAL);
            } catch (Exception exception) {
                exception.printStackTrace();
                sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR);
            }
            toggleAutoLight();
            redrawHouse();
        }

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
                ActionPanel actionPanel = dashboard.getActions();
                if (actionPanel.getSelectedItem().equals("Away_Mode")) {
                    performActionOn(parameters.getAwayMode(), actionPanel.getSelectedAction());
                } else if (canAct()) {
                    ItemChooser chooser = ItemChooser.of(getItems(actionPanel.getSelectedItem()));
                    chooser.addActionListener(f -> {
                        performActionOn(chooser.getSelectedItem(), actionPanel.getSelectedAction());
                        redrawHouse();
                        chooser.dispose();
                    });
                    chooser.pack();
                    chooser.setLocationRelativeTo(dashboard);
                    chooser.setVisible(true);
                } else {
                    String message = "Please select a permission and location to choose an action.";
                    if (house == null) {
                        message += " You must first load a house to select a location.";
                    }
                    sendToConsole(message, Dashboard.MessageType.ERROR);
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

    private void performActionOn(Manipulable manipulable, Action action) {
        try {
            sendToConsole(manipulable.manipulate(parameters.getPermission().authorize(action), parameters, house),
                    Dashboard.MessageType.NORMAL);
        } catch (IllegalArgumentException e) {
            sendToConsole(e.getMessage(), Dashboard.MessageType.ERROR);
        }
    }

    private void toggleAutoLight() {
        if (parameters.isAutoLight()) {
            for (Room room : house) {
                room.toggleLights(room.isOccupied());
            }
        }
    }

    private void startAwayModeCountdown() {
        final boolean[] cancel = {false, false};
        Timer timer = new Timer(parameters.getAwayDelay(), e -> {
            dashboard.addConsoleListener(null, null);
            if (cancel[0]) {
                sendToConsole("Crisis averted!", Dashboard.MessageType.WARNING);
            } else {
                sendToConsole((cancel[1] ? "" : "\n") + "Intruder detected, the authorities have been alerted!",
                        Dashboard.MessageType.WARNING);
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

    private void sendToConsole(String message, Dashboard.MessageType type) {
        dashboard.sendToConsole(message, type, true);
    }

    private void redrawHouse() {
        for (String location : house.getLocations()) {
            dashboard.updateRoom(location, house.getRoom(location));
        }
        dashboard.redrawHouse();
    }

}
