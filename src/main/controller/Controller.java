package main.controller;

import main.model.Module;
import main.model.elements.House;
import main.model.elements.Room;
import main.model.parameters.Clock;
import main.model.parameters.Parameters;
import main.view.Dashboard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static main.util.SeasonCheck.isSummer;
import static main.util.SeasonCheck.isWinter;

/**
 * The {@code Controller} class provides the interface between runtime simulation objects and the UI elements to
 * manipulate those objects. It serves as the entry point into the program.
 *
 * @author Jeff Wilgus
 * @author Émilie Martin
 */
public class Controller {

    private static final double HVAC_ON_STEP = 0.1;
    private static final double HVAC_OFF_STEP = 0.05;
    private static final double TEMP_TOLERANCE = 0.25;
    public static final int MORNING_START = 5, DAY_START = 11, NIGHT_START = 19;
    public static final int PERIOD1 = 0, PERIOD2 = 1, PERIOD3 = 2;

    // This is the entry point into the program
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
    private final Parameters parameters = new Parameters();
    private final Map<String, ModuleController> modules = new HashMap<>();
    private final Dashboard dashboard = new Dashboard();

    /**
     * Constructs a new {@code Controller} object.
     */
    public Controller() {
        startClock();
        addModules();

        ParameterController parameterController = new ParameterController(this);
        dashboard.addPermissions(parameters.getPermissions().values());
        dashboard.setExternalTemperature(String.valueOf(parameters.getExternalTemperature()));
        dashboard.setDate(parameters.getDate());

        // Add listeners
        dashboard.addSimulationListener(new SimulationListener());
        dashboard.addLoadHouseListener(parameterController.new LoadHouseListener());
        dashboard.addEditProfilesListener(parameterController.new ManageProfilesListener());
        dashboard.addManageProfilesListener(parameterController.new EditProfileListener());
        dashboard.addPersistPermissionListener(parameterController.new PersistPermissionListener());
        dashboard.addPermissionListener(parameterController.new PermissionListener());
        dashboard.addEditPermissionListener(parameterController.new EditPermissionListener());
        dashboard.addTemperatureListener(parameterController.new TemperatureListener());
        dashboard.addDateListener(parameterController.new DateListener());
        dashboard.addTimeXListener(parameterController.new TimeXListener());
        dashboard.addTimeUpdateListener(parameterController.new TimeUpdateListener());
        dashboard.addActionSelectionListener(new ActionSelectionListener());

        dashboard.drawHouse(house);
    }

    private void startClock() { // TODO get rid of extra calls to redrawHouse
        (new Timer(1000, e -> {
            int[] fields = parameters.getClockTime();
            LocalTime time = LocalTime.of(fields[0], fields[1], fields[2]); // TODO avoid magic constants
            dashboard.setTime(fields);
            // TODO check if simulation is on
            // Toggle lights on AwayMode
            if (parameters.isAwayMode()) {
                for (Room room : house) {
                    room.toggleLights(room.isAwayLight() && isBetween(time, parameters.getAwayLightStart(),
                            parameters.getAwayLightEnd()));
                }
                redrawHouse();
            } else {
                if (isSummer(parameters.getDate())) {
                    if (house.hasTemperatureAberration(parameters.getExternalTemperature()) && house
                            .hasObstructedWindow()) {
                        sendToConsole(
                                "A blocked window has prevented SHH from opening or closing the windows in this house.",
                                Dashboard.MessageType.ERROR);
                    } else {
                        for (Room room : house) {
                            room.toggleWindows(room.getTemperature() > parameters.getExternalTemperature());
                        }
                    }
                }
            }

            // Fluctuate temperature
            if (parameters.isOn()) {
                for (String location : house.getLocations()) {
                    Room room = house.getRoom(location);
                    double roomTemp = room.getTemperature();
                    double equilibriumTemp = getEquilibriumTemp(location);
                    int sign;

                    if (!isWithinTolerance(roomTemp, equilibriumTemp)) {
                        sign = (int) Math.signum(equilibriumTemp - roomTemp);
                        room.setTemperature(
                                roomTemp + (room.isHVACon() ? HVAC_ON_STEP : HVAC_OFF_STEP) * parameters
                                        .getTimeMultiplier() * sign);
                    } else {
                        room.setHVAC(!room.isHVACon());
                        sign = (int) Math.signum(getEquilibriumTemp(location) - roomTemp);
                        room.setTemperature(equilibriumTemp + TEMP_TOLERANCE * sign);
                    }

                }
            }

            // check home temperature -> if <= 0 -> alert users about pipe burst potential
            if (house != null) {
                double averageHouseTemperature = 0;
                double sumHouseTemperature = 0;
                for (Room room : house) {
                    double roomTemp = room.getTemperature();

                    sumHouseTemperature = sumHouseTemperature + roomTemp;
                }
                averageHouseTemperature = sumHouseTemperature / house.getSize();

                if (Double.compare(averageHouseTemperature, 0) <= 0) {
                    sendToConsole(
                            "WARNING : Temperature inside home is below 0C, pipes might burst.",
                            Dashboard.MessageType.WARNING);
                }
            }

            // redraw the house to see temperature
            if (house != null) {
                redrawHouse();
            }
        })).start();
    }

    public double getEquilibriumTemp(String room) {
        if (parameters.isAwayMode()) {
            if (isSummer(parameters.getDate())) {
                return parameters.getDefaultSummerTemperature();
            }
            else if (isWinter(parameters.getDate())) {
                return parameters.getDefaultWinterTemperature();
            }
        }
        if (parameters.isTemperatureOverridden(room) || house.getRoom(room).isHVACon()) {
            return parameters.getTemperatureControlZone(room).getDesiredTemperatureFor(room, getPeriod());
        }
        return parameters.getExternalTemperature();
    }

    /**
     * Gets the periods of day based on the time of the day
     *
     * @return The period ID that the time falls in
     */
    private int getPeriod() {
        int[] clockTime = parameters.getClockTime();
        int hour = clockTime[Clock.HOURS];
        if (hour > MORNING_START && hour < DAY_START) {
            //morning
            return PERIOD1;
        } else if (hour > DAY_START && hour < NIGHT_START) {
            //day
            return PERIOD2;
        } else {
            //night
            return PERIOD3;
        }
    }

    private boolean isWithinTolerance(double actual, double spec) {
        return Double.compare(actual, spec - TEMP_TOLERANCE) >= 0 && Double.compare(actual, spec + TEMP_TOLERANCE) <= 0;
    }

    private boolean isBetween(LocalTime time, LocalTime origin, LocalTime bound) {
        return origin.compareTo(time) <= 0 && bound.compareTo(time) > 0;
    }

    private void addModules() {
        modules.put(Module.SHC.getName(), new CoreModuleController(this, dashboard.addModule(Module.SHC)));
        modules.put(Module.SHP.getName(), new SecurityModuleController(this, dashboard.addModule(Module.SHP)));
        modules.put(Module.SHH.getName(), new HeatingModuleController(this, dashboard.addModule(Module.SHH)));
    }

    /*
     * Event handlers.
     */
    class SimulationListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (house != null) {
                if (!getHeatingModuleController().unzonedRooms().isEmpty()) {
                    sendToConsole("All rooms must belong to a heating zone before starting the simulation." , Dashboard.MessageType.ERROR);
                } else {
                    parameters.setOn(((JToggleButton) e.getSource()).isSelected());
                    dashboard.toggleOnButton();
                    dashboard.showStates(parameters.isOn());
                    getCoreModuleController().toggleAutoLight();
                    redrawHouse();
                    sendToConsole("Simulation has " + (parameters.isOn() ? "begun." : "ended."),
                            Dashboard.MessageType.NORMAL);
                    if (parameters.isOn() && parameters.isAwayMode() && house.isOccupied()) {
                        getSecurityModuleController().startAwayModeCountdown();
                    }
                }
            } else {
                sendToConsole("Please load a house to start the simulation.", Dashboard.MessageType.ERROR);
                ((JToggleButton) e.getSource()).setSelected(false);
            }
        }

    }

    class ActionSelectionListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {
                modules.get(dashboard.getSelectedModule()).control();
            }
        }
    }

    /*
     * Utility methods for subordinate controllers.
     */
    Parameters getParameters() {
        return parameters;
    }

    House getHouse() {
        return house;
    }

    void setHouse(House house) {
        this.house = house;
    }

    Dashboard getDashboard() {
        return dashboard;
    }

    CoreModuleController getCoreModuleController() {
        return ((CoreModuleController) modules.get(Module.SHC.getName()));
    }

    SecurityModuleController getSecurityModuleController() {
        return ((SecurityModuleController) modules.get(Module.SHP.getName()));
    }

    HeatingModuleController getHeatingModuleController() {
        return ((HeatingModuleController) modules.get(Module.SHH.getName()));
    }

    void sendToConsole(String message, Dashboard.MessageType type) {
        dashboard.sendToConsole(message, type, true);
    }

    void redrawHouse() {
        for (String location : house.getLocations()) {
            dashboard.updateRoom(location, house.getRoom(location));
        }
        dashboard.redrawHouse();
    }

}
