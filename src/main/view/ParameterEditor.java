package main.view;

import main.model.parameters.Parameters;
import main.model.parameters.permissions.*;
import main.view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@code ParameterEditor} class provides the UI elements for a user to set the global settings of a simulation
 * (such as the external temperature and the date) and their own {@code Permission} level and location.
 *
 * @author Jeff Wilgus
 */
public class ParameterEditor extends JPanel {

    private static final int BUTTON_COLUMNS = 2;
    private static final int BUTTON_OFFSET = 0x20;
    private static final int BUTTON_X_PADDING = 0x20;
    private static final int BUTTON_Y_PADDING = 0x20;

    private static final int PERMISSION_ROWS = 2;
    private static final int PERMISSION_COLUMNS = 2;
    private static final int PERMISSION_X_PADDING = 0x15;
    private static final int PERMISSION_Y_PADDING = 0x15;

    private static final int FIELD_ROWS = 3;
    private static final int FIELD_COLUMNS = 2;
    private static final int FIELD_Y_PADDING = 0x60;

    private static final Permission[] PERMISSIONS = new Permission[] {
            null,
            new ParentPermission(),
            new ChildPermission(),
            new GuestPermission(),
            new StrangerPermission()
    };
    private static final SpinnerNumberModel TEMP_MODEL =
            new SpinnerNumberModel(Parameters.DEFAULT_TEMPERATURE, Parameters.MIN_TEMPERATURE,
                    Parameters.MAX_TEMPERATURE, 1);
    private static final SpinnerDateModel DATE_MODEL =
            new SpinnerDateModel(Date.from(Instant.now()), null, null, Calendar.DAY_OF_YEAR);

    static JComboBox<Permission> permissionJComboBox() {
        return new JComboBox<>(PERMISSIONS);
    }

    static JLabel labelFactory(String text) {
        return new JLabel(text + ":", SwingConstants.RIGHT);
    }

    JButton loadHouse = new JButton("Load House");
    JButton manageProfiles = new JButton("Manage Profiles");
    JComboBox<Permission> permission = permissionJComboBox();
    JComboBox<String> location = new JComboBox<>();
    JSpinner temperature = new JSpinner(TEMP_MODEL);
    JSpinner date = new JSpinner(DATE_MODEL);

    PermissionEditor editor = new PermissionEditor();

    /**
     * Constructs a new {@code ParameterEditor} object.
     */
    ParameterEditor() {
        // Containers for buttons and fields respectively.
        JPanel buttons = new JPanel(new SpringLayout());
        JPanel permissions = new JPanel(new SpringLayout());
        JPanel fields = new JPanel(new SpringLayout());

        JButton editPermissions = new JButton("Edit permissions");
        editPermissions.addActionListener(new EditPermissionsListener());

        // Set panel display behavior.
        setLayout(new BorderLayout());

        // Add containers to panel.
        add(buttons, BorderLayout.NORTH);
        add(permissions);
        add(fields, BorderLayout.SOUTH);

        // Add buttons to button panel.
        buttons.add(loadHouse);
        buttons.add(manageProfiles);

        // Set button panel display behavior
        SpringUtilities.makeGrid(
                buttons, 1, BUTTON_COLUMNS,
                BUTTON_OFFSET, BUTTON_OFFSET, BUTTON_X_PADDING, BUTTON_Y_PADDING
        );

        // Add fields to permission panel.
        permissions.add(labelFactory("Permission"));
        permissions.add(permission);
        permissions.add(labelFactory("Edit"));
        permissions.add(editPermissions);

        // Set permissions panel display behavior
        SpringUtilities.makeGrid(
                permissions, PERMISSION_ROWS, PERMISSION_COLUMNS,
                BUTTON_OFFSET, BUTTON_OFFSET, PERMISSION_X_PADDING,PERMISSION_Y_PADDING
        );

        // Add fields to field panel.
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

    class EditPermissionsListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            editor.display();
        }

    }

}
