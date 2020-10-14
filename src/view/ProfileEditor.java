package view;

import permissions.Permission;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileEditor extends JFrame {

    JTextField role = new JTextField();
    JComboBox<Permission> permission = ParameterEditor.permissionJComboBox();
    JComboBox<String> location = new JComboBox<>();
    JButton ok = new JButton("Ok");

    public ProfileEditor(String role, boolean enableLocation) {
        super("Edit Profile");
        JPanel fields = new JPanel(new SpringLayout());
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(256, 128));
        setResizable(false);
        fields.add(ParameterEditor.labelFactory("Role"));
        fields.add(this.role);
        fields.add(ParameterEditor.labelFactory("Permission"));
        fields.add(permission);
        fields.add(ParameterEditor.labelFactory("Location"));
        fields.add(location);
        SpringUtilities.makeCompactGrid(fields, 3, 2, 1, 1, 1, 16);
        add(fields);
        add(ok, BorderLayout.SOUTH);
        if (role != null) {
            this.role.setText(role);
            this.role.setEnabled(false);
        }
        location.setEnabled(enableLocation);
    }

    public String getRole() {
        return role.getText();
    }

    public Permission getPermission() {
        return (Permission)permission.getSelectedItem();
    }

    public String getLocationInput() { // TODO rename this
        return (String)location.getSelectedItem();
    }

    public void addOkListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

}
