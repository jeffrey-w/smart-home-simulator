package view;

import javax.swing.*;
import java.awt.*;

class ParameterPanel extends JPanel {

    Avatar avatar;
    JLabel permission = new JLabel();
    JLabel location = new JLabel();
    JLabel temperature = new JLabel();
    JLabel date = new JLabel();
    JLabel time = new JLabel();

    ParameterPanel() {
        setPreferredSize(new Dimension(256, 440));
        setLayout(new SpringLayout());
        add(new JToggleButton("On"));
        add(avatar = new Avatar(null));
        add(permission);
        add(location);
        add(temperature);
        add(date);
        add(time);
        SpringUtilities.makeCompactGrid(this, 7, 1, 1, 1, 1, 1);
        setPermission(""); // TODO remove these
        setLocation("");
    }

    // TODO null check strings

    void setPermission(String permission) {
        this.permission.setText("Permission: " + permission);
    }

    void setLocation(String location) { // TODO rename this
        this.location.setText("Location: " + location);
    }

    void setTemperature(String temperature) {
        this.temperature.setText("Temperature: " + temperature + " C");
    }

    void setDate(String date) {
        this.date.setText("Date: " + date);
    }

}
