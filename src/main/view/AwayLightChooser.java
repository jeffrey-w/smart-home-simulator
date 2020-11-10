package main.view;

import main.model.elements.Light;
import main.model.elements.Room;
import main.model.parameters.AwayMode;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.List;
import java.util.*;

import static java.awt.BorderLayout.SOUTH;

/**
 * The {@code AwayLightChooser} class provides the UI elements to select the specific {@code Room}s and {@code Time}s
 * during which {@code Lights} shall be on during {@code AwayMode}
 *
 * @author Jeff Wilgus
 * @see AwayMode
 * @see Light
 * @see Room
 */
public class AwayLightChooser extends JFrame {

    private static final int DIM = 0x100;
    private static final int CELLS = 2;

    /**
     * Provides a new {@code AwayLightChooser} of the specified {@code locations}.
     *
     * @param locations the specified locations
     * @return A new {@code AwayLightChooser}
     * @throws NullPointerException If the specified {@code locations} are {@code null}
     */
    public static AwayLightChooser of(Set<String> locations) {
        AwayLightChooser awayLightChooser = new AwayLightChooser();
        awayLightChooser.addLocations(locations);
        return awayLightChooser;
    }

    private final List<JCheckBox> locations = new LinkedList<>();
    JSpinner startSpinner = new JSpinner(new SpinnerDateModel());
    JSpinner endSpinner = new JSpinner(new SpinnerDateModel());
    private final JButton ok = new JButton("Ok");

    /**
     * @return The locations selected by this {@code AwayLightChooser}
     */
    public Set<String> getSelectedLocations() {
        Set<String> selected = new HashSet<>();
        for (JCheckBox box : locations) {
            if (box.isSelected()) {
                selected.add(box.getText());
            }
        }
        return selected;
    }

    /**
     * @return The selected start time for away light mode
     */
    public LocalTime getStart() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime((Date) startSpinner.getModel().getValue());
        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    /**
     * @return The selected end time for away light mode
     */
    public LocalTime getEnd() {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime((Date) endSpinner.getModel().getValue());
        return LocalTime.of(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    /**
     * Registers an event handler for on this {@code AwayLightChooser}
     *
     * @param listener The specified event handler
     */
    public void addActionListener(ActionListener listener) {
        ok.addActionListener(listener);
    }

    private AwayLightChooser() {

        super("Away Light Control");
        JPanel staticFields = new JPanel(new GridLayout(CELLS, 1));
        JPanel timeSpinners = new JPanel(new GridLayout(1, CELLS));
        final JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startSpinner, "HH:mm");
        DateFormatter startFormatter = (DateFormatter) startEditor.getTextField().getFormatter();
        final JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endSpinner, "HH:mm");
        DateFormatter endFormatter = (DateFormatter) endEditor.getTextField().getFormatter();
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
