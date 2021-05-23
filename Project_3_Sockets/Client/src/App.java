
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends JFrame implements ActionListener {

    private JButton new_announce, delete_update, read_announce, exit;
    private JButton login, register, confirm_log, confirm_reg, button_area, confirm_date, confirm;
    private JTextField text_login_name, text_register_name, text_register_pass, text_login_pass, date1, date2;
    private JLabel lab1, lab2, lab3, login_name, login_pass, register_name, register_pass, labann1, labann2;
    ObjectInputStream input;
    ObjectOutputStream output;
    Socket clientlogin;
    // Αν ο χρηστης είναι συνδεδεμενος και το όνομα του
    boolean clientlogged = false;
    String clientname;

    public App() {
        //===============================MENU_FRAME_APP========================================================//
        super(".Ανακοινώσεις.");

        setSize(450, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        lab1 = new JLabel("Καλώς Ήρθατε");
        lab1.setFont(new Font("Courier", Font.PLAIN, 25));
        lab1.setBackground(Color.BLACK);
        lab1.setBounds(130, 230, 190, 40);

        new_announce = new JButton("Νέα Ανακοίνωση");
        new_announce.setFont(new Font("Courier", Font.BOLD, 12));
        new_announce.setBackground(Color.WHITE);
        new_announce.setBounds(130, 290, 170, 30);

        delete_update = new JButton("Ενημέρωση/Διαγραφή");
        delete_update.setFont(new Font("Courier", Font.BOLD, 12));
        delete_update.setBackground(Color.WHITE);
        delete_update.setBounds(130, 330, 170, 30);

        read_announce = new JButton("Ανάγνωση");
        read_announce.setFont(new Font("Courier", Font.BOLD, 12));
        read_announce.setBackground(Color.WHITE);
        read_announce.setBounds(130, 370, 170, 30);

        exit = new JButton("'Εξοδος");
        exit.setFont(new Font("Courier", Font.BOLD, 12));
        exit.setBackground(Color.WHITE);
        exit.setBounds(130, 410, 170, 30);

        this.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
        Container pane = getContentPane();
        pane = new FrameIcon(); // Δημιουργω αντικειμενο της κλασεις FrameIcon και το βαζω μεσα στο Container

        // Εισαγω τα κουμπια στο container
        pane.add(lab1);
        pane.add(new_announce);
        pane.add(delete_update);
        pane.add(read_announce);
        pane.add(exit);

        // Ενεργοποιω τα αντικειμενα στο να ακουσουν στην εντολη (πατημα) που θα κανει ο χρηστης
        // και απο παρακατω γινεται η υλοποιηση των κουμπιων
        new_announce.addActionListener(this);
        delete_update.addActionListener(this);
        read_announce.addActionListener(this);
        exit.addActionListener(this);

        connectSocket();
        pane.setBackground(Color.lightGray);
        pane.setLayout(null); // Αρχικοποιω των Container με μηδεν για να μπουν τα κουμπια και η εικονα στη θεση που οριζω
        setContentPane(pane); // Τοποθέτηση υποδοχέα στο πλαίσιο
        pane.revalidate(); // Ανανεωνω το frame για να εμφανιστει διοτι αλλιως πρεπει να πειραχτει χειροκινητα

        //===============================ΕΠΙΛΟΓΗ ΕΞΟΔΟΥ ΑΠΟ ΤΗΝ ΕΦΑΡΜΟΓΗ==========================//
        // Στην περιπτωση που ο χρηστης κλεισει την εφαρμογη ο server παραμενει ανοιχτος περιμενοντας αλλον χρηστη να κανει login η register
        // ή την αναγνωση των ανακοινωσεων
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                JFrame frame = (JFrame) e.getSource();
                int result = JOptionPane.showConfirmDialog(frame, "θέλετε σίγουρα να κλείσετε την εφαρμογή;", "Έξοδος εφαρμογής", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    disconnectSocket();
                } else {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    //===============================ΣΥΝΔΕΣΗ ΣΤΟΝ SERVER==========================//
    private void connectSocket() {
        try {
            // Socket για συνδεση χρηστη στο server
            clientlogin = new Socket("127.0.0.1", 4444);
            System.out.println("Connecting to " + clientlogin.getInetAddress() + " and port " + clientlogin.getPort());
            System.out.println("Local Address :" + clientlogin.getLocalAddress() + " Port :" + clientlogin.getLocalPort());

            // Input θα διαβαζει οτι ερχεται απο τον server
            input = new ObjectInputStream(clientlogin.getInputStream());
            // Output θα γραφει οτι θελει να στειλει στον server
            output = new ObjectOutputStream(clientlogin.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //===============================ΑΠΟΣΥΝΔΕΣΗ ΑΠΟ ΤΟΝ SERVER==========================//
    private void disconnectSocket() {
        try {
            // Εξοδος εφαρμογης
            output.writeObject(new Response("exit"));
            output.flush();
            output.close();
            input.close();
            clientlogin.close(); // Κλεινω το Socket 
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //===============================FRAME_LOGIN_REGISTER==========================//
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == new_announce) { // Νεα ανακοινωση
            if (clientlogged) { // Αν ο χρηστης εχει συνδεθη ηδη μια φορα δεν χρειαζεται να ξανα συνδεθη και δημιουργει κατευθειαν νεα ανακοινωση
                new_announcement();
            } else {
                JFrame account = new JFrame(".Λογαριασμός.");
                account.setSize(280, 210);
                account.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                account.setLocationRelativeTo(null);
                account.setVisible(true);
                account.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));

                login = new JButton("Σύνδεση");
                login.setFont(new Font("Courier", Font.BOLD, 12));
                login.setBackground(Color.WHITE);
                login.setBounds(45, 45, 170, 30);

                register = new JButton("Εγγραφή");
                register.setFont(new Font("Courier", Font.BOLD, 12));
                register.setBackground(Color.WHITE);
                register.setBounds(45, 90, 170, 30);

                account.add(login);
                account.add(register);
                account.setLayout(null);

                //===============================LOGIN========================================================//
                login.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        JFrame new_login = new JFrame(".Σύνδεση.");
                        account.dispose();
                        new_login.setSize(350, 210);
                        new_login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        new_login.setLocationRelativeTo(null);
                        new_login.setVisible(true);
                        new_login.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));

                        lab2 = new JLabel("ΣΥΝΔΕΣΗ");
                        lab2.setFont(new Font("Courier", Font.PLAIN, 20));
                        lab2.setBackground(Color.BLACK);
                        lab2.setBounds(120, 10, 170, 30);

                        login_name = new JLabel("Όνομα: ");
                        login_name.setFont(new Font("Courier", Font.BOLD, 15));
                        login_name.setBackground(Color.BLACK);
                        login_name.setBounds(40, 45, 130, 30);

                        login_pass = new JLabel("Κωδικός: ");
                        login_pass.setFont(new Font("Courier", Font.BOLD, 15));
                        login_pass.setBackground(Color.BLACK);
                        login_pass.setBounds(40, 85, 130, 30);

                        text_login_name = new JTextField();
                        text_login_name.setFont(new Font("Courier", Font.PLAIN, 15));
                        text_login_name.setBounds(115, 50, 180, 25);

                        text_login_pass = new JTextField();
                        text_login_pass.setBounds(115, 90, 180, 25);

                        confirm_log = new JButton("ΟΚ");
                        confirm_log.setFont(new Font("Courier", Font.BOLD, 12));
                        confirm_log.setBackground(Color.WHITE);
                        confirm_log.setBounds(115, 125, 60, 25);

                        new_login.add(lab2);
                        new_login.add(text_login_name);
                        new_login.add(text_login_pass);
                        new_login.add(login_name);
                        new_login.add(login_pass);
                        new_login.add(confirm_log);

                        new_login.setLayout(null);

                        //===============================OK_LOGIN========================================================//
                        confirm_log.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                if (text_login_name.getText().equals("") || text_login_pass.getText().equals("")) {
                                    JOptionPane.showMessageDialog(null, "Δεν συμπληρώθηκαν όλα τα πεδία!", ".Αποτυχία.", JOptionPane.WARNING_MESSAGE);
                                } else {
                                    try {
                                        output.writeObject(new Response("login"));
                                        output.flush();

                                        MyClient clog = new MyClient(text_login_name.getText(), text_login_pass.getText());
                                        output.writeObject(clog); // Στελνω το αντικειμενο cl στον server
                                        output.flush();

                                        // Αν ειναι σωστα τοτε επιτυχη συνδεση αλλιως ξανα προσπαθησε 
                                        // Ο server επιστρεφει λογικη τιμη
                                        boolean var = ((Answer) input.readObject()).isCorrect();

                                        if (!var) {
                                            JOptionPane.showMessageDialog(null, "Το Username ή Password είναι λάθος.", ".User-Pass.", JOptionPane.WARNING_MESSAGE);
                                        } else if (var) {
                                            JOptionPane.showMessageDialog(null, "Επιτυχής σύνδεση!");
                                            clientlogged = true; // Αν συνδεθει ο χρηστης τοτε θα μπορει παρακατω να κανει την ανακοινωση που θελει
                                            clientname = text_login_name.getText();
                                            // Μετα την συνδεση του χρηστη εμφανιζει στο frame και το ονομα του επανω αριστερα για το ποιος χρηστης ειναι
                                            // συνδεδεμενος εκεινη την στιγμη
                                            setTitle(".Ανακοινώσεις. | " + text_login_name.getText());
                                            text_login_name.setText(null);
                                            text_login_pass.setText(null);
                                        }

                                    } catch (IOException e) {
                                        System.out.println("Connection Failed!");
                                    } catch (ClassNotFoundException ex) {
                                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    new_login.dispose();
                                    // Νεο Frame που θα εχει μεσα την καταχωρηση της ανακοινωσης
                                    if (clientlogged) {
                                        new_announcement();
                                    }
                                }

                            }
                        });
                    }
                });
            }

            //===============================REGISTER========================================================//
            register.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    JFrame new_register = new JFrame(".Εγγραφή.");
                    //account.dispose();
                    new_register.setSize(350, 210);
                    new_register.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    new_register.setLocationRelativeTo(null);
                    new_register.setVisible(true);
                    new_register.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));

                    lab3 = new JLabel("ΕΓΓΡΑΦΗ");
                    lab3.setFont(new Font("Courier", Font.PLAIN, 20));
                    lab3.setBackground(Color.BLACK);
                    lab3.setBounds(120, 10, 170, 30);

                    register_name = new JLabel("Όνομα: ");
                    register_name.setFont(new Font("Courier", Font.BOLD, 15));
                    register_name.setBackground(Color.BLACK);
                    register_name.setBounds(40, 45, 130, 30);

                    register_pass = new JLabel("Κωδικός: ");
                    register_pass.setFont(new Font("Courier", Font.BOLD, 15));
                    register_pass.setBackground(Color.BLACK);
                    register_pass.setBounds(40, 85, 130, 30);

                    text_register_name = new JTextField();
                    text_register_name.setFont(new Font("Courier", Font.PLAIN, 15));
                    text_register_name.setBounds(115, 50, 180, 25);

                    text_register_pass = new JTextField();
                    text_register_pass.setBounds(115, 90, 180, 25);

                    confirm_reg = new JButton("ΟΚ");
                    confirm_reg.setFont(new Font("Courier", Font.BOLD, 12));
                    confirm_reg.setBackground(Color.WHITE);
                    confirm_reg.setBounds(115, 125, 60, 25);

                    new_register.add(lab3);
                    new_register.add(text_register_name);
                    new_register.add(text_register_pass);
                    new_register.add(register_name);
                    new_register.add(register_pass);
                    new_register.add(confirm_reg);

                    new_register.setLayout(null);

                    //===============================OK_REGISTER========================================================//
                    confirm_reg.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            if (text_register_name.getText().equals("") || text_register_pass.getText().equals("")) {
                                JOptionPane.showMessageDialog(null, "Δεν συμπληρώθηκαν όλα τα πεδία!", ".Αποτυχία.", JOptionPane.WARNING_MESSAGE);
                            } else {
                                try {
                                    output.writeObject(new Response("register"));
                                    output.flush();

                                    MyClient creg = new MyClient(text_register_name.getText(), text_register_pass.getText());
                                    output.writeObject(creg); // Στελνω το αντικειμενο creg(client_register) στον server
                                    output.flush();

                                    // Ο server επιστρεφει λογικη τιμη
                                    boolean var = ((Answer) input.readObject()).isCorrect();

                                    if (!var) {
                                        JOptionPane.showMessageDialog(null, "Το Username υπάρχει ήδη.", ".User-Pass.", JOptionPane.WARNING_MESSAGE);
                                    } else if (var) {
                                        JOptionPane.showMessageDialog(null, "Επιτυχής εγγραφή!");
                                        clientlogged = true;
                                        clientname = text_register_name.getText();
                                        setTitle(".Ανακοινώσεις. | " + text_register_name.getText());
                                        text_register_name.setText(null);
                                        text_register_pass.setText(null);
                                    }

                                } catch (IOException e) {
                                    System.out.println("Connection Failed!");
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                new_register.dispose();
                                // Νεο Frame που θα εχει μεσα την καταχωρηση της ανακοινωσης
                                if (clientlogged) {
                                    new_announcement();
                                }
                            }

                        }
                    });
                }
            });
            //===============================DELETE_UPADATE========================================================//
        } else if (e.getSource() == delete_update) { // Ενημερωση και διαγραφη ανακοινωσης
            // Αν ο χρηστης κανει ηδη μια φορα συνδεση και παει να κανει update να μην ξανα ζητηση login
            JFrame del_edit = new JFrame(".Επιλογή."); // Δημιουργια αντικειμενου Frame
            ButtonGroup G1 = new ButtonGroup(); // Εγγυαται οτι μπορει να επιλεγει μονο ενα κουμπι καθε φορα. 1 για διαγραφη / 2 για επεξεργασια
            JLabel L1 = new JLabel("Επιλέξτε:");
            del_edit.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
            del_edit.setSize(420, 200);
            del_edit.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            del_edit.setLocationRelativeTo(null);
            del_edit.setVisible(true);
            del_edit.setLayout(null);
            // Δημιουργω τα αντικειμενα
            JRadioButton delete = new JRadioButton("Διαγραφή");
            JRadioButton edit = new JRadioButton("Επεξεργασία");
            confirm = new JButton("OK"); // Κουμπι που θα με πηγαινει στην καταχωρηση βιβλιου η περιοδικου
            // Χρωματιζω και επεξεργαζομαι το κουμπι ΟΚ
            confirm.setFont(new Font("TimesRoman", Font.PLAIN, 13));
            confirm.setBackground(Color.WHITE);
            // Δινω διαστασεις
            delete.setBounds(120, 30, 100, 50);
            edit.setBounds(220, 30, 100, 50);
            confirm.setBounds(125, 90, 80, 30);
            L1.setBounds(20, 30, 150, 50);
            // Βαζω τα κουμπια και τις επιλογες μεσα στο frame
            del_edit.add(delete);
            del_edit.add(edit);
            del_edit.add(confirm);
            del_edit.add(L1);
            // βαζω τις πιλογες (διαγραφη-επεξεργασια) μεσα στον υποδοχεα
            // Εγγυαται οτι μπορει να επιλεγει μονο ενα κουμπι καθε φορα. Διαγραφή-επεξεργασια
            G1.add(delete);
            G1.add(edit);

            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    // διαγραφη ανακοινωσης
                    if (delete.isSelected()) {
                        del_edit.dispose();
                        try {
                            output.writeObject(new Response("delete"));
                            output.flush();

                        } catch (IOException ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        // επεξεργασια ανακοινωσης
                    } else if (edit.isSelected()) {
                        del_edit.dispose();
                        try {
                            output.writeObject(new Response("edit"));
                            output.flush();

                        } catch (IOException ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
           

            //===============================READ_ANNOUCEMENT========================================================//
        } else if (e.getSource() == read_announce) {

            JFrame date = new JFrame(".Ημερομηνία.");
            date.setSize(350, 200);
            date.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            date.setLocationRelativeTo(null);
            date.setVisible(true);
            date.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));

            labann1 = new JLabel("ΑΠΟ");
            labann1.setFont(new Font("Courier", Font.BOLD, 15));
            labann1.setBackground(Color.BLACK);
            labann1.setBounds(58, 30, 130, 30);

            labann2 = new JLabel("ΕΩΣ");
            labann2.setFont(new Font("Courier", Font.BOLD, 15));
            labann2.setBackground(Color.BLACK);
            labann2.setBounds(230, 30, 130, 30);

            date1 = new JTextField();
            date1.setFont(new Font("Courier", Font.PLAIN, 15));
            date1.setBounds(20, 60, 110, 25);

            date2 = new JTextField();
            date2.setFont(new Font("Courier", Font.PLAIN, 15));
            date2.setBounds(190, 60, 110, 25);

            confirm_date = new JButton("ΟΚ");
            confirm_date.setFont(new Font("Courier", Font.BOLD, 12));
            confirm_date.setBackground(Color.WHITE);
            confirm_date.setBounds(130, 110, 60, 25);

            date.add(labann1);
            date.add(labann2);
            date.add(date1);
            date.add(date2);
            date.add(confirm_date);
            date.setLayout(null);
            date.revalidate();

            confirm_date.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    date.dispose();
                    JFrame ann_frame = new JFrame(".ΑΝΑΚΟΙΝΩΣΕΙΣ.");
                    ann_frame.setSize(600, 400);
                    ann_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ann_frame.setLocationRelativeTo(null);
                    ann_frame.setVisible(true);
                    ann_frame.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));

                    // Περιοχη κειμενου
                    JTextArea area_ann = new JTextArea(20, 40);
                    try {
                        // Αναγνωση ανακοινωσης
                        output.writeObject(new Response("search"));
                        output.flush();
                        // Διαβαζει τα objects που ερχονται απο τον server και περνανε στην λιστα με τις ανακοινωσεις
                        ArrayList<Announce> anns = ((Answer) input.readObject()).anns;
                        int i = 0;
                        // Οσο ειναι το μεγεθος της λιστας να μου εμφανισει τις ανακοινωσεις που υπαρχουν σε ενα JtextArea 
                        for (Announce a : anns) {
                            i++;
                            area_ann.append(i + ") " + a.toString() + "\n");
                        }

                    } catch (IOException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ann_frame.getContentPane().add(new JScrollPane(area_ann));
                    ann_frame.revalidate();
                }
            });

            //===============================EXIT_PROGRAM========================================================//
        } else if (e.getSource() == exit) {
            disconnectSocket();
        }
    }

    //===============================ANNOUNCEMENT_METHOD========================================================//
    // Frame για την καταχωρηση της ανακοινωσης και αποστολη στον server
    // Η μεθοδος αυτη καλειται και οταν γινεται login και οταν γινεται register
    public void new_announcement() {
        JFrame frame = new JFrame(".Νέα Ανακοίνωση.");
        frame.setSize(400, 230);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.GRAY));
        // Ετικετα
        JLabel label = new JLabel("ΑΝΑΚΟΙΝΩΣΗ");
        label.setFont(new Font("Courier", Font.PLAIN, 20));
        // Περιοχη κειμενου
        JTextArea area_ann = new JTextArea(6, 30);
        JScrollPane scroll = new JScrollPane(area_ann);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // Κουμπι
        button_area = new JButton("Καταχώρηση ανακοίνωσης");
        button_area.setFont(new Font("Courier", Font.BOLD, 12));
        button_area.setBackground(Color.WHITE);

        frame.setLayout(new FlowLayout());
        frame.add(label);
        frame.add(scroll);
        frame.add(button_area);
        frame.revalidate();

        button_area.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Στελνει ανακοινωση στον server
                try {
                    output.writeObject(new Response("announce"));
                    output.flush();
                    
                    Announce ann = new Announce(area_ann.getText(), clientname, LocalDate.now());
                    output.writeObject(ann); // Στελνω το αντικειμενο ann στον server
                    output.flush();

                    boolean var = ((Answer) input.readObject()).isCorrect();
                    if (!var) {
                        JOptionPane.showMessageDialog(null, "Η ανακοίνωση δεν καταχωρήθηκε.", ".Announce.", JOptionPane.WARNING_MESSAGE);
                    } else if (var) {
                        JOptionPane.showMessageDialog(null, "Επιτυχής δημιουργία ανακοίνωσης!");
                        area_ann.setText(null);
                    }

                } catch (IOException e) {
                    System.out.println("Connection Failed!");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}
