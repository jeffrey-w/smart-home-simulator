package view;

import view.viewtils.SpringUtilities;

import javax.swing.*;
import java.util.Objects;

class ParameterPanel extends JPanel {

    private static final int COLUMNS = 7;

    Avatar avatar;
    JLabel permission = new JLabel();
    JLabel location = new JLabel();
    JLabel temperature = new JLabel();
    JLabel date = new JLabel();
    JLabel time = new JLabel();

    ParameterPanel() {
        // Set panel display behavior.
        setLayout(new SpringLayout());
        // Add elements to panel.
        add(new JToggleButton("On"));
        add(avatar = new Avatar(null));
        add(permission);
        add(location);
        add(temperature);
        add(date);
        add(time);
        // Set element display behavior.
        SpringUtilities.makeCompactGrid(this, COLUMNS, 1, 1, 1, 1, 1);
        // Set default values for empty parameters.
        setPermission("");
        setLocation("");
    }

    void setPermission(String permission) {
        this.permission.setText("Permission: " + Objects.requireNonNull(permission));
    }

    void setLocation(String location) { // TODO rename this
        this.location.setText("Location: " + Objects.requireNonNull(location));
    }

    void setTemperature(String temperature) {
        this.temperature.setText("Temperature: " + Objects.requireNonNull(temperature) + " C");
    }

    void setDate(String date) {
        this.date.setText("Date: " + Objects.requireNonNull(date));
    }

}
