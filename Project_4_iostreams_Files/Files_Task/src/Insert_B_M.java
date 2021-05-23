
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Insert_B_M extends JFrame {

    // JTextField και JLabel για την καταχωρηση του βιβλιου και περιοδικου
    private JTextField book1, book2, book3, book4, book5, book6, book7, book8;
    private JLabel booklabe1, booklabe2, booklabe3, booklabe4, booklabe5, booklabe6, booklabe7, booklabe8;

    private JTextField magazine1, magazine2, magazine3, magazine4, magazine5, magazine6, magazine7;
    private JLabel perlabe1, perlabe2, perlabe3, perlabe4, perlabe5, perlabe6, perlabe7;
    Files f = new Files();

    public void insert_Book() {
        //-----------Δημιουργια Frame για την καταχωρηση βιβλιου -------//
        
        JFrame insert_book = new JFrame(".Καταχώρηση Βιβλίου.");

        JButton book_confirm = new JButton("Καταχώρηση");
        book_confirm.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        book_confirm.setBackground(Color.WHITE);
        book_confirm.setBounds(110, 450, 150, 30);

        JButton book_back = new JButton("Πίσω");
        book_back.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        book_back.setBackground(Color.WHITE);
        book_back.setBounds(270, 450, 150, 30);

        insert_book.getRootPane().setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.BLACK));
        insert_book.setSize(550, 550);
        insert_book.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        insert_book.setVisible(true);
        insert_book.setLocationRelativeTo(null);
        insert_book.setLayout(null);
        // JLabel
        booklabe1 = new JLabel("Τίτλος: ");
        booklabe1.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe1.setBounds(100, 40, 130, 30);

        booklabe2 = new JLabel("ISBN: ");
        booklabe2.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe2.setBounds(100, 90, 130, 30);

        booklabe3 = new JLabel("Συγγραφέας: ");
        booklabe3.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe3.setBounds(100, 140, 130, 30);

        booklabe4 = new JLabel("Έτος Έκδοσης: ");
        booklabe4.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe4.setBounds(100, 190, 130, 30);

        booklabe5 = new JLabel("Είδος: ");
        booklabe5.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe5.setBounds(100, 240, 130, 30);

        booklabe6 = new JLabel("Σελίδες: ");
        booklabe6.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe6.setBounds(100, 290, 130, 30);

        booklabe7 = new JLabel("Περιγραφή: ");
        booklabe7.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe7.setBounds(100, 340, 130, 30);

        booklabe8 = new JLabel("Θέση βιβλίου: ");
        booklabe8.setFont(new Font("TimesRomans", Font.BOLD, 16));
        booklabe8.setBounds(100, 390, 130, 30);

        // JTextFields
        book1 = new JTextField(20);
        book1.setBackground(Color.WHITE);
        book1.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book1.setBounds(230, 40, 200, 30);

        book2 = new JTextField(20);
        book2.setBackground(Color.WHITE);
        book2.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book2.setBounds(230, 90, 200, 30);

        book3 = new JTextField(20);
        book3.setBackground(Color.WHITE);
        book3.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book3.setBounds(230, 140, 200, 30);

        book4 = new JTextField(20);
        book4.setBackground(Color.WHITE);
        book4.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book4.setBounds(230, 190, 200, 30);

        book5 = new JTextField(20);
        book5.setBackground(Color.WHITE);
        book5.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book5.setBounds(230, 240, 200, 30);

        book6 = new JTextField(20);
        book6.setBackground(Color.WHITE);
        book6.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book6.setBounds(230, 290, 200, 30);

        book7 = new JTextField(30);
        book7.setBackground(Color.WHITE);
        book7.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book7.setBounds(230, 340, 200, 30);

        book8 = new JTextField(20);
        book8.setBackground(Color.WHITE);
        book8.setFont(new Font("TimesRomans", Font.BOLD, 15));
        book8.setBounds(230, 390, 200, 30);
        // Περασμα components μεσα στο frame 
        insert_book.add(booklabe1);
        insert_book.add(booklabe2);
        insert_book.add(booklabe3);
        insert_book.add(booklabe4);
        insert_book.add(booklabe5);
        insert_book.add(booklabe6);
        insert_book.add(booklabe7);
        insert_book.add(booklabe8);

        insert_book.add(book1);
        insert_book.add(book2);
        insert_book.add(book3);
        insert_book.add(book4);
        insert_book.add(book5);
        insert_book.add(book6);
        insert_book.add(book7);
        insert_book.add(book8);

        insert_book.add(book_confirm);
        insert_book.add(book_back);

        book_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                // Ελεγχος αν ολα τα πεδια ειναι ειναι συπληρωμενα
                if (book1.getText().equals("") || book2.getText().equals("") || book3.getText().equals("") || book4.getText().equals("") || book5.getText().equals("") || book6.getText().equals("") || book7.getText().equals("") || book8.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Δεν συμπληρώθηκαν όλα τα πεδία!", ".Αποτυχία.", JOptionPane.WARNING_MESSAGE);
                } else {
                    // Παιρνω τα στοιχεια που υπαρχουν απο το καθε πεδιο
                    Book book = new Book(book1.getText(), book2.getText(), book3.getText(), book4.getText(), book5.getText(), book6.getText(), book7.getText(), book8.getText());
                    
                    try {
                        f.writeBook(book); // Καλω τη μεθοδο writeBook οπου γινεται η εγγραφη των αντικειμενων στο αρχειο
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Τα στοιχεία καταχωρήθηκαν με επιτυχία!");
                    // Αδειαζω τα πεδια για νεα καταχωρηση 
                    book1.setText(null);
                    book2.setText(null);
                    book3.setText(null);
                    book4.setText(null);
                    book5.setText(null);
                    book6.setText(null);
                    book7.setText(null);
                    book8.setText(null);
                }
            }
        });
        
        // Κουμπι για να κλεισει το frame και να γυρισει στο αρχικο frame Home
        book_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insert_book.dispose(); 
            }
        });

    }
    //-----------Δημιουργια Frame για την καταχωρηση Περιοδικου -------//
    public void insert_Magazine() {
        JFrame insert_Magazine = new JFrame(".Καταχώρηση Περιοδικού.");
        
        JButton magazine_confirm = new JButton("Καταχώρηση");
        magazine_confirm.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        magazine_confirm.setBackground(Color.WHITE);
        magazine_confirm.setBounds(110, 410, 150, 30);
        
        JButton magazine_back = new JButton("Πίσω");
        magazine_back.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        magazine_back.setBackground(Color.WHITE);
        magazine_back.setBounds(290, 410, 150, 30);

        insert_Magazine.getRootPane().setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.BLACK));
        insert_Magazine.setSize(550, 550);
        insert_Magazine.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        insert_Magazine.setVisible(true);
        insert_Magazine.setLocationRelativeTo(null);
        insert_Magazine.setLayout(null);
        // JLabel
        perlabe1 = new JLabel("Τίτλος: ");
        perlabe1.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe1.setBounds(100, 40, 130, 30);

        perlabe2 = new JLabel("Αριθμός Τόμου: ");
        perlabe2.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe2.setBounds(100, 90, 150, 30);

        perlabe3 = new JLabel("Αριθμός Τεύχους: ");
        perlabe3.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe3.setBounds(100, 140, 150, 30);

        perlabe4 = new JLabel("Έτος Έκδοσης: ");
        perlabe4.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe4.setBounds(100, 190, 130, 30);

        perlabe5 = new JLabel("Θεματική: ");
        perlabe5.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe5.setBounds(100, 240, 130, 30);

        perlabe6 = new JLabel("Σελίδες: ");
        perlabe6.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe6.setBounds(100, 290, 130, 30);

        perlabe7 = new JLabel("Θέση Περιοδικού: ");
        perlabe7.setFont(new Font("TimesRomans", Font.BOLD, 16));
        perlabe7.setBounds(100, 340, 150, 30);

        // JTextFields
        magazine1 = new JTextField(20);
        magazine1.setBackground(Color.WHITE);
        magazine1.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine1.setBounds(250, 40, 200, 30);

        magazine2 = new JTextField(20);
        magazine2.setBackground(Color.WHITE);
        magazine2.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine2.setBounds(250, 90, 200, 30);

        magazine3 = new JTextField(20);
        magazine3.setBackground(Color.WHITE);
        magazine3.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine3.setBounds(250, 140, 200, 30);

        magazine4 = new JTextField(20);
        magazine4.setBackground(Color.WHITE);
        magazine4.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine4.setBounds(250, 190, 200, 30);

        magazine5 = new JTextField(20);
        magazine5.setBackground(Color.WHITE);
        magazine5.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine5.setBounds(250, 240, 200, 30);

        magazine6 = new JTextField(20);
        magazine6.setBackground(Color.WHITE);
        magazine6.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine6.setBounds(250, 290, 200, 30);

        magazine7 = new JTextField(30);
        magazine7.setBackground(Color.WHITE);
        magazine7.setFont(new Font("TimesRomans", Font.BOLD, 15));
        magazine7.setBounds(250, 340, 200, 30);

        insert_Magazine.add(perlabe1);
        insert_Magazine.add(perlabe2);
        insert_Magazine.add(perlabe3);
        insert_Magazine.add(perlabe4);
        insert_Magazine.add(perlabe5);
        insert_Magazine.add(perlabe6);
        insert_Magazine.add(perlabe7);

        insert_Magazine.add(magazine1);
        insert_Magazine.add(magazine2);
        insert_Magazine.add(magazine3);
        insert_Magazine.add(magazine4);
        insert_Magazine.add(magazine5);
        insert_Magazine.add(magazine6);
        insert_Magazine.add(magazine7);

        insert_Magazine.add(magazine_confirm);
        insert_Magazine.add(magazine_back);

        magazine_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                if (magazine1.getText().equals("") || magazine2.getText().equals("") || magazine3.getText().equals("") || magazine4.getText().equals("") || magazine5.getText().equals("") || magazine6.getText().equals("") || magazine7.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Δεν συμπληρώθηκαν όλα τα πεδία!", ".Αποτυχία.", JOptionPane.WARNING_MESSAGE);
                } else {
                    Magazine magazine = new Magazine(magazine1.getText(), magazine2.getText(), magazine3.getText(), magazine4.getText(), magazine5.getText(), magazine6.getText(), magazine7.getText());
                    
                    try {
                        f.writeMagazine(magazine); // Καλω τη μεθοδο writeMagazine οπου γινεται η εγγραφη των αντικειμενων στο αρχειο
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Τα στοιχεία καταχωρήθηκαν με επιτυχία!");
                    magazine1.setText(null);
                    magazine2.setText(null);
                    magazine3.setText(null);
                    magazine4.setText(null);
                    magazine5.setText(null);
                    magazine6.setText(null);
                    magazine7.setText(null);
                }
            }
        });
        
        magazine_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                insert_Magazine.dispose();
            }

        });
    }
}
