/*
notes :
- FIXME :
 */
// imports

class Room {
    // variables
    private String location = "default";
    private Door[] doors = []
    private Light[] lights = []
    private Window[] windows = []
    private PressureSensor[] pressureSensors = []
    private MotionSensor[] motionSensors = []
    private TemperatureSensor[] temperatureSensors = []

    // constructor

    public Room(String location, Door[] doors, Light[] lights, Window[] windows, PressureSensor[] pressureSensors, MotionSensor[] motionSensors, TemperatureSensor[] temperatureSensors) {
        this.location = location;
        this.doors = doors;
        this.lights = lights;
        this.windows = windows;
        this.pressureSensors = pressureSensors;
        this.motionSensors = motionSensors;
        this.temperatureSensors = temperatureSensors;
    }
    
    // methods
    // get/set

}