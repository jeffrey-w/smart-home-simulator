public class Action {
    private boolean state;
    private Item item;
    private Enums.Permission permission;

    public Action(boolean state, Item item) {
        this.state = state;
        this.item = item;
    }

    public Enums.Permission getPermission() {
        return permission;
    }
}
