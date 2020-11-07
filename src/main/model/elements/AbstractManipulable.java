package main.model.elements;

import main.model.Manipulable;
import main.model.parameters.Parameters;
import main.model.Action;

/**
 * The {@code AbstractManipulable} class provides a minimal implementation of the {@code Manipulable} interface.
 *
 * @author Jeff Wilgus
 * @author Phillipe Vo
 */
public abstract class AbstractManipulable implements Manipulable {

    @Override
    public String manipulate(Action action, Parameters parameters, House house) {
        try {
            return action.doAction(this, parameters, house);
        } catch (ClassCastException e) {
            return "You cannot perform that action on this item.";
        }
    }
}
