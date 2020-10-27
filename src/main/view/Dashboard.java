package main.view;

import main.model.elements.House;
import main.model.parameters.permissions.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * The dashboard represents the user interface. It is through the dashboard that the user can interact with the
 * simulation.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class Dashboard extends JFrame {

    /**
     * A collection of default {@code Permission} levels.
     */
    public static final Permission[] PERMISSIONS = new Permission[] { // TODO rethink this
            new ParentPermission(),
            new ChildPermission(),
            new GuestPermission(),
            new StrangerPermission()
    };

    // Pre-determined size parameters
    static final int WINDOW_WIDTH = 0x600;
    static final int WINDOW_HEIGHT = 0x300;
    static final int PARAMETER_PANE_WIDTH = WINDOW_WIDTH >>> 2;
    static final int CONTENT_PANE_WIDTH = WINDOW_WIDTH - (WINDOW_WIDTH >>> 2); // x >>> y == x / 2^y
    static final int CONTENT_WIDTH = CONTENT_PANE_WIDTH >>> 1; // Computers like bitwise operators!
    static final int CONSOLE_HEIGHT = WINDOW_HEIGHT / 3;
    static final int CONTENT_HEIGHT = WINDOW_HEIGHT - CONSOLE_HEIGHT;
    static final int CONTENT_PADDING = 0x20;
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm a");

    ParameterPanel parameters = new ParameterPanel();
    ParameterEditor editor = new ParameterEditor();
    JPanel content = new JPanel(); // TODO encapsulate actions and layout into one JPanel
    ActionPanel actions = new ActionPanel();
    HouseLayoutPanel layout = new HouseLayoutPanel(null);
    JTextArea console = new JTextArea("Welcome to Smart Home Simulator!");
    ProfileViewer profileViewer = new ProfileViewer();

    /**
     * Creates the dashboard, which is to contain the {@code ParameterPanel}, a console, the {@code HouseLayout}. This
     * is also the interface on which the simulation will be displayed and interacted with.
     */
    public Dashboard() {
        // Set window title.
        super("Smart Home Simulator");

        // Top-level containers for window content.
        JTabbedPane parameterPane = new JTabbedPane();
        JTabbedPane contentPane = new JTabbedPane();

        // Set window display behavior.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(false);

        // Add top-level content containers to the window.
        add(parameterPane, BorderLayout.WEST);
        add(contentPane, BorderLayout.EAST);

        // Add tabs to parameter panel.
        parameterPane.addTab("Parameters", parameters);
        parameterPane.addTab("Edit", editor);
        parameterPane.setPreferredSize((new Dimension(PARAMETER_PANE_WIDTH, WINDOW_HEIGHT)));

        // Add tab to content panel.
        contentPane.addTab("Simulation", content);
        contentPane.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH, WINDOW_HEIGHT));

        // Add main.model.elements to content panel.
        content.setLayout(new BorderLayout());
        content.add(actions, BorderLayout.WEST);
        content.add(layout, BorderLayout.EAST);
        content.add(new JScrollPane(console), BorderLayout.SOUTH);

        // Set content display behavior.
        actions.setPreferredSize(new Dimension(CONTENT_WIDTH - CONTENT_PADDING, CONTENT_HEIGHT));
        actions.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        layout.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        layout.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Set console display behavior.
        console.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH, CONSOLE_HEIGHT));
        console.setEnabled(false);
    }

    /**
     * Sets the {@code permission} level displayed to the user to that specified.
     *
     * @param permission The specified permission level
     * @throws NullPointerException if the specified {@code permission} is {@code null}
     */
    public void setPermission(String permission) {
        parameters.setPermission(permission);
    }

    /**
     * Sets the {@code location} level displayed to the user to that specified.
     *
     * @param location The specified location
     * @throws NullPointerException if the specified {@code location} is {@code null}
     */
    public void setLocation(String location) { // TODO rename this
        parameters.setLocation(location);
    }

    /**
     * Sets the {@code temperature} level displayed to the user to that specified.
     *
     * @param temperature The specified temperature
     * @throws NullPointerException if the specified {@code temperature} is {@code null}
     */
    public void setTemperature(String temperature) {
        parameters.setTemperature(temperature);
    }

    /**
     * Sets the {@code date} level displayed to the user to that specified.
     *
     * @param date The specified date
     * @throws NullPointerException if the specified {@code date} is {@code null}
     */
    public void setDate(Date date) {
        parameters.setDate(DATE_FORMAT.format(date));
    }

    /**
     * Retrieves the {@code Permission} level selected by the user.
     *
     * @return The {@code Permission} level the user has selected for themselves
     */
    public Permission getPermissionInput() {
        return (Permission) editor.permission.getSelectedItem();
    }

    /**
     * Retrieves the {@code location} selected by the user.
     *
     * @return The {@code location} the user has selected for themselves
     */
    public String getLocationInput() {
        return (String) editor.location.getSelectedItem();
    }

    /**
     * Retrieves the {@code temperature} selected by the user.
     *
     * @return The {@code temperature} the user has selected for the outside of their simulated {@code House}
     */
    public Integer getTemperatureInput() {
        return (Integer) editor.temperature.getValue();
    }

    /**
     * Retrieves the {@code Date} selected by the user.
     *
     * @return The {@code Date} the user has selected for the simulation
     */
    public Date getDateInput() {
        return (Date) editor.date.getValue();
    }

    /**
     * Registers an event handler for loading {@code House} layouts.
     *
     * @param listener The specified event handler
     */
    public void addLoadHouseListener(ActionListener listener) {
        editor.loadHouse.addActionListener(listener);
    }

    /**
     * Registers an event handler for editing simulation profiles.
     *
     * @param listener The specified event handler
     */
    public void addManageProfilesListener(ActionListener listener) { // TODO rename this
        editor.editProfiles.addActionListener(listener);
    }

    public void addEditProfileListener(ActionListener listener) { // TODO rename this
        profileViewer.addEditProfileListener(listener);
    }

    /**
     * Registers an event handler for setting a users' {@code Permission} level.
     *
     * @param listener The specified event handler
     */
    public void addPermissionListener(ActionListener listener) {
        editor.permission.addActionListener(listener);
    }

    /**
     * Registers an event handler for setting a users' location.
     *
     * @param listener The specified event handler
     */
    public void addLocationListener(ActionListener listener) {
        editor.location.addActionListener(listener);
    }

    /**
     * Registers an event handler for setting a simulations' temperature.
     *
     * @param listener The specified event handler
     */
    public void addTemperatureListener(ChangeListener listener) {
        editor.temperature.addChangeListener(listener);
    }

    /**
     * Registers an event handler for setting a simulations' {@code Date}.
     *
     * @param listener The specified event handler
     */
    public void addDateListener(ChangeListener listener) {
        editor.date.addChangeListener(listener);
    }

    // TODO comment this
    public void addActionSelectionListener(MouseListener listener) {
        actions.actions.addMouseListener(listener);
    }

    /**
     * Enables the UI element that allows users to select a location for themselves, and populates it with the specified
     * set of {@code locations}.
     *
     * @param locations The specified locations
     */
    public void activateLocations(Set<String> locations) {
        for (String location : locations) {
            editor.location.addItem(location);
        }
        editor.location.setEnabled(true);
    }

    /**
     * Draws the specified {@code house} on this {@code Dashboard}.
     *
     * @param house The specified house
     */
    public void drawHouse(House house) {
        layout.setHouse(house);
    }

    /**
     * Writes the specified message to the console of this {@code Dashboard}.
     *
     * @param message The specified message
     */
    public void sendToConsole(String message) {
        console.setText(console.getText() + '\n' + message);
    }

    // TODO comment this
    public ProfileViewer getProfileViewer() {
        return profileViewer;
    }

    // TODO comment this
    public ActionPanel getActions() {
        return actions;
    }

}
