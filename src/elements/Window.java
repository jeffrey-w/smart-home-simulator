// imports
package src.elements;

class Window extends HouseElement {

    private String wallLocation = "north";
    private boolean isBlocked = false;

    // constructor
    public Window(String type, int height, int width, int[] topLeftPos, String wallLocation) {
        super(type, height, width, topLeftPos);

        this.wallLocation = wallLocation;
    }

    // methods
    // get/set
    public void setBlocked(boolean blocked){
        this.isBlocked = blocked;
    }
}