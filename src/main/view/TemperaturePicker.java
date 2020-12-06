package main.view;

import main.view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TemperaturePicker extends JFrame {

    JSpinner summer = new JSpinner(ParameterEditor.tempModel());
    JSpinner winter = new JSpinner(ParameterEditor.tempModel());
    JButton ok = new JButton("Ok");

    public TemperaturePicker() {
        super("Default Temperatures");
        JPanel spinners = new JPanel(new SpringLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(256, 128)); // TODO
        setResizable(false);
        spinners.add(ParameterEditor.labelFactory("Summer"));
        spinners.add(summer);
        spinners.add(ParameterEditor.labelFactory("Winter"));
        spinners.add(winter);
        add(spinners);
        add(ok, BorderLayout.SOUTH);
        SpringUtilities.makeCompactGrid(spinners, 2, 2, 1, 8, 16, 16); // TODO
    }

    public void addActionListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

    public double getSummerTemperature() {
        return (Double) summer.getValue();
    }

    public double getWinterTemperature() {
        return (Double) winter.getValue();
    }

}
