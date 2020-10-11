/*
What needs to be done :
- House - Rooms - Windows - Obstruction
- Need to make sure that these can work with :
    - house layout reader and maker
    - context of simulation
        - placing people in rooms or outside of house
        - block window movement by puttin arbitrary object

notes :
-
 */
// imports
package src.elements;

class Room {
    // variables
    private String location = "default";
    private Door[] doors = {};
    private Light[] lights = {};
    private Window[] windows = {};

    // constructor

    public Room(String location, Door[] doors, Light[] lights, Window[] windows) {
        this.location = location;
        this.doors = doors;
        this.lights = lights;
        this.windows = windows;

    }

    // methods
    // get/set
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Door[] getDoors() {
        return this.doors;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    public Light[] getLights() {
        return this.lights;
    }

    public void setLights(Light[] lights) {
        this.lights = lights;
    }

    public Window[] getWindows() {
        return this.windows;
    }

    public void setWindows(Window[] windows) {
        this.windows = windows;
    }
}