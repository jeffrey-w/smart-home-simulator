package main.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * The {@code ProfileViewer} class provides the UI elements for a user to browse the profiles (names, {@code Permission}
 * levels, and locations} they have added to a simulation.
 *
 * @author Jeff Wilgus
 * @author Ayman Shehri
 */
public class ParameterViewer extends JFrame {

    private static final int DIMENSION = 0x100;

    final DefaultListModel<String> parameters = new DefaultListModel<>();
    final JList<String> list = new JList<>(parameters);
    final JButton add = new JButton("Add");
    final JButton edit = new JButton("Edit");
    final JButton remove = new JButton("Remove");

    /**
     * Constructs a new {@code ProfileViewer} object with the profiles from the specified {@code parameters} and the
     * locations from the specified {@code house}.
     */
    public ParameterViewer(String title) {
        // Set window title.
        super(title);

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

        // Add buttons to panel
        buttons.add(add);
        buttons.add(edit);
        buttons.add(remove);

        // Initially disable edit and remove buttons.
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
    }

    /**
     * Populates this {@code ParameterViewer} with specified {@code names}.
     *
     * @param names The specified names
     */
    public void populateList(Set<String> names) {
        for (String name : names) {
            parameters.addElement(name);
        }
    }

    /**
     * Clears the list of names currently registered by this {@code ParameterViewer}.
     */
    public void clear() {
        parameters.clear();
    }

    /**
     * Registers an event handler on this {@code ParameterViewer} to manipulate the parameters it lists.
     *
     * @param listener The specified event handler
     */
    public void addActionListener(ActionListener listener) {
        add.addActionListener(listener);
        edit.addActionListener(listener);
        remove.addActionListener(listener);
    }

    /**
     * @param name The parameter we are verifying
     * @return {@code true} if the specified parameter is listed by this {@code ParameterViewer}
     */
    public boolean containsParameter(String name) {
        return parameters.contains(name);
    }

    /**
     * Adds the specified parameter to this {@code ParameterViewer}.
     *
     * @param name The name of the parameter to be added
     */
    public void addParameter(String name) {
        parameters.addElement(name);
    }

    /**
     * @return The selected parameter value
     */
    public String getSelectedValue() {
        return list.getSelectedValue();
    }

    /**
     * Remove a given parameter from this {@code ParameterViewer}.
     *
     * @param name The name of the profile to be removed
     */
    public void removeParameter(String name) {
        parameters.removeElement(name);
    }

}
