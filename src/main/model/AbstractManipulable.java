package main.model;

import main.model.elements.House;
import main.model.parameters.Parameters;

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
