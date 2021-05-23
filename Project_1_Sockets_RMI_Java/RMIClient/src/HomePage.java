
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HomePage extends JFrame {

    private JButton login, register, exit;

    public HomePage() {
        super("Welcome to - VIVA - booking");

        //----------------FRAME------------------------------
        setSize(450, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#F2DB9D")));
        Container pane = new Icon();

        //--------------INITIALIZE_ATTRIBUTES-------------------
        login = new JButton("Login");
        register = new JButton("Register");
        exit = new JButton("Exit");

        //---------------DESIGNS--------------------------------
        login.setFont(new Font("Courier", Font.BOLD, 15));
        login.setBackground(Color.decode("#F2DB9D"));
        login.setBounds(140, 370, 150, 30);

        register.setFont(new Font("Courier", Font.BOLD, 15));
        register.setBackground(Color.decode("#F2DB9D"));
        register.setBounds(140, 410, 150, 30);

        exit.setFont(new Font("Courier", Font.BOLD, 15));
        exit.setBackground(Color.decode("#F2DB9D"));
        exit.setBounds(140, 450, 150, 30);

        pane.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        login.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new Login(); } });
        register.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new Register(); } });
        exit.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { System.exit(0); } });

        //--------------ADD_COMPONENTS-------------------------
        pane.add(login);
        pane.add(register);
        pane.add(exit);

        pane.setLayout(null);
        pane.revalidate();
        setContentPane(pane);
        setVisible(true);
        setResizable(false);

    }

    /**
     * Inner class used to paint the actual viva card image
     */
    private class Icon extends JPanel {

        Image img;

        public Icon() {
            try {
                img = ImageIO.read(new File("viva.png"));
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }

        @Override
        public void paintComponent(Graphics G) {
            super.paintComponent(G);
            G.drawImage(img, -10, 15, 438, 340, null);

        }
    }
}
