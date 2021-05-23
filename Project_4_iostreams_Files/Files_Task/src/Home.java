
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame implements ActionListener {

    private JButton insert, search, exit, confirm;

    public Home() {
        super(".Βιβλιοθήκη UoA.");
        //---------------------------Δημιουργια αρχικου frame Home----------------//
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Δημιουργια αντικειμενων
        insert = new JButton("Καταχώρηση");
        search = new JButton("Αναζήτηση");
        exit = new JButton("Έξοδος");
        // Βαζω συντεταγμενες και μεγεθος κουμπιων
        insert.setBounds(25, 300, 150, 30);
        search.setBounds(245, 300, 150, 30);
        exit.setBounds(134, 350, 150, 30);
        // Χρωματιζω και φτιαχνω τα κουμπια
        insert.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        insert.setBackground(Color.WHITE);
        search.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        search.setBackground(Color.WHITE);
        exit.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        exit.setBackground(Color.WHITE);
        
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, Color.BLACK));
        Container pane = getContentPane();
        pane = new Frame(); // Δημιουργω αντικειμενο της κλασεις Frame και το βαζω μεσα στο Container
        // Αρχικοποιω των Container με μηδεν για να μπουν τα κουμπια και η εικονα στη θεση που οριζω 
        pane.setLayout(null); 
        // Εισαγω τα κουμπια στο container 
        pane.add(insert);
        pane.add(search);
        pane.add(exit);

        // Ενεργοποιω τα αντικειμενα στο να ακουσουν στην εντολη (πατημα) που θα κανει ο χρηστης
        // και απο παρακατω γινεται η υλοποιηση των κουμπιων 
        insert.addActionListener(this);
        search.addActionListener(this);
        exit.addActionListener(this);

        setContentPane(pane); // Τοποθέτηση υποδοχέα στο πλαίσιο
        pane.revalidate(); // Ανανεωνω το frame για να εμφανιστει διοτι αλλιως πρεπει να πειραχτει χειροκινητα
    }
    
    //--------------------------Εκτέλεση των εντολών που επιλέγη ο χρήστης---------//
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == insert) { // Για την καταχωρηση 

            JFrame newf1 = new JFrame(".Επιλογή."); // Δημιουργια αντικειμενου Frame
            ButtonGroup G1 = new ButtonGroup(); // Εγγυαται οτι μπορει να επιλεγει μονο ενα κουμπι καθε φορα. 1 για Βιβλιο / 2 για Περιοδικο
            JLabel L1 = new JLabel("Επιλέξτε:");
            newf1.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            newf1.setSize(420, 200);
            newf1.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            newf1.setLocationRelativeTo(null);
            newf1.setVisible(true);
            newf1.setLayout(null);
            // Δημιουργω τα αντικειμενα
            JRadioButton vivlio = new JRadioButton("Βιβλίο");
            JRadioButton periodiko = new JRadioButton("Περιοδικό");
            confirm = new JButton("OK"); // Κουμπι που θα με πηγαινει στην καταχωρηση βιβλιου η περιοδικου
            // Χρωματιζω και επεξεργαζομαι το κουμπι ΟΚ
            confirm.setFont(new Font("TimesRoman", Font.PLAIN, 13));
            confirm.setBackground(Color.WHITE);
            // Δινω διαστασεις
            vivlio.setBounds(120, 30, 100, 50);
            periodiko.setBounds(220, 30, 100, 50);
            confirm.setBounds(125, 90, 80, 30);
            L1.setBounds(20, 30, 150, 50);
            // Βαζω τα κουμπια και τις επιλογες μεσα στο frame
            newf1.add(vivlio);
            newf1.add(periodiko);
            newf1.add(confirm);
            newf1.add(L1);
            // βαζω τις πιλογες (βιβλιο - περιοδικο) μεσα στον υποδοχεα
            // Εγγυαται οτι μπορει να επιλεγει μονο ενα κουμπι καθε φορα. Βιβλιο ή Περιοδικο
            G1.add(vivlio);
            G1.add(periodiko);

            // Επιλογη χρηστη για καταχωρηση βιβλιου η περιοδικου αντιστοιχα
            confirm.addActionListener(new ActionListener() {
                Insert_B_M ib = new Insert_B_M(); // Δημιουργω αντικειμενο τυπου κλασης Insert_B_M

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (vivlio.isSelected()) { // Αν το βιβλιο ειναι selected
                        newf1.dispose(); // κλεινω το frame επιλογης βιβλιου
                        ib.insert_Book(); // Καλω τη μεθοδο οπου γινεται η καταχωρηση του βιβλιου
                    } else if (periodiko.isSelected()) {
                        newf1.dispose();  // κλεινω το frame επιλογης περιοδικου
                        ib.insert_Magazine(); // Καλω τη μεθοδο οπου γινεται η καταχωρηση του περιοδικου
                    }
                }
            });

        } else if (e.getSource() == search) { // Για την αναζητηση
            JFrame newf2 = new JFrame(".Επιλέξτε."); // Δημιουργια αντικειμενου Frame
            ButtonGroup G2 = new ButtonGroup(); // Εγγυαται οτι μπορει να επιλεγει μονο ενα κουμπι καθε φορα. 1 για Βιβλιο / 2 για Περιοδικο
            JLabel L2 = new JLabel("Επιλογή:");
            newf2.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
            newf2.setSize(420, 200);
            newf2.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            newf2.setLocationRelativeTo(null);
            newf2.setVisible(true);
            newf2.setLayout(null);
            // Δημιουργω τα αντικειμενα που ειναι επιλογη του χρηστη για βιβλιο η περιοδικο
            JRadioButton vivlio = new JRadioButton("Βιβλίο");
            JRadioButton periodiko = new JRadioButton("Περιοδικό");
            confirm = new JButton("OK"); // Κουμπι που θα με πηγαινει στην καταχωρηση βιβλιου η περιοδικου
            // Χρωματιζω και επεξεργαζομαι το κουμπι ΟΚ
            confirm.setFont(new Font("TimesRoman", Font.PLAIN, 13));
            confirm.setBackground(Color.WHITE);
            // Δινω διαστασεις
            vivlio.setBounds(120, 30, 100, 50);
            periodiko.setBounds(220, 30, 100, 50);
            confirm.setBounds(125, 90, 80, 30);
            L2.setBounds(20, 30, 150, 50);
            // Βαζω τα κουμπια και τις επιλογες μεσα στο frame
            newf2.add(vivlio);
            newf2.add(periodiko);
            newf2.add(confirm);
            newf2.add(L2);
            // βαζω τις πιλογες στον υποδοχεα
            G2.add(vivlio);
            G2.add(periodiko);

            // Επιλογη χρηστη για καταχωρηση βιβλιου η περιοδικου αντιστοιχα
            confirm.addActionListener(new ActionListener() {
                Search_B_M ib = new Search_B_M(); // Δημιουργω αντικειμενο τυπου κλασης Insert_B_M

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (vivlio.isSelected()) { // Αν το βιβλιο ειναι selected
                        newf2.dispose(); // κλεινω το frame επιλογης βιβλιου
                        ib.search_Book(); // Καλω τη μεθοδο αναζητησης βιβλιου
                    } else if (periodiko.isSelected()) {
                        newf2.dispose();  // κλεινω το frame επιλογης περιοδικου
                        ib.search_Periodically(); // Καλω τη μεθοδο αναζητησης περιοδικου
                    }
                }
            });

        } else if (e.getSource() == exit) {
            System.exit(0); // Εξοδος προγραμματος
        }
    }
}
