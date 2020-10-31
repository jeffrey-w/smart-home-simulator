package main.view;

import main.model.elements.House;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The {@code HouseLayoutPanel} class provides the UI elements for visualizing a {@code House}.
 *
 * @author Jeff Wilgus
 */
public class HouseLayoutPanel extends JPanel {

    private static final int ROOM_DIM = 0x80;
    private static final String NULL_HOUSE_MESSAGE = "No house loaded.";

    int x, y;
    int drawn = 0;
    Map<String, Point> rooms = new LinkedHashMap<>();

    /**
     * Provides a visual representation of the specified {@code house}.
     * @param house the specified {@code House}
     */
    public void drawHouse(House house) {
        rooms.clear();
        if (house != null) {
            x = getWidth() >>> house.size() - 1;
            y = getHeight() >>> house.size() - 1;
            house.tour((location, room) -> {
                rooms.put(location, new Point(x, y));
                if ((++drawn & 1) == 0) { // drawn + 1 is even
                    x += ROOM_DIM;
                } else {
                    y += ROOM_DIM;
                }
            });
        }
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rooms.isEmpty()) {
            x = getWidth() - g.getFontMetrics().stringWidth(NULL_HOUSE_MESSAGE) >>> 1;
            y = getHeight() >>> 1;
            g.drawString(NULL_HOUSE_MESSAGE, x, y);
        } else {
            for (Map.Entry<String, Point> entry : rooms.entrySet()) {
                int x = entry.getValue().x, y = entry.getValue().y;
                String location = entry.getKey();
                g.drawRect(x, y, ROOM_DIM, ROOM_DIM);
                g.drawString(location, x + (ROOM_DIM - g.getFontMetrics().stringWidth(location) >>> 1),
                        y + (ROOM_DIM >>> 1));
            }
        }
    }

}
