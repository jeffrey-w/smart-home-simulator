package main.model;

import main.model.parameters.Parameters;

import java.util.LinkedList;
import java.util.List;

/**
 * The {@code MultiValueManipulable} class represents a bundle of abstract values chosen for settable simulation {@code
 * Parameters}. Values held by a {@code MultiValueManipulable} may be accessed by index.
 *
 * @author Jeff Wilgus
 * @see Parameters
 */
public class MultiValueManipulable extends ValueManipulable<Object> {

    private final List<ValueManipulable<?>> valueManipulables = new LinkedList<>();

    /**
     * Constructs a new {@code ValueManipulable} with the specified {@code value}.
     *
     * @param value The value held by this {@code ValueManipulable}
     * @throws NullPointerException If the specified {@code value} is {@code null}
     */
    public MultiValueManipulable(Object value) {
        super(value);
    }

    /**
     * Adds the specified {@code value} to this {@code MultiValueManipulable}.
     *
     * @param value The specified value
     */
    public void addValue(Object value) {
        valueManipulables.add(new ValueManipulable<>(value));
    }

    /**
     * Provides the {@code ValueManipulable} held by this {@code MultiValueManipulable} at the specified {@code index}.
     *
     * @param index the specified index
     * @return the {@code ValueManipulable} at the specified {@code index}
     * @throws IndexOutOfBoundsException if the specified {@code index} is negative or greater than or equal to the
     * cardinality of this {@code MultiValueManipulable}
     */
    public ValueManipulable<?> getValueAt(int index) {
        return valueManipulables.get(index);
    }

}
