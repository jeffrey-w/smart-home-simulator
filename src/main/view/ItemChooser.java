package main.view;

import main.model.elements.Manipulable;
import main.view.viewtils.ManipulableRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ItemChooser extends JFrame {

    private static final int DIM = 0x100;

    DefaultListModel<Manipulable> manipulables = new DefaultListModel<>();
    JList<Manipulable> list = new JList<>(manipulables);
    JButton ok = new JButton("Ok");

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

    public Manipulable getSelectedItem() {
        return list.getSelectedValue();
    }

    public void addActionListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

}
