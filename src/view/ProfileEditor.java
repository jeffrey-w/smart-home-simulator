package view;

import permissions.Permission;
import view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code ProfileEditor} class provides the UI elements to edit a specific profile ({@code Permission} level, and
 * location) that has been added to a simulation.
 *
 * @author Jeff Wilgus
 */
public class ProfileEditor extends JFrame {

    private static final int DIM = 0x100;
    private static final int ROWS = 3;
    private static final int COLUMNS = 2;
    private static final int Y_PADDING = 0x30;

    JTextField role = new JTextField();
    JComboBox<Permission> permission = ParameterEditor.permissionJComboBox();
    JComboBox<String> location = new JComboBox<>();
    JButton ok = new JButton("Ok");

    /**
     * Constructs a new {@code ProfileEditor} object for the specified {@code role}. The location of this profile is
     * editable only if specified.
     * @param role the name of the profile being edited; if {@code null}, a new profile will be added
     * @param enableLocation if {@code true}, this profile's location may be edited
     */
    public ProfileEditor(String role, boolean enableLocation) {
        // Set window title.
        super("Edit Profile");
        // A container for fields.
        JPanel fields = new JPanel(new SpringLayout());
        // Set window display behavior.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIM, DIM));
        setResizable(false);
        // Add fields and ok button to window.
        add(fields);
        add(ok, BorderLayout.SOUTH);
        // Add fields to field container.
        fields.add(ParameterEditor.labelFactory("Role"));
        fields.add(this.role);
        fields.add(ParameterEditor.labelFactory("Permission"));
        fields.add(permission);
        fields.add(ParameterEditor.labelFactory("Location"));
        fields.add(location);
        // Set field display behavior.
        SpringUtilities.makeCompactGrid(fields, ROWS, COLUMNS, 1, 1, 1, Y_PADDING);
        // A role is being edited, set the role name and disable changing it.
        if (role != null) {
            this.role.setText(role);
            this.role.setEnabled(false);
        }
        // Set whether or not locations may be edited.
        location.setEnabled(enableLocation);
    }

}
