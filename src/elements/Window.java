/*
notes :
- FIXME :
 */
// imports

class Window {
    // variables
    private String type = "default";
    private int height = 0;
    private int width = 0;

    // constructor
    public Window(String type, int height, int width) {
        this.type = type;
        this.height = height;
        this.width = width;
    }

    // methods
    // get/set
    public String getType() {
        return type;
    }

    public int getSize() {
        return [this.height, this.width];
    }
}