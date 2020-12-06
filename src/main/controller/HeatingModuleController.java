package main.controller;

import main.model.Action;
import main.model.Manipulable;
import main.model.MultiValueManipulable;
import main.model.ValueManipulable;
import main.view.*;

import javax.swing.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The {@code HeatingModuleController} class provides services regarding delivering HVAC-related (heating, ventilation,
 * air conditioning) of the smart home functionality. This includes operations such as creating temperature control
 * zones (for heating and cooling), assigning rooms to those zones, and changing the temperature of the rooms within a
 * zone so that all temperatures of that room are uniform.
 *
 * @author Émilie Martin
 */
public class HeatingModuleController extends AbstractModuleController {

    private static final Manipulable EMPTY_MANIPULABLE = new ValueManipulable<>(null);

    /**
     * Constructs a new {@code HeatingModuleController} object with the specified {@code parent} and {@code view}.
     *
     * @param parent The {@code Controller} to which this {@code HeatingModuleController} is subordinate to
     * @param view The {@code ModuleView} that this {@code HeatingModuleController} controls
     * @throws NullPointerException If the specified {@code parent} or {@code view} is {@code null}
     */
    public HeatingModuleController(Controller parent, ModuleView view) {
        super(parent, view);
    }

    @Override
    public void control() {
        if (canAct()) {
            switch (view.getSelectedAction()) {
                case CHANGE_TEMPERATURE: {
                    MultiValueManipulable manipulable = new MultiValueManipulable(parent.getParameters().getLocation());
                    try {
                        manipulable.addValue(Double.parseDouble(JOptionPane
                                .showInputDialog(parent.getDashboard(), "Enter a temperature.", "Change Temperature",
                                        JOptionPane.PLAIN_MESSAGE)));
                    } catch (NumberFormatException e) {
                        parent.sendToConsole("Please enter a number.", Dashboard.MessageType.ERROR);
                        return;
                    }
                    performActionOn(Action.CHANGE_TEMPERATURE, manipulable);
                    break;
                }
                case READ_TEMPERATURES:
                    performActionOn(Action.READ_TEMPERATURES, EMPTY_MANIPULABLE);
                    break;
                case MANAGE_TEMPERATURE_CONTROL_ZONES:
                    if (parent.getParameters().isOn()) {
                        parent.sendToConsole("You cannot manipulate heating zones while the simulation is on.",
                                Dashboard.MessageType.ERROR);
                        return;
                    }
                    ParameterViewer viewer = new ParameterViewer("Temperature Control Zones");
                    for (String zone : parent.getParameters().getZones()) {
                        viewer.addParameter(zone);
                    }
                    viewer.addActionListener(e -> {
                        Collection<String> in = null, out = unzonedRooms();
                        switch (e.getActionCommand()) {
                            case "Add":
                                if (out.isEmpty()) {
                                    parent.sendToConsole("You have already zoned every room.",
                                            Dashboard.MessageType.ERROR);
                                    return;
                                }
                            case "Edit":
                                String id = viewer.getSelectedValue();
                                if (id != null) {
                                    in = parent.getParameters().getZone(id).getRooms();
                                }
                                ZoneEditor zoneEditor = new ZoneEditor(in, out);
                                zoneEditor.setZoneName(id);
                                zoneEditor.addActionCommand(f -> {
                                    MultiValueManipulable manipulable =
                                            new MultiValueManipulable(zoneEditor.getZoneName());
                                    manipulable.addValue(zoneEditor.getTemp(0));
                                    manipulable.addValue(zoneEditor.getTemp(1));
                                    manipulable.addValue(zoneEditor.getTemp(2)); // TODO avoid magic constants
                                    manipulable.addValue(zoneEditor.getRooms());
                                    performActionOn(Action.MANAGE_TEMPERATURE_CONTROL_ZONES, manipulable);
                                    if (e.getActionCommand().equals(("Add"))) {
                                        viewer.addParameter(zoneEditor.getZoneName());
                                    }
                                    zoneEditor.dispose();
                                });
                                zoneEditor.pack();
                                zoneEditor.setLocationRelativeTo(parent.getDashboard());
                                zoneEditor.setVisible(true);
                                break;
                            case "Remove":
                                MultiValueManipulable manipulable = new MultiValueManipulable(null);
                                manipulable.addValue(viewer.getSelectedValue());
                                performActionOn(Action.MANAGE_TEMPERATURE_CONTROL_ZONES, manipulable);
                                viewer.removeParameter(viewer.getSelectedValue());
                                break;
                        }
                    });
                    viewer.pack();
                    viewer.setLocationRelativeTo(parent.getDashboard());
                    viewer.setVisible(true);
                    break;
                case SET_DEFAULT_TEMPERATURE: {
                    TemperaturePicker picker = new TemperaturePicker();
                    picker.addActionListener(e -> {
                        MultiValueManipulable manipulable = new MultiValueManipulable(picker.getSummerTemperature());
                        manipulable.addValue(picker.getWinterTemperature());
                        performActionOn(Action.SET_DEFAULT_TEMPERATURE, manipulable);
                        picker.dispose();
                    });
                    picker.pack();
                    picker.setLocationRelativeTo(parent.getDashboard());
                    picker.setVisible(true);
                }
            }
        } else {
            String message = "Please select a permission and location to choose an action.";
            if (parent.getHouse() == null) {
                message += " You must first load a house to select a location.";
            }
            parent.sendToConsole(message, Dashboard.MessageType.ERROR);
        }
    }

    Collection<String> unzonedRooms() {
        Set<String> unzoned = new HashSet<>();
        for (String location : parent.getHouse().getLocations()) {
            try {
                parent.getParameters().getTemperatureControlZone(location);
            } catch (NoSuchElementException e) {
                unzoned.add(location);
            }
        }
        return unzoned;
    }

    private boolean canAct() {
        return !(parent.getParameters().getPermission() == null || parent.getParameters().getLocation() == null);
    }
}
