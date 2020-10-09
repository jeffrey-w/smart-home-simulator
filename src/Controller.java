import java.util.ArrayList;

public class Controller {


    public void execute(Action action){

    }

    public boolean validatePersonActions(Person person, ArrayList<Action> actions){
        boolean isAuthorized = true;
        for (Action action: actions) {
            for (Enums.Permission permission: person.getProfile().getRole().getPermissions()){
                if (permission != action.getPermission()){
                    isAuthorized = false;
                    break;
                }
            }
        }
        return isAuthorized;
    }
}
