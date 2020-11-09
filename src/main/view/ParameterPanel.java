package main.view;

import main.view.viewtils.SpringUtilities;

import javax.swing.*;
import java.util.Objects;

/**
 * The ParameterPanel displays all simulation parameters to the user.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class ParameterPanel extends JPanel {

    private static final int ROWS = 7;

    Avatar avatar;
    JLabel permission = new JLabel();
    JLabel location = new JLabel();
    JLabel temperature = new JLabel();
    JLabel date = new JLabel();
    JLabel time = new JLabel();

    /**
     * Constructs a parameter panel with an avatar and editable main.model.parameters
     */
    public ParameterPanel() {
        // Set panel display behavior.
        setLayout(new SpringLayout());

        // Add main.model.elements to panel.
        add(new JToggleButton("On"));
        add(avatar = new Avatar(null));
        add(permission);
        add(location);
        add(temperature);
        add(date);
        add(time);

        // Set element display behavior.
        SpringUtilities.makeCompactGrid(this, ROWS, 1, 1, 1, 1, 1);

        // Set default values for empty parameters.
        setPermission("");
        setLocation("");
    }

    /**
     * Setter to modify the permission of a user
     *
     * @param permission The new assigned user permission
     */
    void setPermission(String permission) {
        this.permission.setText("Permission: " + ((permission == null) ? "" : permission));
    }

    /**
     * Setter to modify the location of a user
     *
     * @param location The user's new position
     */
    void setLocation(String location) {
        this.location.setText("Location: " + ((location == null) ? "" : location));
    }

    /**
     * Setter that allows the user to change the temperature
     *
     * @param temperature The new temperature
     * @throws NullPointerException If the specified {@code temperature} is {@code null}
     */
    void setTemperature(String temperature) {
        this.temperature.setText("Temperature: " + Objects.requireNonNull(temperature) + " C");
    }

    /**
     * Setter that allows the user to change the simulation date
     *
     * @param date The new date
     * @throws NullPointerException If the specified {@code date} is {@code null}
     */
    void setDate(String date) {
        this.date.setText("Date: " + Objects.requireNonNull(date));
    }

    /**
     * Setter that allows the user to change the simulation date
     *
     * @param time The new time
     * @throws NullPointerException If the specified {@code date} is {@code null}
     */
    void setTime(String time) {
        this.time.setText("Time: " + Objects.requireNonNull(time));
    }
}
