package main.model;

import main.model.parameters.Parameters;

/**
 * The {@code ValueManipulable} class represents an abstract value chosen for simulation {@code Parameters}.
 *
 * @param <T> The domain of this {@code ValueManipulable}
 * @author Jeff Wilgus
 * @see Parameters
 */
public class ValueManipulable<T> extends AbstractManipulable {

    private final T value;

    /**
     * Constructs a new {@code ValueManipulable} with the specified {@code value}.
     *
     * @param value The value held by this {@code ValueManipulable}
     */
    public ValueManipulable(T value) {
        this.value = value;
    }

    /**
     * @return The value held by this {@code ValueManipulable}
     */
    public T getValue() {
        return value;
    }

}
