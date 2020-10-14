package view;

import elements.House;
import parameters.Parameters;
import permissions.Permission;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileViewer extends JFrame implements ActionListener {

    DefaultListModel<String> profiles = new DefaultListModel<>();
    JList<String> list = new JList<>(profiles);
    JButton add = new JButton("Add");
    JButton edit = new JButton("Edit");
    JButton remove = new JButton("Remove");
    Parameters parameters;
    House house;

    public ProfileViewer(Parameters parameters, House house) {
        super("Edit Profiles");
        this.profiles.addAll(parameters.getActors());
        JScrollPane scrollPane = new JScrollPane(list);
        JPanel buttons = new JPanel();
        add.addActionListener(this);
        edit.addActionListener(this);
        remove.addActionListener(this);
        buttons.add(add);
        buttons.add(edit);
        buttons.add(remove);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(256, 256));
        setResizable(false);
        add(scrollPane);
        add(buttons, BorderLayout.SOUTH);
        if (profiles.size() == 0) {
            edit.setEnabled(false);
        }
        this.parameters = parameters;
        this.house = house;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) { // TODO abstract Add and Edit logic
            case "Add": {
                SwingUtilities.invokeLater(() -> {
                    ProfileEditor editor = new ProfileEditor(null, house != null);
                    editor.pack();
                    editor.setLocationRelativeTo(this);
                    editor.setVisible(true);
                    editor.addOkListener(new OkListener(editor));
                });
                break;
            }
            case "Edit": {
                SwingUtilities.invokeLater(() -> {
                    ProfileEditor editor = new ProfileEditor(list.getSelectedValue(), house != null);
                    editor.pack();
                    editor.setLocationRelativeTo(this);
                    editor.setVisible(true);
                    editor.addOkListener(new OkListener(editor));
                });
                break;
            }
            case "Remove": {
                parameters.removeActor(list.getSelectedValue());
                // TODO house remove actor
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

    class OkListener implements ActionListener { // TODO rename this

        ProfileEditor editor;

        OkListener(ProfileEditor editor) {
            this.editor = editor;
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            String role = editor.getRole();
            Permission permission = editor.getPermission();
            // TODO if Location activated and selected do house placement logic
            parameters.addActor(role, permission); // TODO exception handling
            if (!profiles.contains(role)) {
                profiles.addElement(role);
            }
            edit.setEnabled(true);
            editor.dispose();
        }

    }

}
