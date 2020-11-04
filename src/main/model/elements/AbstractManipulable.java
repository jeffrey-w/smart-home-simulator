package main.model.elements;

import main.model.parameters.permissions.Action;

public abstract class AbstractManipulable implements Manipulable {

    @Override
    public String manipulate(Action action) {
        try {
            return action.doAction(this);
        } catch (ClassCastException e) {
            return "You cannot perform that action on this item.";
        }
    }
}
