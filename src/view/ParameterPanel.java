package view;

import view.viewtils.SpringUtilities;

import javax.swing.*;
import java.util.Objects;

/**
 * The ParameterPanel displays all simulation parameters to the user.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class ParameterPanel extends JPanel {

    private static final int ROWS = 6;

    Avatar avatar;
    JLabel permission = new JLabel();
    JLabel location = new JLabel();
    JLabel temperature = new JLabel();
    JLabel date = new JLabel();

    /**
     * Constructs a parameter panel with an avatar and editable parameters
     */
    public ParameterPanel() {
        // Set panel display behavior.
        setLayout(new SpringLayout());

        // Add elements to panel.
        add(new JToggleButton("On"));
        add(avatar = new Avatar(null));
        add(permission);
        add(location);
        add(temperature);
        add(date);

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
     * @throws NullPointerException if the specified {@code permission} is {@code null}
     */
    void setPermission(String permission) {
        this.permission.setText("Permission: " + Objects.requireNonNull(permission));
    }

    /**
     * Setter to modify the location of a user
     *
     * @param location The user's new position
     * @throws NullPointerException if the specified {@code location} is {@code null}
     */
    void setLocation(String location) { // TODO rename this
        this.location.setText("Location: " + Objects.requireNonNull(location));
    }

    /**
     * Setter that allows the user to change the temperature
     *
     * @param temperature The new temperature
     * @throws NullPointerException if the specified {@code temperature} is {@code null}
     */
    void setTemperature(String temperature) {
        this.temperature.setText("Temperature: " + Objects.requireNonNull(temperature) + " C");
    }

    /**
     * Setter that allows the user to change the simulation date
     *
     * @param date The new date
     * @throws NullPointerException if the specified {@code date} is {@code null}
     */
    void setDate(String date) {
        this.date.setText("Date: " + Objects.requireNonNull(date));
    }

}
