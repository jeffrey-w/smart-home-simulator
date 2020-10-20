package main.view;

import main.model.elements.House;

import javax.swing.*;
import java.awt.*;

/**
 * The {@code HouseLayoutPanel} class provides the UI elements for visualizing a {@code House}.
 *
 * @author Jeff Wilgus
 */
public class HouseLayoutPanel extends JPanel { // TODO move this logic to Controller

    private static final int ROOM_DIM = 100;
    private static final String NULL_HOUSE_MESSAGE = "No house loaded.";

    int x, y;
    int drawn = 0;
    House house;

    /**
     * Constructs a new {@code HouseLayoutPanel} object.
     *
     * @param house The {@code House} object being visualized
     */
    public HouseLayoutPanel(House house) {
        setHouse(house);
    }

    /**
     * Sets the {@code House} object being visualized to that specified. Calls to this method cause this panel to be
     * redrawn.
     *
     * @param house the {@code House} being visualized
     */
    public void setHouse(House house) {
        this.house = house;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (house == null) {
            x = getWidth() - g.getFontMetrics().stringWidth(NULL_HOUSE_MESSAGE) >>> 1;
            y = getHeight() >>> 1;
            g.drawString("No house loaded.", x, y);
        } else {
            x = getWidth() >>> house.size() - 1;
            y = getHeight() >>> house.size() - 1;
            house.tour((location, room) -> {
                g.drawRect(x, y, ROOM_DIM, ROOM_DIM);
                g.drawString(location, x + (ROOM_DIM - g.getFontMetrics().stringWidth(location) >>> 1),
                        y + (ROOM_DIM >>> 1));
                if ((++drawn & 1) == 0) { // drawn + 1 is even
                    x += ROOM_DIM;
                } else {
                    y += ROOM_DIM;
                }
            });
        }
    }

}
