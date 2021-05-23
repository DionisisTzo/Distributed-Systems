
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class HomePage extends JFrame {

    private JButton display, add, edit, delete;

    public HomePage() {
        super("Welcome to - The New Music ");

        //----------------FRAME------------------------------
        setSize(450, 610);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        Container pane = new Icon();

        //--------------INITIALIZE_ATTRIBUTES-------------------
        display = new JButton("Display");
        add = new JButton("Add");
        edit = new JButton("Edit");
        delete = new JButton("Delete");
        JLabel label1 = new JLabel("New Albums / Songs");

        //---------------DESIGNS--------------------------------
        label1.setFont(new Font("Courier", Font.BOLD, 28));
        label1.setForeground(Color.white);
        label1.setBounds(80, 350, 310, 35);

        display.setFont(new Font("Courier", Font.BOLD, 15));
        display.setBackground(Color.decode("#E8E8E8"));
        display.setBounds(145, 410, 150, 30);

        add.setFont(new Font("Courier", Font.BOLD, 15));
        add.setBackground(Color.decode("#E8E8E8"));
        add.setBounds(145, 450, 150, 30);

        edit.setFont(new Font("Courier", Font.BOLD, 15));
        edit.setBackground(Color.decode("#E8E8E8"));
        edit.setBounds(145, 490, 150, 30);

        delete.setFont(new Font("Courier", Font.BOLD, 15));
        delete.setBackground(Color.decode("#E8E8E8"));
        delete.setBounds(145, 530, 150, 30);

        pane.setBackground(Color.decode("#484848"));

        //--------------ACTION_PERFORMANCE---------------------
        display.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new Library(1); } });
        add.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new Library(2); } });
        edit.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new Library(3); } });
        delete.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new Library(4); } });

        //--------------ADD_COMPONENTS-------------------------
        pane.add(display);
        pane.add(add);
        pane.add(edit);
        pane.add(delete);
        pane.add(label1);

        pane.setLayout(null);
        setContentPane(pane);
        pane.revalidate();
        setVisible(true);
        setResizable(false);
    }

    /**
     * Inner class used to paint the imagine for the home page
     */
    private class Icon extends JPanel {

        Image img;

        public Icon() {
            try {
                img = ImageIO.read(new File("music.png"));
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        }

        @Override
        public void paintComponent(Graphics G) {
            super.paintComponent(G);
            G.drawImage(img,  20, 10, 400, 328, null);

        }
    }
}
