package main.controller;

import main.model.elements.House;
import main.model.elements.Window;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import main.util.HouseReader;
import main.util.JSONFilter;
import main.view.Dashboard;
import main.view.ProfileEditor;
import main.view.ProfileViewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

/**
 * The {@code Controller} class provides the interface between runtime simulation objects and the UI elements to
 * manipulate those objects. It serves as the entry point into the program.
 *
 * @author Jeff Wilgus
 */
public class Controller {

    private static final JSONFilter JSON_FILTER = new JSONFilter();

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
    private final Dashboard dashboard;

    /**
     * Constructs a new {@code Controller} object.
     */
    public Controller() {
        dashboard = new Dashboard();
        dashboard.setTemperature(String.valueOf(parameters.getTemperature()));
        dashboard.setDate(parameters.getDate());
        dashboard.addLoadHouseListener(new LoadHouseListener());
        dashboard.addProfileEditListener(new ProfileEditListener());
        dashboard.addEditProfileListener(new EditProfileListener());
        dashboard.addPermissionListener(new PermissionListener());
        dashboard.addTemperatureListener(new TemperatureListener());
        dashboard.addDateListener(new DateListener());
        dashboard.addWindowActionListener(new WindowActionListener());
    }

    /*
     * Below are various event handlers that transform input from the user into data that can be manipulated by the data
     * model of a simulation
     */

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

    class ProfileEditListener implements ActionListener {

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

    class EditProfileListener implements ActionListener { // TODO rename this

        @Override
        public void actionPerformed(final ActionEvent e) {
            String actionCommand = e.getActionCommand();
            ProfileViewer viewer = dashboard.getProfileViewer();
            switch (actionCommand) {
                case "Add":
                case "Edit": {
                    ProfileEditor editor = new ProfileEditor(viewer.getSelectedValue(), house != null);
                    if (house != null) {
                        editor.addLocations(house.getLocations());
                    }
                    editor.pack();
                    editor.setLocationRelativeTo(viewer);
                    editor.setVisible(true);
                    editor.addActionListener(f -> {
                        // Extract input from user
                        String name = editor.getRole();
                        Permission permission = editor.getSelectedPermission();
                        String location = editor.getSelectionLocation();

                        // TODO validate input

                        // Add profile
                        parameters.addActor(name, permission);

                        // Place person in location
                        if (location != null) {
                            house.addPerson(name, permission, location);
                        }

                        // Add in the ui
                        if (!viewer.containsProfile(name)) {
                            viewer.addProfile(name);
                        }
                        editor.dispose();
                    });
                    break;
                }
                case "Remove": {
                    parameters.removeActor(viewer.getSelectedValue());
                    if (house != null) {
                        house.removePerson(viewer.getSelectedValue());
                    }
                    viewer.removeProfile(viewer.getSelectedValue());
                    break;
                }
                default:
                    throw new AssertionError();
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
                house.addPerson("user", permission, location);
            }
        }

    }

    class LocationListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            // TODO require that permission be set
            String location = dashboard.getLocationInput();
            parameters.setLocation(location);
            dashboard.setLocation(location);
            house.addPerson("user", parameters.getPermission(), location);
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

    public class WindowActionListener extends MouseAdapter {

        @Override
        public void mouseClicked(final MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                SwingUtilities.invokeLater(() -> {
                    WindowViewer viewer = new WindowViewer(house.getWindowsOf(parameters.getLocation()));
                    viewer.pack();
                    viewer.setLocationRelativeTo(dashboard);
                    viewer.setVisible(true);
                });
            }
        }

    }

    class WindowViewer extends JFrame {

        JList<Window> list;

        WindowViewer(Window[] windows) {
            super("Obstruct Windows");
            list = new JList<>(windows);
            JButton ok = new JButton("Ok");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(0x100, 0x100)); // TODO avoid magic constants
            setResizable(false);
            add(list);
            add(ok, BorderLayout.SOUTH);
            ok.addActionListener(e -> {
                Window window = list.getSelectedValue();
                house.getRoom(parameters.getLocation())
                        .setObstructed(window.getWall().toInt(), true); // TODO parameterize obstructed
                dashboard.sendToConsole("Blocked " + window.getWall() + " window in " + parameters.getLocation() + ".");
                dispose();
            });
        }

    }

}
