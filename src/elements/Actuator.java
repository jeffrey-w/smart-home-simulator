// imports
package elements;

class Actuator {
    // variables
    private String type = "default";
    private String targetDevice = "default";

    // constructor
    Actuator(String type, String targetDevice) {
        this.type = type;
        this.targetDevice = targetDevice;
    }

    // methods
    public void actuate(){
    }

    public void deactivate(){
    }

    // get/set
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTargetDevice(String targetDevice) {
        this.targetDevice = targetDevice;
    }
}