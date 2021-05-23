
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Search_B_M extends JFrame {
    // JTextField και JLabel για την αναζητηση του βιβλιου και περιοδικου
    public JTextField book1,book2;
    private JLabel booklabe1,booklabe2;

    public JTextField magazine1,magazine2;
    private JLabel perlabe1,perlabe2;
    Files f = new Files();

    public void search_Book(){
        //----------Δημιουργια Frame για την αναζητηση του βιβλιου-------//
        
        JFrame search_book = new JFrame(".Αναζήτηση Βιβλίου.");
        JButton book_search = new JButton("Αναζήτηση");
        book_search.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        book_search.setBackground(Color.WHITE);
        book_search.setBounds(150,140,150,30);

        search_book.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));
        search_book.setSize(450,260);
        search_book.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        search_book.setVisible(true);
        search_book.setLocationRelativeTo(null);
        search_book.setLayout(null);
        // JLabel
        booklabe1 = new JLabel("Τίτλος: ");
        booklabe1.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe1.setBounds(80, 40, 130, 30);
        booklabe2 = new JLabel("ISBN: ");
        booklabe2.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe2.setBounds(83, 80, 130, 30);
        // JTextfields
        book1 = new JTextField(20);
        book1.setBackground(Color.WHITE);
        book1.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book1.setBounds(150, 40, 200, 30);

        book2 = new JTextField(20);
        book2.setBackground(Color.WHITE);
        book2.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book2.setBounds(150, 80, 200, 30);

        search_book.add(book1);
        search_book.add(book2);

        search_book.add(booklabe1);
        search_book.add(booklabe2);

        search_book.add(book_search);

        book_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(book1.getText().equals("") && book2.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Δεν συμπλήρωσες όλα τα πεδία!",".Αποτυχία.",JOptionPane.WARNING_MESSAGE);
                }else
                {
                    try {
                        // παιρνω απο τα πεδια τα στοιχεια που εδωσε ο χρηστης και τα περναω ως παραμετρο στη μεθοδο readbook
                        f.readBook(book1.getText(),book2.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    book1.setText(null);
                    book2.setText(null);
                }
            }
        });
    }
    
    public void search_Periodically(){
        //----------Δημιουργια Frame για την αναζητηση του περιοδικου-------//
        
        JFrame search_magazine = new JFrame(".Αναζήτηση Περιοδικού.");
        JButton magazine_search = new JButton("Αναζήτηση");
        magazine_search.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        magazine_search.setBackground(Color.WHITE);
        magazine_search.setBounds(150,140,150,30);

        search_magazine.getRootPane().setBorder(BorderFactory.createMatteBorder(4,4,4,4, Color.BLACK));
        search_magazine.setSize(450,260);
        search_magazine.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        search_magazine.setVisible(true);
        search_magazine.setLocationRelativeTo(null);
        search_magazine.setLayout(null);
        // JLabel
        perlabe1 = new JLabel("Τίτλος: ");
        perlabe1.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe1.setBounds(25, 40, 130, 30);
        perlabe2 = new JLabel("Έτος Έκδοσης: ");
        perlabe2.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe2.setBounds(25, 80, 150, 30);
        // JTextfields
        magazine1 = new JTextField(20);
        magazine1.setBackground(Color.WHITE);
        magazine1.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine1.setBounds(150, 40, 200, 30);

        magazine2 = new JTextField(20);
        magazine2.setBackground(Color.WHITE);
        magazine2.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine2.setBounds(150, 80, 200, 30);

        search_magazine.add(perlabe1);
        search_magazine.add(perlabe2);

        search_magazine.add(magazine1);
        search_magazine.add(magazine2);

        search_magazine.add(magazine_search);

        magazine_search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(magazine1.getText().equals("") && magazine2.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Δεν συμπλήρωσες όλα τα πεδία!",".Αποτυχία.",JOptionPane.WARNING_MESSAGE);
                }else
                {
                    try {
                        // παιρνω απο τα πεδια τα στοιχεια που εδωσε ο χρηστης και τα περναω ως παραμετρο στη μεθοδο readMagazine
                        f.readMagazine(magazine1.getText(),magazine2.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    magazine1.setText(null);
                    magazine2.setText(null);
                }
            }
        });
    }
}
