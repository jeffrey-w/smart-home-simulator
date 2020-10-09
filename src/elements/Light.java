/*
notes :
- FIXME :
 */
// imports

class Light {
    // variables
    private String type = "default";
    private int brightness = 0;
    private String color = "#FFFFFF";

    // constructor
    public Light(String type, int brightness, String color) {
        this.type = type;
        this.brightness = brightness;
        this.color = color;
    }

    // methods
    // get/set
    public String getType() {
        return type;
    }

    public int getBrightness() {
        return brightness;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}