package main.model.elements;

import main.model.parameters.permissions.Action;

/**
 * The {@code Manipulable} interface describes objects whose physical configurations may be changed by performing {@code
 * Action}s on them.
 *
 * @author Jeff Wilgus
 * @see Action
 */
public interface Manipulable {

    /**
     * Performs the specified {@code action} on this {@code Manipulable}.
     *
     * @param action The specified {@code Action}
     * @return A description of the result of the specified {@code action} on this {@code Manipulable}
     */
    String manipulate(Action action);

}
