package view;

import elements.House;
import parameters.Parameters;
import permissions.Permission;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The {@code ProfileViewer} class provides the UI elements for a user to browse the profiles (names, {@code Permission}
 * levels, and locations} they have added to a simulation.
 *
 * @author Jeff Wilgus
 * @author Ayman Shehri
 */
public class ProfileViewer extends JFrame implements ActionListener { // TODO move this logic to controller

    private static final int DIMENSION = 0x100;

    DefaultListModel<String> profiles = new DefaultListModel<>();
    JList<String> list = new JList<>(profiles);
    JButton add = new JButton("Add");
    JButton edit = new JButton("Edit");
    JButton remove = new JButton("Remove");
    Parameters parameters;
    House house;

    /**
     * Constructs a new {@code ProfileViewer} object with the profiles from the specified {@code parameters} and the
     * locations from the specified {@code house}.
     *
     * @param parameters the specified {@code Parameters}
     * @param house the specified {@code House}
     */
    public ProfileViewer(Parameters parameters, House house) {
        // Set window title.
        super("Edit Profiles");

        // Containers for profile list and buttons.
        JScrollPane scrollPane = new JScrollPane(list);
        JPanel buttons = new JPanel();

        // Set window display behavior.
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIMENSION, DIMENSION));
        setResizable(false);

        // Add top-level containers to window.
        add(scrollPane);
        add(buttons, BorderLayout.SOUTH);

        // Populate profile list.
        for (String actor : parameters.getActors()) {
            profiles.addElement(actor);
        }

        // Add buttons to panel
        buttons.add(add);
        buttons.add(edit);
        buttons.add(remove);

        // Register handlers for buttons.
        add.addActionListener(this);
        edit.addActionListener(this);
        remove.addActionListener(this);
        edit.setEnabled(false);
        remove.setEnabled(false);

        // Switch edit and remove button behavior based on whether or not any profile is selected.
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (list.getSelectedIndex() == -1) {
                    // No selection.
                    edit.setEnabled(false);
                    remove.setEnabled(false);
                } else {
                    // Selection.
                    edit.setEnabled(true);
                    remove.setEnabled(true);
                }
            }
        });

        // Keep track of model data.
        this.parameters = parameters;
        this.house = house;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Add":
            case "Edit": {
                SwingUtilities.invokeLater(() -> {
                    ProfileEditor editor = new ProfileEditor(list.getSelectedValue(), house != null);
                    if (house != null) {
                        for (String location : house.getLocations()) {
                            editor.location.addItem(location);
                        }
                    }
                    editor.pack();
                    editor.setLocationRelativeTo(this);
                    editor.setVisible(true);
                    editor.ok.addActionListener(new ConfirmListener(editor));
                });
                break;
            }
            case "Remove": {
                parameters.removeActor(list.getSelectedValue());
                if (house != null) {
                    house.removePerson(list.getSelectedValue());
                }
                profiles.removeElement(list.getSelectedValue());
                break;
            }
            default:
                throw new AssertionError();
        }
    }

    class ConfirmListener implements ActionListener {

        ProfileEditor editor;

        ConfirmListener(ProfileEditor editor) {
            this.editor = editor;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            // Extract input from user
            String role = editor.role.getText();
            Permission permission = (Permission)editor.permission.getSelectedItem();
            String location = editor.location.isEnabled() ? (String)editor.location.getSelectedItem() : null;

            //TODO validate input
            // Add as a profile
            parameters.addActor(role, permission); // TODO exception handling

            // Add in the simulation
            if (location != null) {
                house.addPerson(role, permission, location);
            }

            // Add in the ui
            if (!profiles.contains(role)) {
                profiles.addElement(role);
            }
            editor.dispose();
        }

    }

}
