package view;

import permissions.Permission;

import javax.swing.*;

public class ProfileEditor extends JPanel {

    JTextField role = new JTextField();
    JComboBox<Permission> permission = new JComboBox<>();

    public ProfileEditor() {
        setLayout(new SpringLayout());
        add(new JLabel("Role"));
        add(role);
        add(new JLabel("Permission"));
        add(permission);
        for (Permission permission : ParameterEditor.PERMISSIONS) {
            this.permission.addItem(permission);
        }
        SpringUtilities.makeCompactGrid(this, 2, 2, 1, 1, 1, 1);
    }

    public String getRole() {
        return role.getText();
    }

    public Permission getPermission() {
        return (Permission)permission.getSelectedItem();
    }

    public void setRole(final String role) {
        this.role.setText(role);
        this.role.setEnabled(false);
    }
}
