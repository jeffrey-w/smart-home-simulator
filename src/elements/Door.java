/*
notes :
- FIXME :
 */
// imports

class Door {
    // variables
    private String type = "default";
    private int height = 0;
    private int width = 0;

    // constructor
    public Door(String type, int height, int width) {
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