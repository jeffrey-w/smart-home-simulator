package main.view;

import main.model.Action;
import main.model.Module;
import main.model.elements.House;
import main.model.elements.Room;
import main.model.parameters.permissions.Permission;
import org.tinylog.Logger;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.WEST;

/**
 * The dashboard represents the user interface. It is through the dashboard that the user can interact with the
 * simulation.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class Dashboard extends JFrame {

    /**
     * The category a console message might have.
     */
    public enum MessageType {
        NORMAL,
        WARNING,
        ERROR
    }

    // Pre-determined size parameters
    static final int WINDOW_WIDTH = 0x600;
    static final int WINDOW_HEIGHT = 0x300;
    static final int PARAMETER_PANE_WIDTH = WINDOW_WIDTH >>> 2; // x >>> y == x / 2^y
    static final int CONTENT_PANE_WIDTH = WINDOW_WIDTH - PARAMETER_PANE_WIDTH;
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm a");
    private static final EnumMap<MessageType, Color> MESSAGE_COLORS = new EnumMap<>(MessageType.class);

    static {
        MESSAGE_COLORS.put(MessageType.WARNING, Color.YELLOW);
        MESSAGE_COLORS.put(MessageType.ERROR, Color.RED);
    }

    ParameterPanel parameters = new ParameterPanel();

    private int inputLoc;
    ParameterEditor editor = new ParameterEditor();
    ActionPanel actions = new ActionPanel();
    HouseLayoutPanel layout = new HouseLayoutPanel();
    JTextPane console = new JTextPane();
    ProfileViewer profileViewer = new ProfileViewer();

    /**
     * Creates the dashboard, which is to contain the {@code ParameterPanel}, a console, the {@code HouseLayout}. This
     * is also the interface on which the simulation will be displayed and interacted with.
     */
    public Dashboard() {
        // Set window title.
        super("Smart Home Simulator");

        // Top-level containers for window content.
        JPanel content = new JPanel(new GridLayout(2, 1));
        JPanel top = new JPanel(new GridLayout(1, 2));
        JTabbedPane parameterPane = new JTabbedPane();
        JTabbedPane contentPane = new JTabbedPane();

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
        top.add(actions);
        top.add(layout);
        content.add(top);
        content.add(new JScrollPane(console));

        // Set content display behavior.
        actions.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        actions.addModule(Module.SHC);
        actions.addModule(Module.SHP);
        layout.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Set console display behavior.
        console.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        console.setEditable(false);
        console.setCaretPosition(console.getDocument().getLength());
        console.getCaret().setVisible(true);
        sendToConsole("Welcome to Smart Home Simulator!\n", MessageType.NORMAL, true);
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
     * Registers an event handler for turning the simulation on and off.
     *
     * @param listener The specified event handler
     */
    public void addSimulationListener(ActionListener listener) {
        parameters.on.addActionListener(listener);
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
     * Registers an event handler for selecting an {@code Action} to perform on a {@code Manipulable}
     *
     * @param listener the specified event handler
     */
    public void addActionSelectionListener(MouseListener listener) {
        for (JList<Action> actionList : actions.actions) {
            actionList.addMouseListener(listener);
        }
    }

    /**
     * Registers an event handler for user input to this {@code Dashboard}'s console.
     *
     * @param listener The specified handler
     * @param prompt A message to write to the console that describes the expected user input
     * @throws NullPointerException If the specified {@code prompt} is {@code null}
     */
    public void addConsoleListener(KeyListener listener, String prompt) {
        if (listener == null) {
            for (KeyListener l : console.getKeyListeners()) {
                console.removeKeyListener(l);
            }
            console.setEditable(false);
        } else {
            sendToConsole(Objects.requireNonNull(prompt) + " ", MessageType.WARNING, false);
            console.addKeyListener(listener);
            console.setEditable(true);
            console.grabFocus();
            inputLoc = console.getDocument().getLength();
        }
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
    public void sendToConsole(String message, MessageType type, boolean newLine) {
        int len = console.getDocument().getLength();
        Color orig = console.getForeground();
        console.setEditable(true);
        console.setCaretPosition(len);
        console.setCharacterAttributes(attributesOf(type, orig), false);
        console.replaceSelection(message);
        console.setCharacterAttributes(attributesOf(MessageType.NORMAL, orig), false);
        if (newLine) {
            console.replaceSelection("\n> ");
        }
        Logger.info(message);
    }

    private static AttributeSet attributesOf(MessageType type, Color original) {
        return StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground,
                MESSAGE_COLORS.getOrDefault(type, original));
    }

    /**
     * Provides the last message written to this {@code Dashboard}'s console.
     *
     * @return The last input to this {@code Dashboard}'s console
     */
    public String getLastConsoleMessage() {
        String message;
        message = console.getText();
        message = message.substring(inputLoc);
        return message.toUpperCase();
    }

    /**
     * Updates the {@code Room} at the specified {@code location} on this {@code Dashboard} with the status of the
     * specified {@code room}.
     *
     * @param location The specified location
     * @param room The specified {@code Room}
     * @throws NoSuchElementException If the specified {@code location} does not exist on this {@code Dashboard}
     * @throws NullPointerException If the specified {@code room} is {@code null}
     */
    public void updateRoom(String location, Room room) {
        layout.updateRoom(location, room);
    }

    /**
     * Updates the text of this {@code Dashboard}'s on/off button depending on whether or not a simulation is currently
     * running.
     */
    public void toggleOnButton() {
        switch (parameters.on.getText()) {
            case "On":
                parameters.on.setText("Off");
                break;
            case "Off":
                parameters.on.setText("On");
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * Induces the {@code HouseLayoutPanel} to redraw itself.
     */
    public void redrawHouse() {
        layout.revalidate();
        layout.repaint();
    }

    /**
     * Induces the {@code HouseLayoutPanel} to render {@code Room} states or not depending on the specified {@code
     * flag}.
     *
     * @param flag If {@code true}, {@code Room} states shall be rendered on this {@code Dashboard}'s {@code
     * HouseLayoutPanel}
     */
    public void showStates(boolean flag) {
        layout.showStates = flag;
    }

}
