
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class Frame extends JPanel {

    Image img;

    public Frame() {
        try {
            img = ImageIO.read(new File("UOTA.png"));// Διαβαζω την εικονα
            img.getScaledInstance(28, 28, Image.SCALE_DEFAULT);// Αρχικοποιω τις διαστασεις
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Ζωγραφιζει το image στο Background Panel
        g.drawImage(img, 0, 0, 425, 280, null);
    }
}
