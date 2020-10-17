package elements;

import permissions.Permission;

import java.util.ArrayList;

public abstract class Place {
    private ArrayList<Permission> occupants = new ArrayList<>();

    public ArrayList<Permission> getOccupants() {
        return occupants;
    }

    public void setOccupants(ArrayList<Permission> occupants) {
        this.occupants = occupants;
    }

    public void addOccupant(Permission occupant){
        occupants.add(occupant);
    }

    public void removeOccupant(Permission occupant){
        occupants.remove(occupant);
    }
}
