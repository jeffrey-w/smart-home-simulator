import java.util.ArrayList;

public class Role {
    private Enums.RoleType roleType;
    private ArrayList<Enums.Permission> permissions;

    public ArrayList<Enums.Permission> getPermissions() {
        return permissions;
    }

    public Role(Enums.RoleType roleType) {
        //set the roleType
        this.roleType = roleType;
        //set the permissions based on the roleType
        permissions = new ArrayList<>();
        switch (roleType){
            case ADULT:
                permissions.add(Enums.Permission.CAN_OPEN_DOOR);
                break;
            case CHILD:
                permissions.add(Enums.Permission.CAN_CLOSE_DOOR);
                break;
            case GUEST:
                permissions.add(Enums.Permission.CAN_CLOSE_WINDOW);
                break;
            case STRANGER:
                permissions.add(Enums.Permission.CAN_CLOSE_DOOR);
                permissions.add(Enums.Permission.CAN_CLOSE_WINDOW);
                break;
            default:
                break;
        }


    }
}
