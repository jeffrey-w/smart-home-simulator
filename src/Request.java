import java.util.ArrayList;

public class Request {
    private ISource source;
    private ArrayList<Action> actions;
    private boolean status;

    public Request(ISource source, ArrayList<Action> actions) {
        this.source = source;
        this.actions = actions;
    }

    public boolean send(){
        boolean isSuccessful = false;
        //send the request
        //capture the result
        //return the result
        return isSuccessful;
    }
}
