// imports
package src.elements;

class HouseElement {
    // variables
    private String type = "default";
    private int height = 0;
    private int width = 0;
    private int[] topLeftPos = {0,0};

    // constructor
    public HouseElement(String type, int height, int width, int[] topLeftPos) {
        this.type = type;
        this.height = height;
        this.width = width;
        this.topLeftPos = topLeftPos;
    }

    // methods
    // get/set
    public String getType() {
        return this.type;
    }

    public int[] getSize() {
        int[] size = {this.width, this.height};
        return size;
    }

    public int[] getTopLeftPos() {
        return this.topLeftPos;
    }
}