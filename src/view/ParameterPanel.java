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
        JPanel buttons = new JPanel();
        setPreferredSize(new Dimension(256, 440));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); // TODO need better layout
        buttons.add(new JToggleButton("On"));
        add(buttons);
        add(avatar = new Avatar(null));
        add(permission);
        add(location);
        add(temperature);
        add(date);
        add(time);
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
