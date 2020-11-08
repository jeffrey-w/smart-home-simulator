package main.model;

import main.model.elements.AbstractManipulable;
import main.model.parameters.Parameters;

import java.util.Objects;

/**
 * The {@code ValueManipulable} class represents an abstract value chosen for simulation {@code Parameters}.
 *
 * @param <T> The domain of this {@code ValueManipulable}
 *
 * @author Jeff Wilgus
 * @see Parameters
 */
public class ValueManipulable<T> extends AbstractManipulable {

    private final T value;

    /**
     * Constructs a new {@code ValueManipulable} with the specified {@code value}.
     * @param value The value held by this {@code ValueManipulable}
     * @throws NullPointerException If the specified {@code value} is {@code null}
     */
    public ValueManipulable(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     *
     * @return The value held by this {@code ValueManipulable}
     */
    public T getValue() {
        return value;
    }

}
