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

    private static class RoomInfo {

        Point coordinates;
        int[] states = new int[NUM_STATES];

        RoomInfo(int x, int y) {
            coordinates = new Point(x, y);
        }

    }

    public static final int DOORS_OPEN = 0;
    public static final int DOORS_LOCKED = 1;
    public static final int LIGHTS_ON = 2;
    public static final int WINDOWS_OPEN = 3;
    public static final int WINDOWS_BLOCKED = 4;
    public static final int NUMBER_OF_PEOPLE = 5;
    private static final int ROOM_DIM = 0x80;
    private static final int NUM_STATES = 6;
    private static final int OFFSET = 8;
    private static final int STATE_DIM = 0x10;
    private static final String NULL_HOUSE_MESSAGE = "No house loaded.";
    private static final Color[] STATE_COLORS = {
            Color.ORANGE,
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.GRAY,
            Color.PINK
    };

    int x, y;
    int drawn = 0;
    Map<String, RoomInfo> rooms = new LinkedHashMap<>();

    /**
     * Provides a visual representation of the specified {@code house}.
     *
     * @param house the specified {@code House}
     */
    public void drawHouse(House house) {
        rooms.clear();
        if (house != null) {
            x = getWidth() >>> house.size() - 1;
            y = getHeight() >>> house.size() - 1;
            house.tour((location, room) -> {
                rooms.put(location, new RoomInfo(x, y));
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

    public void changeStatusOfIn(boolean status, int of, String in) {
        if (rooms.containsKey(in)) {
            rooms.get(in).states[of] += (status) ? 1 : -1;
            revalidate();
            repaint();
        }
        // TODO throw exception?
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rooms.isEmpty()) {
            x = getWidth() - g.getFontMetrics().stringWidth(NULL_HOUSE_MESSAGE) >>> 1;
            y = getHeight() >>> 1;
            g.drawString(NULL_HOUSE_MESSAGE, x, y);
        } else {
            for (Map.Entry<String, RoomInfo> entry : rooms.entrySet()) {
                int x = entry.getValue().coordinates.x, y = entry.getValue().coordinates.y;
                int offset = OFFSET, stateIndex = 0;
                String location = entry.getKey();
                g.drawRect(x, y, ROOM_DIM, ROOM_DIM);
                g.drawString(location, x + (ROOM_DIM - g.getFontMetrics().stringWidth(location) >>> 1),
                        y + (ROOM_DIM >>> 1));
                for (int state : entry.getValue().states) {
                    if (state > 0) {
                        g.setColor(STATE_COLORS[stateIndex]);
                        g.fillOval(x + offset, y + offset, STATE_DIM, STATE_DIM);
                        offset += OFFSET + STATE_DIM;
                    }
                    stateIndex++;
                }
            }
        }
    }

}
