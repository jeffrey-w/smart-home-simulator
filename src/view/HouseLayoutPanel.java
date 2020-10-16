package view;

import elements.House;

import javax.swing.*;
import java.awt.*;

import static elements.House.MAX_CONNECTIONS;

public class HouseLayoutPanel extends JPanel {

    private static final int ROOM_DIM = 100;
    private static final String NULL_HOUSE_MESSAGE = "No house loaded.";

    int x, y;
    int drawn = 0;
    House house;

    public HouseLayoutPanel(House house) {
        setHouse(house);
    }

    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (house == null) {
            x = getWidth() + g.getFontMetrics().stringWidth(NULL_HOUSE_MESSAGE) >>> 1;
            y = getHeight() >>> 1;
            g.drawString("No house loaded.", x, y);
        } else {
            x = (getWidth() + ROOM_DIM) >>> 1;
            y = getHeight() >>> 1;
            house.tour("Front", (location, room) -> { // TODO parameterize starting room
                g.drawRect(x, y, ROOM_DIM, ROOM_DIM);
                g.drawString(location, x + (g.getFontMetrics().stringWidth(location) + (ROOM_DIM >>> 2) >>> 1),
                        y + (ROOM_DIM >>> 1));
                nextCoordinate();
            });
        }
    }

    private void nextCoordinate() {
        switch (drawn++ & MAX_CONNECTIONS - 1) { // TODO this is rather arcane, and may break if MAX_CONNECTIONS changes
            case 0:
                x += ROOM_DIM;
                break;
            case 1:
                x -= ROOM_DIM;
                y += ROOM_DIM;
                break;
            case 2:
                x -= ROOM_DIM;
                y -= ROOM_DIM;
                break;
            case 3:
                x += ROOM_DIM;
                y -= ROOM_DIM;
                break;
            default:
                throw new AssertionError();
        }
    }

}
