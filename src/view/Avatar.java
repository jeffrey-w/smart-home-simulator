package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Avatar extends JPanel {

    private static final int DIMENSION = 0x100;

    private BufferedImage avatar;

    Avatar(File file) {
        try {
            avatar = scale(ImageIO.read(file));
        } catch (IOException | IllegalArgumentException e) { // TODO remove IllegalArgumentException
            try {
                avatar = scale(ImageIO.read(new File("assets/default-avatar.png")));
            } catch (IOException ioException) {
                System.err.println("failed to load default image."); // TODO this is sloppy
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - DIMENSION) >>> 1;
        int y = (getHeight() - DIMENSION) >>> 1;
        g.drawImage(avatar, x, y, null);
    }

    private BufferedImage scale(BufferedImage image) {
        Image temp = image.getScaledInstance(DIMENSION, DIMENSION, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(DIMENSION, DIMENSION, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(temp, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
