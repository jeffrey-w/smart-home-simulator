package main.view;

import main.model.elements.House;
import main.model.parameters.permissions.Permission;

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

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;

import org.tinylog.Logger;

/**
 * The dashboard represents the user interface. It is through the dashboard that the user can interact with the
 * simulation.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class Dashboard extends JFrame {

    // Pre-determined size parameters
    static final int WINDOW_WIDTH = 0x600;
    static final int WINDOW_HEIGHT = 0x300;
    static final int PARAMETER_PANE_WIDTH = WINDOW_WIDTH >>> 2; // x >>> y == x / 2^y
    static final int CONTENT_PANE_WIDTH = WINDOW_WIDTH - PARAMETER_PANE_WIDTH;
    static final int CONTENT_WIDTH = CONTENT_PANE_WIDTH >>> 1; // Computers like bitwise operators!
    static final int CONSOLE_WIDTH = CONTENT_WIDTH / 0x10;
    static final int CONSOLE_HEIGHT = (WINDOW_HEIGHT / 3) / 0x10;
    static final int CONTENT_HEIGHT = WINDOW_HEIGHT - CONSOLE_HEIGHT;
    static final int CONTENT_PADDING = 0x20;
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd"); // NOTE: removed the time here so that we can implement that independently

    ParameterPanel parameters = new ParameterPanel();
    ParameterEditor editor = new ParameterEditor();
    ActionPanel actions = new ActionPanel();
    HouseLayoutPanel layout = new HouseLayoutPanel();
    JTextArea console = new JTextArea("Welcome to Smart Home Simulator!\n\n> ", CONSOLE_HEIGHT, CONSOLE_WIDTH);
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
        JPanel content = new JPanel();

        // Set window display behavior.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        setResizable(false);

        // Add top-level content containers to the window.
        add(parameterPane, WEST);
        add(contentPane, EAST);

        // Add tabs to parameter panel.
        parameterPane.addTab("Parameters", parameters);
        parameterPane.addTab("Edit", editor);
        parameterPane.setPreferredSize((new Dimension(PARAMETER_PANE_WIDTH, WINDOW_HEIGHT)));

        // Add tab to content panel.
        contentPane.addTab("Simulation", content);
        contentPane.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH, WINDOW_HEIGHT));

        // Add elements to content panel.
        content.setLayout(new BorderLayout());
        content.add(actions, WEST);
        content.add(layout, EAST);
        content.add(new JScrollPane(console), BorderLayout.SOUTH);

        // Set content display behavior.
        actions.setPreferredSize(new Dimension(CONTENT_WIDTH - CONTENT_PADDING, CONTENT_HEIGHT));
        actions.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        layout.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        layout.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Set console display behavior.
        console.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        console.setEditable(false);
        console.setCaretPosition(console.getDocument().getLength());
        console.getCaret().setVisible(true);
    }

    /**
     * Sets the {@code permission} level displayed to the user to that specified.
     *
     * @param permission The specified permission level
     */
    public void setPermission(String permission) {
        parameters.setPermission(permission);
    }

    /**
     * Sets the {@code location} level displayed to the user to that specified.
     *
     * @param location The specified location
     */
    public void setLocation(String location) {
        parameters.setLocation(location);
    }

    /**
     * Sets the {@code temperature} level displayed to the user to that specified.
     *
     * @param temperature The specified temperature
     * @throws NullPointerException If the specified {@code temperature} is {@code null}
     */
    public void setTemperature(String temperature) {
        parameters.setTemperature(temperature);
    }

    /**
     * Sets the {@code date} level displayed to the user to that specified.
     *
     * @param date The specified date
     * @throws NullPointerException If the specified {@code date} is {@code null}
     */
    public void setDate(Date date) {
        parameters.setDate(DATE_FORMAT.format(date));
    }

    /**
     * Sets the {@code time}
     *
     * @param time The specified time
     * @throws NullPointerException If the specified {@code time} is {@code null}
     */
    public void setTime(int[] time) {
        String h = Integer.toString(time[0]);
        String m = Integer.toString(time[1]);
        String s = Integer.toString(time[2]);
        String timeStr = ("" + h + ":" + m + ":" + s);

        parameters.setTime(timeStr);
    }

    /**
     * @return The {@code Permission} level the user has selected for themselves
     */
    public Permission getPermissionInput() {
        return (Permission) editor.permission.getSelectedItem();
    }

    /**
     * @return The location the user has selected for themselves
     */
    public String getLocationInput() {
        return (String) editor.location.getSelectedItem();
    }

    /**
     * @return The temperature the user has selected for the outside of their simulated {@code House}
     */
    public Integer getTemperatureInput() {
        return (Integer) editor.temperature.getValue();
    }

    /**
     * @return The {@code Date} the user has selected for the simulation
     */
    public Date getDateInput() {
        return (Date) editor.date.getValue();
    }

    /**
     * @return The time speed multiplier the user has set for the simulation {@code House}
     */
    public Integer getTimeXInput() {
        return (Integer) editor.timeX.getValue();
    }

    /**
     * @return The time hour the user has set for the simulation {@code House}
     */
    public Integer getHourInput() {
        return (Integer) editor.hour.getValue();
    }

    /**
     * @return The time min the user has set for the simulation {@code House}
     */
    public Integer getMinInput() {
        return (Integer) editor.min.getValue();
    }

    /**
     * @return The time sec the user has set for the simulation {@code House}
     */
    public Integer getSecInput() {
        return (Integer) editor.sec.getValue();
    }

    /**
     * @return The {@code ProfileViewer} for this {@code Dashboard}
     */
    public ProfileViewer getProfileViewer() {
        return profileViewer;
    }

    /**
     * @return The {@code ActionPanel} for this {@code Dashboard}
     */
    public ActionPanel getActions() {
        return actions;
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
    public void addEditProfilesListener(ActionListener listener) {
        editor.manageProfiles.addActionListener(listener);
    }

    /**
     * Registers an event handler for managing simulation profiles.
     *
     * @param listener The specified event handler
     */
    public void addManageProfilesListener(ActionListener listener) {
        profileViewer.addManageProfileListener(listener);
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

    /**
     * Registers an event handler for setting a simulations' {@code Clock}.
     *
     * @param listener The specified event handler
     */
    public void addTimeXListener(ChangeListener listener) {
        editor.timeX.addChangeListener(listener);
    }

    /**
     * Registers an event handler for setting a simulations' {@code Clock}.
     *
     * @param listener The specified event handler
     */
    public void addTimeUpdateListener(ChangeListener listener) {
        editor.hour.addChangeListener(listener);
        editor.min.addChangeListener(listener);
        editor.sec.addChangeListener(listener);
    }

    /**
     * Registers an event handler for selecting an {@code Action} to perform on a {@code Manipulable}
     *
     * @param listener the specified event handler
     */
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
        editor.location.addItem(null);
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
        layout.drawHouse(house);
    }

    /**
     * Writes the specified {@code message} to the console of this {@code Dashboard}.
     *
     * @param message The specified message
     */
    public void sendToConsole(String message) {
        console.append(message + "\n> ");
        console.setCaretPosition(console.getDocument().getLength());

        // Send message to log file as well
        Logger.info(message);
    }

}
