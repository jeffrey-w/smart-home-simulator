package parameters;

import elements.Place;
import permissions.Permission;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Parameters {

    private static final int DEFAULT_TEMPERATURE = 15;

    private Permission permission;
    private String location;
    private Person loggedUser;
    private LocalDate date;
    private LocalTime time;
    private int temperature;
    private boolean on;

    public Parameters() {
        permission = null;
        location = null;
        loggedUser = null;
        date = LocalDate.now();
        time = LocalTime.now();
        temperature = DEFAULT_TEMPERATURE;
        on = false;
    }

    public Permission getPermission() {
        return permission;
    }

    public String getLocation() {
    public Person getLoggedUser() {
        return loggedUser;
    }

        return location;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean isOn() {
        return on;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public void setLocation(String location) {
    public void setLoggedUser(Person loggedUser) {
        this.loggedUser = loggedUser;
    }

        this.location = location;
    }

    public void setDate(LocalDate date) {
        this.date = Objects.requireNonNull(date);
    }

    public void setTime(LocalTime time) {
        this.time = Objects.requireNonNull(time);
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

}
