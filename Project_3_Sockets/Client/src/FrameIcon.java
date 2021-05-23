
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FrameIcon extends JPanel {

    Image img;

    public FrameIcon() {
        try {
            img = ImageIO.read(new File("shout.jpg"));// Διαβαζω την εικονα
            img.getScaledInstance(28, 28, Image.SCALE_DEFAULT);// Αρχικοποιω τις διαστασεις
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Ζωγραφιζει το image στο Background Panel
        g.drawImage(img, 0, 0, 430, 220, null);
    }
}
