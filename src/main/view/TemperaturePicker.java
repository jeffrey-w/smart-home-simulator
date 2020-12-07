package main.view;

import main.view.viewtils.SpringUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The {@code TemperaturePicker} class provides the UI elements for selecting default temperatures during {@code
 * AwayMode}.
 *
 * @author Jeff Wilgus
 */
public class TemperaturePicker extends JFrame {

    final JSpinner summer = new JSpinner(ParameterEditor.tempModel());
    final JSpinner winter = new JSpinner(ParameterEditor.tempModel());
    final JButton ok = new JButton("Ok");

    /**
     * Constructs a {@code TemperaturePicker}
     */
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

    /**
     * Registers an event handler on this {@code TemperaturePicker}
     *
     * @param listener the specified event handler
     */
    public void addActionListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

    /**
     * Provides the temperature specified for summer.
     *
     * @return The temperature set for the summer
     */
    public double getSummerTemperature() {
        return (Double) summer.getValue();
    }

    /**
     * Provides the temperature specified for winter.
     *
     * @return The temperature set for the winter
     */
    public double getWinterTemperature() {
        return (Double) winter.getValue();
    }

}
