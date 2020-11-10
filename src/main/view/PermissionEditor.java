package main.view;

import main.model.Action;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The {@code PermissionEditor} class provides the UI for a user to change the permissible actions of all permission types.
 * The permission types include: Parent, Child, Guest, Stranger.
 *
 * @author Émilie Martin
 */
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

    /* Organize permissions by columns.
        Column0 is the action and Columns1-4 are the parent, child, guest and stranger permissions.
     */
    Object[][] permissions = {
            {
                Action.CHANGE_TEMPERATURE,
                    TempChangePermissions[0], TempChangePermissions[1],
                    TempChangePermissions[2], TempChangePermissions[3]
            },
            {
                Action.TOGGLE_LOCK_DOOR,
                    DoorLockTogglePermissions[0], DoorLockTogglePermissions[1],
                    DoorLockTogglePermissions[2], DoorLockTogglePermissions[3]
            },
            {
                Action.TOGGLE_DOOR,
                    DoorTogglePermissions[0], DoorTogglePermissions[1],
                    DoorTogglePermissions[2], DoorTogglePermissions[3]
            },
            {
                Action.TOGGLE_BLOCK_WINDOW,
                    WindowBlockTogglePermissions[0], WindowBlockTogglePermissions[1],
                    WindowBlockTogglePermissions[2], WindowBlockTogglePermissions[3]
            },
            {
                Action.TOGGLE_WINDOW,
                    WindowTogglePermissions[0], WindowTogglePermissions[1],
                    WindowTogglePermissions[2], WindowTogglePermissions[3]
            },
            {
                Action.TOGGLE_LIGHT,
                    LightTogglePermissions[0], LightTogglePermissions[1],
                    LightTogglePermissions[2], LightTogglePermissions[3]
            }
    };

    // Build the actions and permissions into a JTable
    DefaultTableModel model = new DefaultTableModel(permissions, columnNames);
    JTable table = new JTable(model) {
        @Override
        public Class getColumnClass(int col) {
            if (col == 0) {
                return Action.class;
            } else {
                return Boolean.class; // Generate JTable checkboxes
            }
        }
    };

    /**
     * Constructs a new {@code PermissionEditor} object.
     */
    public PermissionEditor() {
        super("Edit Permissions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIM_X, DIM_Y));
        setResizable(false);

        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);
        table.setFillsViewportHeight(true);
    }

    /**
     * Registers an TableModelEvent handler for changing the permissible actions.
     *
     * @param listener The specified TableModelEvent handler
     */
    public void addTableModelListener(TableModelListener listener) {
        model.addTableModelListener(listener);
    }
}
