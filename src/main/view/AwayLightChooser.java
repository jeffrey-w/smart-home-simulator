package main.view;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;
import java.util.*;

import static java.awt.BorderLayout.SOUTH;

public class AwayLightChooser extends JFrame {

    private static final int DIM = 0x100;
    private static final int CELLS = 2;

    public static AwayLightChooser of(Set<String> locations) {
        AwayLightChooser awayLightChooser = new AwayLightChooser();
        awayLightChooser.addLocations(locations);
        return awayLightChooser;
    }

    private final List<JCheckBox> locations = new LinkedList<>();
    JSpinner startSpinner = new JSpinner(new SpinnerDateModel());
    JSpinner endSpinner = new JSpinner(new SpinnerDateModel());
    private final JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startSpinner, "HH:mm");
    private final JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endSpinner, "HH:mm");
    private final JButton ok = new JButton("Ok");


    public Set<String> getSelectedLocations() {
        Set<String> selected = new HashSet<>();
        for (JCheckBox box : locations) {
            if (box.isSelected()) {
                selected.add(box.getText());
            }
        }
        return selected;
    }

    public LocalTime getStart() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime((Date) startSpinner.getModel().getValue());
        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public LocalTime getEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime((Date) endSpinner.getModel().getValue());
        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public void addActionListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

    private AwayLightChooser() {

        super("Away Light Control");
        JPanel staticFields = new JPanel(new GridLayout(CELLS, 1));
        JPanel timeSpinners = new JPanel(new GridLayout(1, CELLS));
        DateFormatter startFormatter = (DateFormatter) startEditor.getTextField().getFormatter();
        DateFormatter endFormatter = (DateFormatter)endEditor.getTextField().getFormatter();
        startFormatter.setAllowsInvalid(false);
        startFormatter.setOverwriteMode(true);
        endFormatter.setAllowsInvalid(false);
        endFormatter.setOverwriteMode(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(DIM, DIM));
        setResizable(false);
        timeSpinners.add(startEditor);
        timeSpinners.add(endEditor);
        staticFields.add(timeSpinners);
        staticFields.add(ok);
        add(staticFields, SOUTH);
    }

    private void addLocations(Set<String> locations) {
        JPanel boxes = new JPanel(new GridLayout(locations.size(), 1));
        for (String location : locations) {
            JCheckBox box = new JCheckBox(location);
            boxes.add(box);
            this.locations.add(box);
        }
        add(boxes);
    }

}
