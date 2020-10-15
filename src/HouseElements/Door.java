package HouseElements;

public class Door {
    private boolean located;
    private boolean isLocked;

    public Door(boolean loc, boolean locked) {
        this.located = loc;
        this.isLocked = locked;
    }

    void isLockedString() {
        if(this.isLocked) {
            System.out.println("This door is locked");
        }
        else {
            System.out.println("This door is not locked");
        }
    }
}
