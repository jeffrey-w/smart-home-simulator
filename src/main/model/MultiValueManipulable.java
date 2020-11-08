package main.model;

import main.model.elements.AbstractManipulable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MultiValueManipulable extends AbstractManipulable {

    List<ValueManipulable<?>> valueManipulables = new LinkedList<>();

    public int addValue(Object value) {
        valueManipulables.add(new ValueManipulable<>(Objects.requireNonNull(value)));
        return valueManipulables.size() - 1;
    }

    public ValueManipulable<?> getValueAt(int index) {
        return valueManipulables.get(index);
    }

}
