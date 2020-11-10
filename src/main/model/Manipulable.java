package main.model;

import main.model.elements.House;
import main.model.parameters.Parameters;

/**
 * The {@code Manipulable} interface describes objects whose physical configurations may be changed by performing {@code
 * Action}s on them.
 *
 * @author Jeff Wilgus
 * @see Action
 */
public interface Manipulable {

    /**
     * Performs the specified {@code action} on this {@code Manipulable} in the context of the specified {@code
     * parameters} and {@code house}.
     *
     * @param action The specified {@code Action}
     * @param parameters The specified {@code Parameters}
     * @param house The specified {@code house}
     * @return A description of the result of the specified {@code action} on this {@code Manipulable}
     */
    String manipulate(Action action, Parameters parameters, House house);

}
