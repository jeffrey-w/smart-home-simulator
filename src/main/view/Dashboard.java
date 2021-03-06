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
import java.time.LocalTime;
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

    public ModuleView addModule(Module module) {
        return actions.addModule(module);
    }

    public String getSelectedModule() {
        return actions.getTitleAt(actions.getSelectedIndex());
    }

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
    static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");

    // Action color-coded legend
    private static final EnumMap<MessageType, Color> MESSAGE_COLORS = new EnumMap<>(MessageType.class);
    static {
        MESSAGE_COLORS.put(MessageType.WARNING, Color.MAGENTA);
        MESSAGE_COLORS.put(MessageType.ERROR, Color.RED);
    }

    private int inputLoc;

    // Dashboard panels
    final ParameterPanel parameters = new ParameterPanel();
    final ParameterEditor editor = new ParameterEditor();
    final ActionPanel actions = new ActionPanel();
    final HouseLayoutPanel layout = new HouseLayoutPanel();
    final JTextPane console = new JTextPane();
    final ParameterViewer profileViewer = new ParameterViewer("Edit Profiles");

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
     * Sets the external {@code temperature} (outside the simulated house) level displayed to the user to that specified.
     *
     * @param temperature The specified temperature
     * @throws NullPointerException If the specified {@code temperature} is {@code null}
     */
    public void setExternalTemperature(String temperature) {
        parameters.setExternalTemperature(temperature);
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
        parameters.setTime(time[0] + ":" + time[1] + ":" + time[2]);
    }

    /**
     * @return The {@code Permission} level the user has selected for themselves
     */
    public Permission getPermissionInput() {
        return (Permission) editor.permissions.getSelectedItem();
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
    public Double getTemperatureInput() {
        return (Double) editor.temperature.getValue();
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

    public int[] getTimeInput() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime((Date) editor.time.getValue());
        LocalTime time = LocalTime.of(
            calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND)
        );
        return new int[] {
            time.getHour(),
            time.getMinute(),
            time.getSecond()
        };
    }

    /**
     * @return The {@code ProfileViewer} for this {@code Dashboard}
     */
    public ParameterViewer getProfileViewer() {
        return profileViewer;
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
        profileViewer.addActionListener(listener);
    }

    /**
     * Registers an event handler for loading and saving custom {@code Permission} settings.
     *
     * @param listener The specified event handler
     */
    public void addPersistPermissionListener(ActionListener listener) {
        editor.persistPermissions.addActionListener(listener);
    }

    /**
     * Registers an event handler for setting a users' {@code Permission} level.
     *
     * @param listener The specified event handler
     */
    public void addPermissionListener(ActionListener listener) {
        editor.permissions.addActionListener(listener);
    }

    /**
     * Registers an event handler for editing a the permissible {@code Action}s for each {@code Permission}.
     *
     * @param editPermissionsListener The specified event handler
     */
    public void addEditPermissionListener(ActionListener editPermissionsListener) {
        editor.editPermissions.addActionListener(editPermissionsListener);
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
     * Registers an event handler for setting a simulation's temperature.
     *
     * @param listener The specified event handler
     */
    public void addTemperatureListener(ChangeListener listener) {
        editor.temperature.addChangeListener(listener);
    }

    /**
     * Registers an event handler for setting a simulation's {@code Date}.
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
        editor.time.addChangeListener(listener);
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
     * Provides the specified {@code permissions} to various UI elements.
     *
     * @param permissions The specified {@code Permission}s
     */
    public void addPermissions(Collection<Permission> permissions) {
        editor.permissions.addItem(null); // Add default option
        for (Permission permission : permissions) {
            editor.permissions.addItem(permission);
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
        editor.location.addItem(House.EXTERIOR_NAME);
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
        return StyleContext.getDefaultStyleContext().addAttribute(
            SimpleAttributeSet.EMPTY,
            StyleConstants.Foreground,
            MESSAGE_COLORS.getOrDefault(type, original)
        );
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
     * Switches the function of the Save/Load Permissions button between the save and load functionality.
     */
    public void togglePermissionButton() {
        switch (editor.persistPermissions.getText()) {
            case "Save Permissions":
                editor.persistPermissions.setText("Load Permissions");
                editor.persistPermissions.setActionCommand("Load Permissions");
                break;
            case "Load Permissions":
                editor.persistPermissions.setText("Save Permissions");
                editor.persistPermissions.setActionCommand("Save Permissions");
                break;
            default:
                throw new AssertionError();
        }
    }

    /**
     * @return {@code true} if this {@code Dashboard} allows users to load {@code Permission}s
     */
    public boolean canLoadPermissions() {
        return editor.persistPermissions.getText().equals("Load Permissions");
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
