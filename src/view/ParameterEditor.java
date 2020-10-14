package view;

import parameters.Parameters;
import permissions.AbstractPermission;
import permissions.Permission;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class ParameterEditor extends JPanel {

    private static JComboBox<Permission> PERMISSION_J_COMBO_BOX;
    private static final SpinnerNumberModel TEMP_MODEL =
            new SpinnerNumberModel(Parameters.DEFAULT_TEMPERATURE, Parameters.MIN_TEMPERATURE,
                    Parameters.MAX_TEMPERATURE, 1);
    private static final SpinnerDateModel DATE_MODEL =
            new SpinnerDateModel(Date.from(Instant.now()), null, null, Calendar.DAY_OF_YEAR);

    static JComboBox<Permission> permissionJComboBox() {
        if (PERMISSION_J_COMBO_BOX == null) {
            PERMISSION_J_COMBO_BOX = new JComboBox<>();
            for (Permission permission : AbstractPermission.PERMISSIONS) {
                PERMISSION_J_COMBO_BOX.addItem(permission);
            }
        }
        return PERMISSION_J_COMBO_BOX;
    }

    static JLabel labelFactory(String text) { // TODO rename this
        JLabel label = new JLabel(text + ":", SwingConstants.RIGHT);
        label.setFont(label.getFont().deriveFont(11f));
        return label;
    }

    JButton loadHouse = new JButton("Load House");
    JButton editProfiles = new JButton("Edit Profiles");
    JComboBox<Permission> permission = permissionJComboBox();
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
        fields.add(labelFactory("Permission"));
        fields.add(permission);
        fields.add(labelFactory("Location"));
        fields.add(location);
        fields.add(labelFactory("Temperature"));
        fields.add(temperature);
        fields.add(labelFactory("Date"));
        fields.add(date);
        SpringUtilities.makeCompactGrid(fields, 4, 2, 1, 1, 1, 86);
        add(fields);
        location.setEnabled(false);
    }

}
