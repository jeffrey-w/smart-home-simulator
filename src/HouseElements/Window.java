package HouseElements;

public class Window {
    private boolean located;
    private boolean isBlocked;

    public Window(boolean loc, boolean blocked) {
        this.located = loc;
        this.isBlocked = blocked;
    }

    void isBlockedString() {
        if(this.isBlocked) {
            System.out.println("This window is blocked");
        }
        else {
            System.out.println("This window is not blocked");
        }
    }
}
