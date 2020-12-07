package main.view;

import main.model.Manipulable;
import main.view.viewtils.ManipulableRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The {@code ItemChooser} class provides the UI elements to choose a specific {@code Manipulable} household item to
 * perform an {@code Action} on.
 *
 * @author Jeff Wilgus
 */
public class ItemChooser extends JFrame {

    private static final int DIM = 0x100;

    final DefaultListModel<Manipulable> manipulables = new DefaultListModel<>();
    final JList<Manipulable> list = new JList<>(manipulables);
    final JButton ok = new JButton("Ok");

    /**
     * Provides a new {@code ItemChoose} populated with the specified {@code item}.
     *
     * @param items The specified items
     * @return A new {@code ItemChooser}
     * @throws NullPointerException If the specified {@code items} are {@code null}
     */
    public static ItemChooser of(Manipulable[] items) {
        ItemChooser chooser = new ItemChooser();
        chooser.list.setCellRenderer(new ManipulableRenderer());
        for (Manipulable manipulable : items) {
            chooser.manipulables.addElement(manipulable);
        }
        return chooser;
    }

    private ItemChooser() {
        super("Item Selection");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIM, DIM));
        setResizable(false);

        add(list);
        add(ok, BorderLayout.SOUTH);
    }

    /**
     * Provides the currently selected {@code Manipulable} household item.
     *
     * @return The currently selected item
     */
    public Manipulable getSelectedItem() {
        return list.getSelectedValue();
    }

    /**
     * Registers the specified event handler on this {@code ItemChooser}
     *
     * @param listener The specified event handler
     */
    public void addActionListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

}
