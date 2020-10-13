package elements;

import permissions.Person;

import java.util.ArrayList;

public abstract class Place {
    private ArrayList<Person> occupants = new ArrayList<>();

    public ArrayList<Person> getOccupants() {
        return occupants;
    }

    public void setOccupants(ArrayList<Person> occupants) {
        this.occupants = occupants;
    }

    public void addOccupant(Person occupant){
        occupants.add(occupant);
    }

    public void removeOccupant(Person occupant){
        occupants.remove(occupant);
    }
}
