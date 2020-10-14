package controllers;

import elements.House;
import elements.Place;
import parameters.Parameters;
import permissions.Action;
import permissions.Person;
import view.Dashboard;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Set;

public class SimulationController {

    private static Parameters parameters;
    private static ArrayList<Person> profiles;
    private House house;
    private Dashboard view;

    //Getters and Setters
    public Parameters getParameters() {
        return parameters;
    }

    public ArrayList<Person> getProfiles() {
        return profiles;
    }

    public House getHouse() {
        return house;
    }

    public Dashboard getView() {
        return view;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public void setProfiles(ArrayList<Person> profiles) {
        this.profiles = profiles;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setView(Dashboard view) {
        this.view = view;
    }

    //Methods
    public static void placePerson(Person person, Place place){
        //TODO remove person from previous room
        place.addOccupant(person);
    }

    public static void logIn(Person person){
        parameters.setLoggedUser(person);
    }

    public static void addUser(Person person){
        profiles.add(person);
    }
}
