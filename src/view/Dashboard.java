package view;

import elements.House;
import permissions.Permission;

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
     * Pre-determined size parameters
     */
    private static final int WINDOW_WIDTH = 0x600;
    private static final int WINDOW_HEIGHT = 0x300;
    private static final int PARAMETER_PANE_WIDTH = WINDOW_WIDTH >>> 2;
    private static final int CONTENT_PANE_WIDTH = WINDOW_WIDTH - (WINDOW_WIDTH >>> 2);
    private static final int CONTENT_WIDTH = CONTENT_PANE_WIDTH >>> 1;
    private static final int CONSOLE_HEIGHT = WINDOW_HEIGHT / 3;
    private static final int CONTENT_HEIGHT = WINDOW_HEIGHT - CONSOLE_HEIGHT;
    private static final int CONTENT_PADDING = 0x20;
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm a");

    ParameterPanel parameters = new ParameterPanel();
    ParameterEditor editor = new ParameterEditor();
    JPanel content = new JPanel(); // TODO encapsulate actions and layout into one JPanel
    ActionPanel actions = new ActionPanel();
    HouseLayoutPanel layout = new HouseLayoutPanel(null);
    JTextArea console = new JTextArea("Welcome to Smart Home Simulator!");

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

        // Add elements to content panel.
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
     * @param permission the specified permission level
     * @throws NullPointerException if the specified {@code permission} is {@code null}
     */
    public void setPermission(String permission) {
        parameters.setPermission(permission);
    }

    /**
     * Sets the {@code location} level displayed to the user to that specified.
     *
     * @param location the specified permission level
     * @throws NullPointerException if the specified {@code location} is {@code null}
     */
    public void setLocation(String location) { // TODO rename this
        parameters.setLocation(location);
    }

    /**
     * Sets the {@code temperature} level displayed to the user to that specified.
     *
     * @param temperature the specified permission level
     * @throws NullPointerException if the specified {@code temperature} is {@code null}
     */
    public void setTemperature(String temperature) {
        parameters.setTemperature(temperature);
    }

    /**
     * Sets the {@code date} level displayed to the user to that specified.
     *
     * @param date the specified permission level
     * @throws NullPointerException if the specified {@code date} is {@code null}
     */
    public void setDate(Date date) {
        parameters.setDate(DATE_FORMAT.format(date));
    }

    /**
     * Retrieves the {@code Permission} level selected by the user.
     *
     * @return the {@code Permission} level the user has selected for themselves
     */
    public Permission getPermissionInput() {
        return (Permission)editor.permission.getSelectedItem();
    }

    /**
     * Retrieves the {@code location} selected by the user.
     *
     * @return the {@code location} the user has selected for themselves
     */
    public String getLocationInput() {
        return (String)editor.location.getSelectedItem();
    }

    /**
     * Retrieves the {@code temperature} selected by the user.
     *
     * @return the {@code temperature} the user has selected for the outside of their simulated {@code House}
     */
    public Integer getTemperatureInput() {
        return (Integer)editor.temperature.getValue();
    }

    /**
     * Retrieves the {@code Date} selected by the user.
     *
     * @return the {@code Date} the user has selected for the simulation
     */
    public Date getDateInput() {
        return (Date)editor.date.getValue();
    }

    /**
     * Registers an event handler for loading {@code House} layouts.
     *
     * @param listener the specified event handler
     */
    public void addLoadHouseListener(ActionListener listener) {
        editor.loadHouse.addActionListener(listener);
    }

    /**
     * Registers an event handler for editing simulation profiles.
     *
     * @param listener the specified event handler
     */
    public void addProfileEditListener(ActionListener listener) {
        editor.editProfiles.addActionListener(listener);
    }

    /**
     * Registers an event handler for setting a users' {@code Permission} level.
     *
     * @param listener the specified event handler
     */
    public void addPermissionListener(ActionListener listener) {
        editor.permission.addActionListener(listener);
    }

    /**
     * Registers an event handler for setting a users' location.
     *
     * @param listener the specified event handler
     */
    public void addLocationListener(ActionListener listener) {
        editor.location.addActionListener(listener);
    }

    /**
     * Registers an event handler for setting a simulations' temperature.
     *
     * @param listener the specified event handler
     */
    public void addTemperatureListener(ChangeListener listener) {
        editor.temperature.addChangeListener(listener);
    }

    /**
     * Registers an event handler for setting a simulations' {@code Date}.
     *
     * @param listener the specified event handler
     */
    public void addDateListener(ChangeListener listener) {
        editor.date.addChangeListener(listener);
    }

    /**
     * Registers an event handler for processing {@code Window}-specific {@code Action}s.
     *
     * @param listener the specified event handler
     */
    public void addWindowActionListener(MouseListener listener) {
        ActionPanel.WINDOW_ACTION_LISTENER = listener;
    }

    /**
     * Enables the UI element that allows users to select a location for themselves, and populates it with the specified
     * set of {@code locations}.
     *
     * @param locations the specified locations
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
     * @param house the specified house
     */
    public void drawHouse(House house) {
        layout.setHouse(house);
    }

}
