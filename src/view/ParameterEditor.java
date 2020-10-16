package view;

import parameters.Parameters;
import permissions.AbstractPermission;
import permissions.Permission;
import view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class ParameterEditor extends JPanel {

    private static final int BUTTON_COLUMNS = 2;
    private static final int BUTTON_OFFSET = 0x20;
    private static final int BUTTON_X_PADDING = 0x4A;
    private static final int BUTTON_Y_PADDING = 0x20;
    private static final int FIELD_ROWS = 4;
    private static final int FIELD_COLUMNS = 2;
    private static final int FIELD_Y_PADDING = 0x80;
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

    static JLabel labelFactory(String text) {
        return new JLabel(text + ":", SwingConstants.RIGHT);
    }

    JButton loadHouse = new JButton("Load House");
    JButton editProfiles = new JButton("Edit Profiles");
    JComboBox<Permission> permission = permissionJComboBox();
    JComboBox<String> location = new JComboBox<>();
    JSpinner temperature = new JSpinner(TEMP_MODEL);
    JSpinner date = new JSpinner(DATE_MODEL);

    ParameterEditor() {
        // Containers for buttons and fields respectively.
        JPanel buttons = new JPanel(new SpringLayout());
        JPanel fields = new JPanel(new SpringLayout());
        // Set panel display behavior.
        setLayout(new BorderLayout());
        // Add containers to panel.
        add(buttons, BorderLayout.NORTH);
        add(fields, BorderLayout.SOUTH);
        // Add buttons to button panel.
        buttons.add(loadHouse);
        buttons.add(editProfiles);
        // Set button panel display behavior
        SpringUtilities
                .makeGrid(buttons, 1, BUTTON_COLUMNS, BUTTON_OFFSET, BUTTON_OFFSET, BUTTON_X_PADDING, BUTTON_Y_PADDING);
        // Add fields to field panel.
        fields.add(labelFactory("Permission"));
        fields.add(permission);
        fields.add(labelFactory("Location"));
        fields.add(location);
        fields.add(labelFactory("Temperature"));
        fields.add(temperature);
        fields.add(labelFactory("Date"));
        fields.add(date);
        // Set field panel display behavior
        SpringUtilities.makeCompactGrid(fields, FIELD_ROWS, FIELD_COLUMNS, 1, 1, 1, FIELD_Y_PADDING);
        // Selecting a location is disabled by default.
        location.setEnabled(false);
    }

}
