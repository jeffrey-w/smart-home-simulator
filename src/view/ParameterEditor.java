package view;

import parameters.Parameters;
import permissions.*;

import javax.swing.*;
import java.awt.*;
import java.time.*;
import java.util.Calendar;
import java.util.Date;

public class ParameterEditor extends JPanel {

    private static final SpinnerNumberModel TEMP_MODEL = new SpinnerNumberModel(Parameters.DEFAULT_TEMPERATURE, -100, 100, 1); // TODO refine these numbers
    private static final SpinnerDateModel DATE_MODEL =
            new SpinnerDateModel(Date.from(Instant.now()), null, null, Calendar.DAY_OF_YEAR);
    static Permission[] PERMISSIONS = new Permission[] {
            new ParentPermission(),
            new ChildPermission(),
            new GuestPermission(),
            new StrangerPermission()
    };

    private static JLabel labelFactory(String text) { // TODO rename this
        JLabel label = new JLabel(text, SwingConstants.RIGHT);
        label.setFont(label.getFont().deriveFont(11f));
        return label;
    }

    JButton loadHouse = new JButton("Load House");
    JButton editProfiles = new JButton("Edit Profiles");
    JComboBox<Permission> permission = new JComboBox<>();
    JComboBox<String> location = new JComboBox<>();
    JSpinner temperature = new JSpinner(TEMP_MODEL);
    JSpinner date = new JSpinner(DATE_MODEL);

    ParameterEditor() {
        JPanel buttonPanel = new JPanel(new SpringLayout());
        JPanel fields = new JPanel(new SpringLayout());
        setLayout(new BorderLayout());
        buttonPanel.add(loadHouse);
        buttonPanel.add(editProfiles);
        SpringUtilities.makeGrid(buttonPanel, 1, 2, 8, 8, 8, 8);
        add(buttonPanel, BorderLayout.NORTH);
        fields.add(labelFactory("Permission:"));
        fields.add(permission);
        fields.add(labelFactory("Location:"));
        fields.add(location);
        fields.add(labelFactory("Temperature:"));
        fields.add(temperature);
        fields.add(labelFactory("Date"));
        fields.add(date);
        SpringUtilities.makeCompactGrid(fields, 4, 2, 1, 1, 1, 86);
        add(fields);
        addPermissions();
        location.setEnabled(false);
    }

    private void addPermissions() {
        for (Permission permission : PERMISSIONS) {
            this.permission.addItem(permission);
        }
    }

}
