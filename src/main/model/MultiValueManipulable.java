package main.model;

import main.model.elements.AbstractManipulable;
import main.model.parameters.Parameters;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code MultiValueManipulable} class represents a bundle of abstract values chosen for settable simulation {@code
 * Parameters}. Values held by a {@code MultiValueManipulable} may be accessed by index.
 *
 * @author Jeff Wilgus
 * @see Parameters
 */
public class MultiValueManipulable extends AbstractManipulable {

    private List<ValueManipulable<?>> valueManipulables = new LinkedList<>();

    /**
     * Adds the specified {@code value} to this {@code MultiValueManipulable}.
     *
     * @param value The specified value
     * @return The index at which the specified {@code value} may be accessed
     * @throws NullPointerException if the specified {@code value} is {@code null}
     */
    public int addValue(Object value) {
        valueManipulables.add(new ValueManipulable<>(Objects.requireNonNull(value)));
        return valueManipulables.size() - 1;
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
