package main.view;

import main.model.parameters.Parameters;
import main.model.parameters.permissions.Permission;
import main.view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
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

    private static final int BUTTON_ROWS = 2;
    private static final int BUTTON_COLUMNS = 2;
    private static final int BUTTON_OFFSET = 0x10;
    private static final int BUTTON_X_PADDING = 0x40;
    private static final int BUTTON_Y_PADDING = 0x20;
    private static final int FIELD_ROWS = 6;
    private static final int FIELD_COLUMNS = 2;
    private static final int FIELD_Y_PADDING = 0x40;

    private static final SpinnerNumberModel TEMP_MODEL =
            new SpinnerNumberModel(Parameters.DEFAULT_TEMPERATURE, Parameters.MIN_TEMPERATURE,
                    Parameters.MAX_TEMPERATURE, 1);
    private static final SpinnerDateModel DATE_MODEL =
            new SpinnerDateModel(Date.from(Instant.now()), null, null, Calendar.DAY_OF_YEAR);
    private static final SpinnerNumberModel TIMEX_MODEL =
            new SpinnerNumberModel(Parameters.DEFAULT_TIMEX, Parameters.MIN_TIMEX,
                    Parameters.MAX_TIMEX, 1);

    static JLabel labelFactory(String text) {
        return new JLabel(text + ":", SwingConstants.RIGHT);
    }

    static SpinnerNumberModel tempModel() {
        return new SpinnerNumberModel(Parameters.DEFAULT_TEMPERATURE, Parameters.MIN_TEMPERATURE,
                Parameters.MAX_TEMPERATURE, 1);
    }

    final JButton loadHouse = new JButton("Load House");
    final JButton manageProfiles = new JButton("Manage Profiles");
    final JButton editPermissions = new JButton("Edit Permissions");
    final JButton persistPermissions = new JButton("Load Permissions");
    final JComboBox<Permission> permissions = new JComboBox<>();
    final JComboBox<String> location = new JComboBox<>();
    final JSpinner temperature = new JSpinner(TEMP_MODEL);
    final JSpinner date = new JSpinner(DATE_MODEL);
    final JSpinner time = new JSpinner(new SpinnerDateModel());
    final JSpinner timeX = new JSpinner(TIMEX_MODEL);

    /**
     * Constructs a new {@code ParameterEditor} object.
     */
    ParameterEditor() {
        // Containers for buttons and fields respectively.
        JPanel buttons = new JPanel(new SpringLayout());
        JPanel fields = new JPanel(new SpringLayout());

        // Set panel display behavior.
        setLayout(new BorderLayout());

        // Add containers to panel.
        add(buttons, BorderLayout.NORTH);
        add(fields);

        // Add buttons to button panel.
        buttons.add(loadHouse);
        buttons.add(manageProfiles);
        buttons.add(editPermissions);
        buttons.add(persistPermissions);

        // Set button panel display behavior
        SpringUtilities
                .makeGrid(buttons, BUTTON_ROWS, BUTTON_COLUMNS, BUTTON_OFFSET, BUTTON_OFFSET, BUTTON_X_PADDING,
                        BUTTON_Y_PADDING);

        // Add fields to field panel.
        fields.add(labelFactory("Permission"));
        fields.add(permissions);
        fields.add(labelFactory("Location"));
        fields.add(location);
        fields.add(labelFactory("Temperature"));
        fields.add(temperature);
        fields.add(labelFactory("Date"));
        fields.add(date);
        fields.add(labelFactory("Time"));
        fields.add(time);
        fields.add(labelFactory("Time Speed Multiplier"));
        fields.add(timeX);

        // Set field panel display behavior
        SpringUtilities.makeCompactGrid(fields, FIELD_ROWS, FIELD_COLUMNS, 1, 1, 1, FIELD_Y_PADDING);

        // Selecting a location is disabled by default.
        location.setEnabled(false);
        date.setEditor(new JSpinner.DateEditor(date, "yyyy/MM/dd"));
        time.setEditor(new JSpinner.DateEditor(time, "HH:mm:ss"));
    }

}
