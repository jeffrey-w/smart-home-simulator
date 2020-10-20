package main.controller;

import main.model.elements.House;
import main.model.elements.Window;
import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import main.util.HouseReader;
import main.util.JSONFilter;
import main.view.Dashboard;
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
import java.util.NoSuchElementException;

/**
 * The {@code Controller} class provides the interface between runtime simulation objects and the UI main.model.elements
 * to manipulate those objects. It serves as the entry point into the program.
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

    private static House house;
    private static final Parameters parameters = new Parameters();

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
        dashboard.addPermissionListener(new PermissionListener());
        dashboard.addTemperatureListener(new TemperatureListener());
        dashboard.addDateListener(new DateListener());
        dashboard.addWindowActionListener(new WindowActionListener());
    }

    /**
     * Adds a new profile to the simulation {@code Parameters}.
     *
     * @param name The name of the added profile
     * @param permission The {@code Permission} level of the added profile
     * @throws IllegalArgumentException if the specified {@code role} is not a non-empty string of word
     *         characters (i.e. [a-z, A-Z, 0-9, _])
     * @throws NullPointerException if the specified {@code permission} is {@code null}
     */
    public static void addProfile(String name, Permission permission) {
        parameters.addActor(name, permission); // TODO exception handling
    }

    /**
     * Places a person in a specific {@code Place} in the simulated {@code House}.
     *
     * @param name The name of the added person
     * @param permission The {@code Permission} level of the added person
     * @param location The place in the {@code House} the added person is inserted into
     * @throws IllegalArgumentException if the specified {@code name} is not a non-empty string of word
     *         characters (i.e. [a-z, A-Z, 0-9, _])
     * @throws NoSuchElementException if the specified {@code location} does not exist in this {@code House}
     * @throws NullPointerException if the specified {@code permission} is {@code null}
     */
    public static void placePerson(String name, Permission permission, String location) {
        house.addPerson(name, permission, location);
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
        public void actionPerformed(final ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                ProfileViewer viewer = new ProfileViewer(parameters, house);
                viewer.pack();
                viewer.setLocationRelativeTo(dashboard);
                viewer.setVisible(true);
            });
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
