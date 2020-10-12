// imports
package elements;

class Door extends HouseElement {

    private String wallLocation = "north";

    // constructor
    public Door(String type, int height, int width, int[] topLeftPos, String wallLocation) {
        super(type, height, width, topLeftPos);

        this.wallLocation = wallLocation;
    }

    // methods
    // get/set
}