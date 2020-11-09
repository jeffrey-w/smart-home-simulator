package main.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import main.model.parameters.permissions.Action;

public class PermissionEditor extends JFrame {
    private static final int DIM_X = 0x150;
    private static final int DIM_Y = 0x100;

    private String[] columnNames = {"Action", "Parent", "Child", "Guest", "Stranger"};

    Boolean[] TempChangePermissions = Action.CHANGE_TEMPERATURE.getPermissions();
    Boolean[] DoorLockTogglePermissions = Action.TOGGLE_LOCK_DOOR.getPermissions();
    Boolean[] DoorTogglePermissions = Action.TOGGLE_DOOR.getPermissions();
    Boolean[] WindowBlockTogglePermissions = Action.TOGGLE_BLOCK_WINDOW.getPermissions();
    Boolean[] WindowTogglePermissions = Action.TOGGLE_WINDOW.getPermissions();
    Boolean[] LightTogglePermissions = Action.TOGGLE_LIGHT.getPermissions();

    Object[][] permissions = {
            {
                Action.CHANGE_TEMPERATURE.toString(),
                    TempChangePermissions[0], TempChangePermissions[1],
                    TempChangePermissions[2], TempChangePermissions[3]
            },
            {
                Action.TOGGLE_LOCK_DOOR.toString(),
                    DoorLockTogglePermissions[0], DoorLockTogglePermissions[1],
                    DoorLockTogglePermissions[2], DoorLockTogglePermissions[3]
            },
            {
                Action.TOGGLE_DOOR.toString(),
                    DoorTogglePermissions[0], DoorTogglePermissions[1],
                    DoorTogglePermissions[2], DoorTogglePermissions[3]
            },
            {
                Action.TOGGLE_BLOCK_WINDOW.toString(),
                    WindowBlockTogglePermissions[0], WindowBlockTogglePermissions[1],
                    WindowBlockTogglePermissions[2], WindowBlockTogglePermissions[3]
            },
            {
                Action.TOGGLE_WINDOW.toString(),
                    WindowTogglePermissions[0], WindowTogglePermissions[1],
                    WindowTogglePermissions[2], WindowTogglePermissions[3]
            },
            {
                Action.TOGGLE_LIGHT.toString(),
                    LightTogglePermissions[0], LightTogglePermissions[1],
                    LightTogglePermissions[2], LightTogglePermissions[3]
            }
    };

    public PermissionEditor() {
        super("Edit Permissions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIM_X, DIM_Y));
        setResizable(false);
    }

    public void addActionListener(ActionListener listener) {
        this.addActionListener(listener);
    }

    public PermissionEditor display() {
        PermissionEditor editor = new PermissionEditor();

        DefaultTableModel model = new DefaultTableModel(permissions, columnNames);
        JTable table = new JTable(model) {
            @Override
            public Class getColumnClass(int col) {
                if (col ==0) {
                    return String.class;
                } else {
                    return Boolean.class;
                }
            }
        };

        editor.add(table.getTableHeader(), BorderLayout.PAGE_START);
        editor.add(table, BorderLayout.CENTER);
        table.setVisible(true);
        table.setFillsViewportHeight(true);

        editor.pack();
        editor.setVisible(true);

        return editor;
    }
}
