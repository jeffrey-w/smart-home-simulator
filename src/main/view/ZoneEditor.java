package main.view;

import main.view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ZoneEditor extends JFrame {

    final JTextField name = new JTextField();
    final JSpinner tempOne = new JSpinner(ParameterEditor.tempModel());
    final JSpinner tempTwo = new JSpinner(ParameterEditor.tempModel());
    final JSpinner tempThree = new JSpinner(ParameterEditor.tempModel());
    final JCheckBox selectOne = new JCheckBox();
    final JCheckBox selectTwo = new JCheckBox();
    final JCheckBox selectThree = new JCheckBox();
    final DefaultListModel<String> in = new DefaultListModel<>();
    final DefaultListModel<String> out = new DefaultListModel<>();
    final JList<String> inList = new JList<>(in);
    final JList<String> outList = new JList<>(out);
    final JButton add = new JButton("<");
    final JButton remove = new JButton(">");
    final JButton ok = new JButton("Ok");

    public ZoneEditor(Collection<String> in, Collection<String> out) {
        super("Edit Zone");
        JPanel attributes = new JPanel(new SpringLayout());
        JPanel rooms = new JPanel(new GridLayout(1, 3));
        JPanel buttons = new JPanel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(512, 256)); // TODO
        setResizable(false);
        add(attributes, BorderLayout.NORTH);
        add(rooms);
        add(ok, BorderLayout.SOUTH);
        attributes.add(new JLabel()); // Placeholder
        attributes.add(name);
        attributes.add(ParameterEditor.labelFactory("Name"));
        attributes.add(name);
        attributes.add(selectOne);
        attributes.add(ParameterEditor.labelFactory("Period 1"));
        attributes.add(tempOne);
        attributes.add(selectTwo);
        attributes.add(ParameterEditor.labelFactory("Period 2"));
        attributes.add(tempTwo);
        attributes.add(selectThree);
        attributes.add(ParameterEditor.labelFactory("Period 3"));
        attributes.add(tempThree);
        buttons.add(add);
        buttons.add(remove);
        rooms.add(inList);
        rooms.add(buttons);
        rooms.add(outList);
        SpringUtilities.makeCompactGrid(attributes, 4, 3, 1, 1, 1, 1);
        populateModelWith(this.in, in);
        populateModelWith(this.out, out);
        tempTwo.setEnabled(false);
        tempThree.setEnabled(false);
        setupCheckBoxes();
        setupButtons();
    }

    private void populateModelWith(DefaultListModel<String> model, Collection<String> values) {
        if (values != null) {
            for (String value : values) {
                model.addElement(value);
            }
        }
    }

    private void setupCheckBoxes() {
        selectOne.setSelected(true);
        selectOne.setEnabled(false);
        selectTwo.addChangeListener(e -> {
            if (selectTwo.isSelected()) {
                tempTwo.setEnabled(true);
            } else {
                tempTwo.setEnabled(false);
            }
        });
        selectThree.addChangeListener(e -> {
            if (selectThree.isSelected()) {
                tempThree.setEnabled(true);
            } else {
                tempThree.setEnabled(false);
            }
        });
    }

    private void setupButtons() {
        add.setEnabled(false);
        remove.setEnabled(false);
        inList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (inList.getSelectedIndex() == -1) {
                    remove.setEnabled(false);
                } else {
                    remove.setEnabled(true);
                }
            }
        });
        outList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (outList.getSelectedIndex() == -1) {
                    add.setEnabled(false);
                } else {
                    add.setEnabled(true);
                }
            }
        });
        add.addActionListener(e -> {
            this.in.addElement(outList.getSelectedValue());
            this.out.removeElement(outList.getSelectedValue());
        });
        remove.addActionListener(e -> {
            this.out.addElement(inList.getSelectedValue());
            this.in.removeElement(inList.getSelectedValue());
        });
    }

    public void addActionCommand(ActionListener listener) {
        ok.addActionListener(listener);
    }

    public String getZoneName() {
        return name.getText();
    }

    public Double getTemp(int period) {
        switch (period) {
            case 0:
                return (Double) tempOne.getValue();
            case 1:
                if (selectTwo.isSelected()) {
                    return (Double) tempTwo.getValue();
                }
                return null;
            case 2:
                if (selectThree.isSelected()) {
                    return (Double) tempThree.getValue();
                }
                return null;
            default:
                throw new IllegalArgumentException("Invalid period selection.");
        }
    }

    public Collection<String> getRooms() {
        Set<String> rooms = new HashSet<>();
        for (int i = 0; i < in.getSize(); i++) {
            rooms.add(in.getElementAt(i));
        }
        return rooms;
    }

    public void setZoneName(String id) {
        name.setText(id);
    }
}
