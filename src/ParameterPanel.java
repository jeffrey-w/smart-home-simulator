import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

class ParameterPanel extends JPanel {

    Avatar avatar;
    JLabel role = new JLabel();
    JLabel location = new JLabel();
    JLabel temperature_outdoors = new JLabel();
    JLabel date = new JLabel();
    JLabel time = new JLabel();

    ParameterPanel() {
        JPanel buttons = new JPanel();
        setPreferredSize(new Dimension(256, 512));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS)); // TODO need better layout
        buttons.add(new JToggleButton("On"));
        buttons.add(new JButton("Edit"));
        add(buttons);
        add(avatar = new Avatar(null));
        add(role);
        add(location);
        add(temperature_outdoors);
        add(date);
        add(time);
        setRole("Parent"); // TODO remove these
        setLocation("Kitchen");
        setOutdoorTemperature("Outside Temp 15°C");
        setDate("Fri Sep 18 2020");
        setTime("10:38:20");
    }

    void setRole(String role) {
        this.role.setText(role);
    }

    void setLocation(String location) {
        this.location.setText(location);
    }

    void setOutdoorTemperature(String temperature) {
        this.temperature_outdoors.setText(temperature);
    }

    void setDate(String date) {
        this.date.setText(date);
    }

    void setTime(String time) {
        this.time.setText(time);
    }

}
