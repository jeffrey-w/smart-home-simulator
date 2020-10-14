package view;

import permissions.Permission;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Dashboard extends JFrame {

    private final static Border OUTER = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private final static Border INNER = BorderFactory.createLineBorder(Color.BLACK, 1, true);
    final static Border BORDER = BorderFactory.createCompoundBorder(OUTER, INNER);
    final static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm a");

    ParameterPanel parameters = new ParameterPanel();
    ParameterEditor editor = new ParameterEditor();
    JPanel content = new JPanel();


    public Dashboard() {
        super("Smart Home Simulator");
        JTabbedPane parameterPane = new JTabbedPane();
        JTabbedPane contentPane = new JTabbedPane();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1024, 512));
        setResizable(false);
        setLayout(new BorderLayout());
        add(parameterPane, BorderLayout.WEST);
        add(contentPane, BorderLayout.EAST);
        parameterPane.addTab("Parameters", parameters);
        parameterPane.addTab("Edit", editor);
        parameterPane.setPreferredSize((new Dimension(255, 511)));
        contentPane.addTab("Simulation", content);
        contentPane.setPreferredSize(new Dimension(767, 511));
    }

    public void setPermission(String permission) {
        parameters.setPermission(permission);
    }

    public void setLocation(String location) { // TODO rename this
        parameters.setLocation(location);
    }

    public void setTemperature(String temperature) {
         parameters.setTemperature(temperature);
    }

    public void setDate(Date date) {
        parameters.setDate(dateFormat.format(date));
    }

    public Permission getPermissionInput() {
        return (Permission) editor.permission.getSelectedItem();
    }

    public String getLocationInput() {
        return (String)editor.location.getSelectedItem();
    }

    public Integer getTemperatureInput() {
        return (Integer)editor.temperature.getValue();
    }

    public Date getDateInput() {
        return (Date)editor.date.getValue();
    }

    public void addLoadHouseListener(ActionListener listener) {
        editor.loadHouse.addActionListener(listener);
    }

    public void addProfileEditListener(ActionListener listener) {
        editor.editProfiles.addActionListener(listener);
    }

    public void addPermissionListener(ActionListener listener) {
        editor.permission.addActionListener(listener);
    }

    public void addLocationListener(ActionListener listener) {
        editor.location.addActionListener(listener);
    }

    public void addTemperatureListener(ChangeListener listener) {
        editor.temperature.addChangeListener(listener);
    }

    public void addDateListener(ChangeListener listener) {
        editor.date.addChangeListener(listener);
    }

}
