package main.view;

import main.model.Action;
import main.model.parameters.Parameters;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * The {@code PermissionEditor} class provides the UI for a user to change the permissible {@code Action}s of all {@code
 * Permission} levels.
 *
 * @author Émilie Martin
 * @see Action
 * @see main.model.parameters.permissions.Permission
 */
public class PermissionEditor extends JFrame {
    private static final int DIM_X = 0x400;
    private static final int DIM_Y = 0x200;

    private static final String[] COLUMN_NAMES = {"Action", "Parent", "Child", "Guest", "Stranger"};

    /* Organize permissions by columns.
       Column0 is the action and Columns1-4 are the parent, child, guest and stranger permissions.
     */
    Object[][] permissions;
    final DefaultTableModel model;
    final JTable table;

    /**
     * Constructs a new {@code PermissionEditor} object with the specified {@code Parameters}.
     *
     * @param parameters The specified parameters
     */
    public PermissionEditor(Parameters parameters) {
        super("Edit Permissions");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIM_X, DIM_Y));
        setResizable(false);

        parsePermissions(parameters);

        // Build the actions and permissions into a JTable
        model = new DefaultTableModel(permissions, COLUMN_NAMES);
        table = new JTable(model) {
            @Override
            public Class<?> getColumnClass(int col) {
                if (col == 0) {
                    return Action.class;
                } else {
                    return Boolean.class; // Generate JTable checkboxes
                }
            }
        };

        add(table.getTableHeader(), BorderLayout.PAGE_START);
        add(table, BorderLayout.CENTER);
        table.setFillsViewportHeight(true);
    }

    private void parsePermissions(Parameters parameters) {
        int index = 0;
        this.permissions = new Object[Action.values().length][];
        for (Action action : Action.values()) {
            boolean[] isPermissible = action.isPermissibleBy(parameters);
            this.permissions[index] = new Object[isPermissible.length + 1];
            this.permissions[index][0] = action;
            for (int i = 0; i < isPermissible.length; i++) {
                this.permissions[index][i + 1] = isPermissible[i];
            }
            index++;
        }
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
