import elements.House;
import elements.Window;
import parameters.Parameters;
import permissions.Permission;
import util.HouseReader;
import util.JSONFilter;
import view.Dashboard;
import view.ProfileViewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class Controller {

    private static final JSONFilter JSON_FILTER = new JSONFilter();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Controller controller = new Controller();
            controller.dashboard.pack();
            controller.dashboard.setVisible(true);
            controller.dashboard.setLocationRelativeTo(null);
        });
    }

    private House house;
    private final Parameters parameters;
    private final Dashboard dashboard;

    public Controller() {
        parameters = new Parameters();
        dashboard = new Dashboard();
        dashboard.setTemperature(String.valueOf(parameters.getTemperature()));
        dashboard.setDate(parameters.getDate());
        dashboard.addLoadHouseListener(new LoadHouseListener());
        dashboard.addProfileEditListener(new ProfileEditListener());
        dashboard.addPermissionListener(new PermissionListener());
        dashboard.addTemperatureListener(new TemperatureListener());
        dashboard.addDateListener(new DateListener());
        dashboard.addWindowListener(new WindowListener());
    }

    class LoadHouseListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(JSON_FILTER);
            if (chooser.showOpenDialog(dashboard) == JFileChooser.APPROVE_OPTION) {
                HouseReader reader = new HouseReader(chooser.getSelectedFile());
                house = reader.readHouse();
                dashboard.addLocationListener(null);
                dashboard.activateLocations(house.getLocations());
                dashboard.addLocationListener(new LocationListener());
                dashboard.drawHouse(house);
            }
        }

    }

    class ProfileEditListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            SwingUtilities.invokeLater(() -> {
                ProfileViewer viewer = new ProfileViewer(parameters, house);
                viewer.pack();
                viewer.setLocationRelativeTo(dashboard);
                viewer.setVisible(true);
            });
        }

    }

    class PermissionListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            Permission permission = dashboard.getPermissionInput();
            parameters.setPermission(permission);
            dashboard.setPermission(permission.toString());
            // TODO if house...
        }

    }

    class LocationListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            // TODO require that permission be set and a house loaded first
            String location = dashboard.getLocationInput();
            parameters.setLocation(location);
            dashboard.setLocation(location);
            house.addPerson("user", parameters.getPermission(), location);
        }

    }

    class TemperatureListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            int temperature = dashboard.getTemperatureInput();
            parameters.setTemperature(temperature);
            dashboard.setTemperature(String.valueOf(temperature));
        }

    }

    class DateListener implements ChangeListener {

        @Override
        public void stateChanged(final ChangeEvent e) {
            Date date = dashboard.getDateInput();
            parameters.setDate(date);
            dashboard.setDate(date);
        }

    }

    public class WindowListener implements ListSelectionListener {

        @Override
        public void valueChanged(final ListSelectionEvent e) {
            SwingUtilities.invokeLater(() -> {
                WindowBlocker blocker = new WindowBlocker(house.getWindowsOf(parameters.getLocation()));
                blocker.pack();
                blocker.setLocationRelativeTo(dashboard);
                blocker.setVisible(true);
                house.getRoom(parameters.getLocation()).setObstructed(blocker.selection, true); // TODO parameterize obstructed
            });
        }

    }

    static class WindowBlocker extends JFrame {

        int selection;
        JList<Window> list;

        WindowBlocker(Window[] windows) {
            super("Obstruct Windows");
            list = new JList<>(windows);
            JButton ok = new JButton("Ok");
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(0x100, 0x100)); // TODO avoid magic constants
            setResizable(false);
            add(list);
            add(ok, BorderLayout.SOUTH);
            ok.addActionListener(e -> {
                selection = list.getSelectedIndex();
                dispose();
            });
        }

    }

}
