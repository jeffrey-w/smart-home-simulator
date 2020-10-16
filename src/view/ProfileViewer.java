package view;

import elements.House;
import parameters.Parameters;
import permissions.Permission;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileViewer extends JFrame implements ActionListener {

    private static final int DIMENSION = 0x100;

    DefaultListModel<String> profiles = new DefaultListModel<>();
    JList<String> list = new JList<>(profiles);
    JButton add = new JButton("Add");
    JButton edit = new JButton("Edit");
    JButton remove = new JButton("Remove");
    Parameters parameters;
    House house;

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
        profiles.addAll(parameters.getActors());
        // Add buttons to panel
        buttons.add(add);
        buttons.add(edit);
        buttons.add(remove);
        // Register handlers for buttons.
        add.addActionListener(this);
        edit.addActionListener(this);
        remove.addActionListener(this);
        // Switch edit button behavior based on whether or not any profiles exist.
        if (profiles.size() == 0) {
            edit.setEnabled(false);
        }
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
                    editor.pack();
                    editor.setLocationRelativeTo(this);
                    editor.setVisible(true);
                    editor.ok.addActionListener(new ConfirmListener(editor));
                });
                break;
            }
            case "Remove": {
                parameters.removeActor(list.getSelectedValue());
                house.removePerson(list.getSelectedValue());
                profiles.removeElement(list.getSelectedValue());
                if (profiles.size() == 0) {
                    edit.setEnabled(false);
                }
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
            String role = editor.role.getText();
            Permission permission = (Permission)editor.permission.getSelectedItem();
            String location = editor.location.isEnabled() ? (String)editor.location.getSelectedItem() : null;
            parameters.addActor(role, permission); // TODO exception handling
            if (location != null) {
                house.addPerson(role, permission, location);
            }
            if (!profiles.contains(role)) {
                profiles.addElement(role);
            }
            edit.setEnabled(true);
            editor.dispose();
        }

    }

}
