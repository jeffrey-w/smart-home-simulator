// imports
package elements;

class Light extends HouseElement {

    private int brightness = 0;
    private String color = "#FFFFFF";

    // constructor
    public Light(String type, int height, int width, int[] topLeftPos, int brightness, String color) {
        super(type, height, width, topLeftPos);

        this.brightness = brightness;
        this.color = color;
    }

    // methods
    // get/set
    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}