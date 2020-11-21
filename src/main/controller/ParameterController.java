package main.controller;

import main.model.Action;
import main.model.elements.House;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import main.util.HouseReader;
import main.util.JSONFilter;
import main.util.PermissionManager;
import main.util.TextFilter;
import main.view.Dashboard;
import main.view.PermissionEditor;
import main.view.ProfileEditor;
import main.view.ProfileViewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ParameterController {

    private static final JSONFilter JSON_FILTER = new JSONFilter();
    private static final TextFilter TEXT_FILTER = new TextFilter();

    private final Controller parent;

    public ParameterController(Controller parent) {
        this.parent = Objects.requireNonNull(parent);
    }

    class LoadHouseListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            Dashboard dashboard = parent.getDashboard();
            House house;
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(JSON_FILTER);
            if (chooser.showOpenDialog(dashboard) == JFileChooser.APPROVE_OPTION) {
                HouseReader reader = new HouseReader(chooser.getSelectedFile());
                parent.setHouse(house = reader.readHouse());
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
            Dashboard dashboard = parent.getDashboard();
            ProfileViewer viewer = dashboard.getProfileViewer();
            viewer.clear();
            viewer.populateList(parent.getParameters().getActorsIdentifier());
            viewer.pack();
            viewer.setLocationRelativeTo(dashboard);
            viewer.setVisible(true);
        }

    }

    class EditProfileListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String actionCommand = e.getActionCommand();
            House house = parent.getHouse();
            Parameters parameters = parent.getParameters();
            ProfileViewer viewer = parent.getDashboard().getProfileViewer();
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
                                parent.sendToConsole(name + " has entered the " + location + ".",
                                        Dashboard.MessageType.NORMAL);
                                parent.getCoreModuleController().toggleAutoLight();
                                parent.redrawHouse();
                                if (parameters.isOn() && parameters.isAwayMode()) {
                                    parent.getSecurityModuleController().startAwayModeCountdown();
                                }
                            } else {
                                if (house != null && house.removePerson(name)) {
                                    parent.sendToConsole(name + " has exited the house.", Dashboard.MessageType.NORMAL);
                                }
                            }
                            if (!viewer.containsProfile(name)) {
                                viewer.addProfile(name);
                            }
                            editor.dispose();
                        } catch (Exception exception) {
                            parent.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR);
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
                        parent.sendToConsole(viewer.getSelectedValue() + " has exited the house.",
                                Dashboard.MessageType.NORMAL);
                        parent.getCoreModuleController().toggleAutoLight();
                        parent.redrawHouse();
                    }
                    viewer.removeProfile(viewer.getSelectedValue());
                    break;
                }
                default:
                    throw new AssertionError(); // Defensive measure; this should never happen.
            }
        }

    }

    class PersistPermissionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String actionCommand = e.getActionCommand();
            Parameters parameters = parent.getParameters();
            Dashboard dashboard = parent.getDashboard();
            switch (actionCommand) {
                case "Load Permissions": {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileFilter(TEXT_FILTER);
                    if (chooser.showOpenDialog(dashboard) == JFileChooser.APPROVE_OPTION) {
                        try {
                            parameters.setPermissions(PermissionManager.loadPermissions(chooser.getSelectedFile()));
                            //TODO update profile list
                        } catch (Exception exception) {
                            parent.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR);
                        }
                    }
                    break;
                }
                case "Save Permissions": {

                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Specify a file to save");

                    // Disable the all files option
                    chooser.setAcceptAllFileFilterUsed(false);
                    chooser.setCurrentDirectory(new java.io.File("."));

                    if (chooser.showSaveDialog(dashboard) == JFileChooser.APPROVE_OPTION) {
                        try {
                            //TODO warn about overwriting
                            PermissionManager.savePermissions(parameters.getPermissions(), chooser.getSelectedFile());
                        } catch (Exception exception) {
                            parent.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR);
                        }
                    }
                    dashboard.togglePermissionButton();
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
            House house = parent.getHouse();
            Parameters parameters = parent.getParameters();
            Dashboard dashboard = parent.getDashboard();
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

    class EditPermissionsListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            Parameters parameters = parent.getParameters();
            Dashboard dashboard = parent.getDashboard();
            PermissionEditor editor = dashboard.getPermissionEditor();
            editor.addTableModelListener(f -> {
                DefaultTableModel table = (DefaultTableModel) f.getSource();
                for (int i = 0; i < table.getRowCount(); i++) {
                    main.model.Action action = (Action) table.getValueAt(i, 0);

                    for (int j = 1; j < table.getColumnCount(); j++) {
                        Boolean value = (Boolean) table.getValueAt(i, j);

                        if (value) { // If checkbox is selected
                            parameters.getPermissionOf(table.getColumnName(j)).addPermission(action);
                        } else {
                            parameters.getPermissionOf(table.getColumnName(j)).removePermission(action);
                        }
                    }
                }
                if (dashboard.canLoadPermissions()) {
                    dashboard.togglePermissionButton();
                }
            });

            editor.setLocationRelativeTo(dashboard);
            editor.pack();
            editor.setVisible(true);
        }

    }

    class LocationListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            Parameters parameters = parent.getParameters();
            Dashboard dashboard = parent.getDashboard();
            String location = dashboard.getLocationInput();
            parameters.setLocation(location);
            dashboard.setLocation(location);
            try {
                parent.getHouse().addPerson("user", parameters.getPermission(), location);
                parent.sendToConsole("You have entered the " + location + ".", Dashboard.MessageType.NORMAL);
                if (parameters.isOn() && parameters.isAwayMode()) {
                    parent.getSecurityModuleController().startAwayModeCountdown();
                }
            } catch (NoSuchElementException exception) {
                parent.sendToConsole("You have exited the house.", Dashboard.MessageType.NORMAL);
            } catch (Exception exception) {
                exception.printStackTrace();
                parent.sendToConsole(exception.getMessage(), Dashboard.MessageType.ERROR);
            }
            parent.getCoreModuleController().toggleAutoLight();
            parent.redrawHouse();
        }

    }

    class TemperatureListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            Dashboard dashboard = parent.getDashboard();
            int temperature = dashboard.getTemperatureInput();
            parent.getParameters().setTemperature(temperature);
            dashboard.setTemperature(String.valueOf(temperature));
        }

    }

    class DateListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            Dashboard dashboard = parent.getDashboard();
            Date date = dashboard.getDateInput();
            parent.getParameters().setDate(date);
            dashboard.setDate(date);
        }

    }

    class TimeXListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            parent.getParameters().setClockTimeMultiplier(parent.getDashboard().getTimeXInput());
        }

    }

    class TimeUpdateListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            Dashboard dashboard = parent.getDashboard();
            int h = dashboard.getHourInput();
            int m = dashboard.getMinInput();
            int s = dashboard.getSecInput();
            int[] time = new int[] {h, m, s};

            parent.getParameters().setTime(time);
        }

    }

}
