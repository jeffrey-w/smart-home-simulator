package view;

import elements.House;
import permissions.Permission;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * The dashboard represents the user interface. It is through the dashboard that the user can interact with the simulation.
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
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm a");

    ParameterPanel parameters = new ParameterPanel();
    ParameterEditor editor = new ParameterEditor();
    JPanel content = new JPanel(); // TODO encapsulate actions and layout into one JPanel
    JPanel actions = new JPanel();
    JPanel items = new JPanel();
    JPanel options = new JPanel();
    HouseLayoutPanel layout = new HouseLayoutPanel(null);
    JTextArea console = new JTextArea("Welcome to Smart Home Simulator!");

    /**
     * Creates the dashboard, which is to contain the {@code ParameterPanel}, a console, the {@code HouseLayeout}.
     * This is also the interface on which the simulation will be displayed and interacted with.
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
        actions.setLayout(new BorderLayout());
        actions.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        actions.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        actions.add(new JScrollPane(items), BorderLayout.NORTH);
        actions.add(new JScrollPane(options), BorderLayout.SOUTH);
        items.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT >>> 1));
        options.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT >>> 1));
        layout.setPreferredSize(new Dimension(CONTENT_WIDTH, CONTENT_HEIGHT));
        layout.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        // Set console display behavior.
        console.setPreferredSize(new Dimension(CONTENT_PANE_WIDTH, CONSOLE_HEIGHT));
        console.setEnabled(false);
    }

    public void setPermission(String permission) {
        parameters.setPermission(permission);
    }

    public void setLocation(String location) { // TODO rename this
        parameters.setLocation(location);
    }

    public void setTemperature(String temperature) {
        parameters.setTemperature(temperature);
    }

    public void setDate(Date date) {
        parameters.setDate(DATE_FORMAT.format(date));
    }

    public Permission getPermissionInput() {
        return (Permission)editor.permission.getSelectedItem();
    }

    public String getLocationInput() {
        return (String)editor.location.getSelectedItem();
    }

    public Integer getTemperatureInput() {
        return (Integer)editor.temperature.getValue();
    }

    public Date getDateInput() {
        return (Date)editor.date.getValue();
    }

    public void addLoadHouseListener(ActionListener listener) {
        editor.loadHouse.addActionListener(listener);
    }

    public void addProfileEditListener(ActionListener listener) {
        editor.editProfiles.addActionListener(listener);
    }

    public void addPermissionListener(ActionListener listener) {
        editor.permission.addActionListener(listener);
    }

    public void addLocationListener(ActionListener listener) {
        editor.location.addActionListener(listener);
    }

    public void addTemperatureListener(ChangeListener listener) {
        editor.temperature.addChangeListener(listener);
    }

    public void addDateListener(ChangeListener listener) {
        editor.date.addChangeListener(listener);
    }

    public void activateLocations(final Set<String> locations) {
        for (String location : locations) {
            editor.location.addItem(location);
        }
        editor.location.setEnabled(true);
    }

    public void drawHouse(House house) {
        layout.setHouse(house);
    }

}
