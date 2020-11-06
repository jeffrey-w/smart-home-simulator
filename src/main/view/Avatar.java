package main.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@code Avatar} class provides the UI element for a user profile image.
 *
 * @author Jeff Wilgus
 */
class Avatar extends JPanel {

    private static final int DIMENSION = 0x100;

    private BufferedImage avatar;

    /**
     * Constructs a new {@code Avatar} object from the image at the specified {@code file}
     *
     * @param file The specified {@code File}
     */
    Avatar(File file) {
        try {
            avatar = scale(ImageIO.read(file != null ? file : new File("assets/default-avatar.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = getWidth() - DIMENSION >>> 1;
        int y = getHeight() - DIMENSION >>> 1;
        g.drawImage(avatar, x, y, null);
    }

    // Resize the avatar
    private BufferedImage scale(BufferedImage image) {
        Image temp = image.getScaledInstance(DIMENSION, DIMENSION, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(DIMENSION, DIMENSION, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(temp, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
