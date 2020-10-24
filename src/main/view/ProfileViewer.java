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
public class ProfileViewer extends JFrame {

    private static final int DIMENSION = 0x100;

    DefaultListModel<String> profiles = new DefaultListModel<>();
    JList<String> list = new JList<>(profiles);
    JButton add = new JButton("Add");
    JButton edit = new JButton("Edit");
    JButton remove = new JButton("Remove");

    /**
     * Constructs a new {@code ProfileViewer} object with the profiles from the specified {@code parameters} and the
     * locations from the specified {@code house}.
     */
    public ProfileViewer() {
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

    public void populateList(Set<String> names) {
        for (String name : names) {
            profiles.addElement(name);
        }
    }

    public void clear() {
        profiles.clear();
    }

    public void addEditProfileListener(ActionListener listener) { // TODO rename this
        add.addActionListener(listener);
        edit.addActionListener(listener);
        remove.addActionListener(listener);
    }

    public boolean containsProfile(String name) {
        return profiles.contains(name);
    }

    public void addProfile(String name) {
        profiles.addElement(name);
    }

    public String getSelectedValue() {
        return list.getSelectedValue();
    }

    public void removeProfile(String name) {
        profiles.removeElement(name);
    }
}
