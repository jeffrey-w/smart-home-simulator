package parameters;

import permissions.Permission;

import java.time.Instant;
import java.util.*;

public class Parameters {

    public static final int DEFAULT_TEMPERATURE = 15;

    private Permission permission;
    private Map<String, Permission> actors;
    private String location;
    private Date date;
    private int temperature;
    private boolean on;

    public Parameters() {
        permission = null;
        actors = new HashMap<>();
        location = null;
        date = Date.from(Instant.now());
        temperature = DEFAULT_TEMPERATURE;
        on = false;
    }

    public void addActor(String role, Permission permission) {
        actors.putIfAbsent(role, permission);
    }

    public Permission getPermission() {
        return permission;
    }

    public Map<String, Permission> getActors() {
        return Collections.unmodifiableMap(actors);
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
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
        this.location = location;
    }

    public void setDate(Date date) {
        this.date = Objects.requireNonNull(date);
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

}
