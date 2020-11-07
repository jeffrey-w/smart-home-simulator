package main.view;

import main.model.elements.House;
import main.model.elements.Room;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * The {@code HouseLayoutPanel} class provides the UI elements for visualizing a {@code House}.
 *
 * @author Jeff Wilgus
 */
public class HouseLayoutPanel extends JPanel {

    private static class RoomInfo {

        Point coordinates;
        int[] states = new int[NUMBER_OF_STATES];

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
    private static final int NUMBER_OF_STATES = 6;
    private static final int ROOM_DIM = 0x80;
    private static final int OFFSET = 4;
    private static final int STATE_DIM = 0x10;
    private static final String NULL_HOUSE_MESSAGE = "No house loaded.";

    private static final Color[] STATE_COLORS = {
            Color.BLUE,
            Color.RED,
            Color.YELLOW,
            Color.GREEN,
            Color.GRAY,
            Color.PINK
    };
    int x, y;
    int drawn = 0;
    boolean showStates = false;
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

    /**
     * Updates the {@code Room} at the specified {@code location} on this {@code HouseLayoutPanel} with the status of
     * the specified {@code room}.
     *
     * @param location The specified location
     * @param room The specified {@code Room}
     * @throws NoSuchElementException If the specified {@code location} does not exist on this {@code HouseLayoutPanel}
     * @throws NullPointerException If the specified {@code room} is {@code null}
     */
    public void updateRoom(String location, Room room) {
        if (rooms.containsKey(location)) {
            RoomInfo info = rooms.get(location);
            for (int i = 0; i < NUMBER_OF_STATES; i++) {
                updateState(room, info, i);
            }
        }
        throw new NoSuchElementException("That location does not exist");
    }

    private void updateState(Room room, RoomInfo info, int state) {
        switch (state) {
            case DOORS_OPEN:
                info.states[state] = room.getNumberOfOpenDoors();
                break;
            case DOORS_LOCKED:
                info.states[state] = room.getNumberOfLockedDoors();
                break;
            case LIGHTS_ON:
                info.states[state] = room.getNumberOfLightsOn();
                break;
            case WINDOWS_OPEN:
                info.states[state] = room.getNumberOfWindowsOpen();
                break;
            case WINDOWS_BLOCKED:
                info.states[state] = room.getNumberOfWindowsBlocked();
                break;
            case NUMBER_OF_PEOPLE:
                info.states[state] = room.getNumberOfPeople();
                break;
            default:
                throw new AssertionError();
        }
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
                int stateIndex = 0, offset = OFFSET;
                String location = entry.getKey();
                g.drawRect(x, y, ROOM_DIM, ROOM_DIM);
                g.drawString(location, x + (ROOM_DIM - g.getFontMetrics().stringWidth(location) >>> 1),
                        y + (ROOM_DIM >>> 1));
                if (showStates) {
                    Color color = g.getColor();
                    for (int state : entry.getValue().states) {
                        if (state > 0) {
                            g.setColor(STATE_COLORS[stateIndex]);
                            g.fillOval(x + offset, y + OFFSET, STATE_DIM, STATE_DIM);
                            offset += OFFSET + STATE_DIM;
                        }
                        stateIndex++;
                    }
                    g.setColor(color);
                }
            }
        }
    }

}
