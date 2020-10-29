package main.view.viewtils;

import javax.swing.*;
import java.awt.*;

public class ManipulableRenderer extends DefaultListCellRenderer {

    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;

    private static String toWall(int index) {
        switch (index) {
            case NORTH:
                return "North";
            case EAST:
                return "East";
            case SOUTH:
                return "South";
            case WEST:
                return "West";
            default:
                throw new AssertionError();
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setText(value == null ? null : toWall(index));
        return this;
    }

}
