package view;

import parameters.Parameters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileViewer extends JFrame implements ActionListener {

    JList<String> profiles;
    JButton add = new JButton("Add");
    JButton edit = new JButton("Edit");
    JButton remove = new JButton("Remove");
    Parameters parameters;

    public ProfileViewer(String[] profiles, Parameters parameters) {
        super("Edit Profiles");
        this.profiles = new JList<>(profiles);
        JScrollPane scrollPane = new JScrollPane(this.profiles);
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
        if (profiles.length == 0) {
            edit.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        String actionCommand = e.getActionCommand();
        ProfileEditor editor = new ProfileEditor();
        switch (actionCommand) {
            case "Add": {
                int result = JOptionPane.showConfirmDialog(null, editor, "Enter profile information.",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    parameters.addActor(editor.getRole(), editor.getPermission());
                    edit.setEnabled(true);
                }
                break;
            }
            case "Edit": {
                editor.setRole(profiles.getSelectedValue());
                int result = JOptionPane.showConfirmDialog(null, editor, "Edit profile permissions.",
                        JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    parameters.addActor(editor.getRole(), editor.getPermission());
                }
                break;
            }
            case "Remove": {
                parameters.removeActor(profiles.getSelectedValue());
                if (profiles.getModel().getSize() == 0) {
                    edit.setEnabled(false);
                }
                break;
            }
            default:
                throw new AssertionError();
        }
    }
}
