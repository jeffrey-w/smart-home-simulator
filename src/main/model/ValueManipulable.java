package main.model;

import main.model.elements.AbstractManipulable;

import java.util.Objects;

public class ValueManipulable<T> extends AbstractManipulable {

    T value;

    public ValueManipulable(T value) {
        this.value = Objects.requireNonNull(value);
    }

    T getValue() {
        return value;
    }

}
