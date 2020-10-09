package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class Avatar extends JPanel {

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
        setPreferredSize(new Dimension(128, 128));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = (getWidth() - 128) / 2;
        int y = (getHeight() - 128) / 2;
        g.drawImage(avatar, x, y, null);
    }

    private BufferedImage scale(BufferedImage image) {
        Image temp = image.getScaledInstance(128, 128, Image.SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(temp, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }

}
