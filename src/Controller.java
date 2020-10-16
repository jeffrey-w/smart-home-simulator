import elements.House;
import parameters.Parameters;
import permissions.Permission;
import view.Dashboard;
import view.ProfileViewer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Date;

public class Controller {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
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
        dashboard.addLocationListener(new LocationListener());
        dashboard.addTemperatureListener(new TemperatureListener());
        dashboard.addDateListener(new DateListener());
    }

    class LoadHouseListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileFilter(new FileFilter() { // TODO make this static?

                @Override
                public boolean accept(final File f) {
                    if (f.isDirectory()) {
                        return true;
                    }
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".json");
                }

                @Override
                public String getDescription() {
                    return "JSON files (*.json)";
                }

            });
            if (chooser.showOpenDialog(dashboard) == JFileChooser.APPROVE_OPTION) {
                // TODO house reader logic
                dashboard.activateLocations(house.getLocations());
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
        }

    }

    class LocationListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent e) {
            String location = dashboard.getLocationInput();
            parameters.setLocation(location);
            house.addPerson("user", dashboard.getPermissionInput(), location);
            dashboard.setLocation(location);
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

}
