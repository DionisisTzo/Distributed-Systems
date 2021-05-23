
import javax.swing.*;
import java.io.*;

public class Files {

    // Δημιουργω ενα αρχειο book οπου εκει θα γινεται η εγγραφη των αντικειμενων
    public void writeBook(Book book) throws IOException {
        // Δημιουργια αρχειου 
        File f = new File("InsertBook.txt"); 
        ObjectOutputStream insertB;
        FileOutputStream insertB_F;
        try {
            if (f.exists()) { // Αν ο φακελος υπαρχει
                // Να γινει append διοτι το header εχει ηδη γραφτει μια φορα και θελουμε τα επομενα αντικειμενα να γραφτουν απο κατω (Να μην ξανα παρει header)...
                //... writeStreamHeader
                insertB_F = new FileOutputStream(f, true); 
                // Προσθηκη αντικειμενων στο αρχειο (stream)
                insertB = new ObjectOutputStream(insertB_F) {
                    @Override
                    public void writeStreamHeader() {
                    }
                };
            } else {
                // Δημιουργια αρχειου με ονομα InsertBook από την αρχή περνοντας για πρωτη φορα την πρωτη καταχωρηση 
                insertB_F = new FileOutputStream(f);
                insertB = new ObjectOutputStream(insertB_F);
            }
            // Γραφει τα αντικειμενα που ειναι της κλασεις book (παραμετρος της μεθοδου) και λογο Serializable 
            //... θα μπορουν να εγγραφουν σε streams
            insertB.writeObject(book); // Παιρνει ως παραμετρο το αντικειμενο που θελουμε να γραψουμε στο stream
            insertB.flush();
            insertB_F.flush();
            insertB.close();
            insertB_F.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error with specified file");
            ex.printStackTrace();
        }
    }

    // Δημιουργω ενα αρχειο Magazine οπου εκει θα γινεται η εγγραφη αντικειμενων
    public void writeMagazine(Magazine per) throws IOException {
        File f = new File("InsertMagazine.txt");
        FileOutputStream insertP_F;
        ObjectOutputStream insertP;
        try {
            if(f.exists()){
                insertP_F = new FileOutputStream(f,true);
                insertP = new ObjectOutputStream(insertP_F) {
                    @Override
                    public void writeStreamHeader() {
                    }
                };
            }else
            {
                insertP_F = new FileOutputStream(f);
                insertP = new ObjectOutputStream(insertP_F);
            }
            insertP.writeObject(per);
            insertP.flush();
            insertP_F.flush();
            insertP.close();
            insertP_F.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Error with specified file");
            ex.printStackTrace();
        }
    }

    // Κανουμε ελεγχο και επιστρεφουμε το αντικειμενο που ειχαμε γραψει προηγουμενος στο αρχειο book
    public void readBook(String title, String isbn) throws IOException {

        FileInputStream searchB = new FileInputStream("InsertBook.txt");
        ObjectInputStream searchOB = new ObjectInputStream(searchB);
        StringBuilder sb = new StringBuilder(""); // Δημιουργια αντικειμενου StringBuilder
        int i = 0; // Μετρηση των βιβλιων
        
        try {
            while (true) {
                // Να διαβαζει τα αντικειμενα απο το αρχειο και να γινονται cast της κλασης Book 
                Book b = (Book) searchOB.readObject();
                // Μπορει ενα ονομα βιβλιου να υπαρχει 2 φορες μεσα στο αρχειο οποτε εμφανιζει συμφωνα με το ονομα 1 ή περισσοτερα παρομοια βιβλια
                // Και ξεχωριστα το isbn το καθε βιβλιου που ειναι μοναδικο
                if ((!title.equals("") && b.getTitlos().contains(title)) || b.getIsbn().equals(isbn)) {
                    sb.append("Βιβλίο: " + (i + 1) + "\n");
                    sb.append(b.toString() + "\n");
                    sb.append("========================\n");
                    i++;
                }
            }

        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            if (i != 0) { // Αν το επομενο βιβλιο που ψαχνει δεν εχει φτασει στο τελος (Αρα το βρει) τοτε...
                JOptionPane.showMessageDialog(null, sb); //... Εκτυπωσε πληροφοριες βιβλιου
            } else {
                // Αλλιως αν φτασει στο τελος και δεν εχει βρεθει το βιβλιο 
                JOptionPane.showMessageDialog(null, "Δεν υπάρχει η καταχώρηση του βιβλίου", ".Αποτυχία.", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("File reading is done");
        } finally { // Εκτελειται ανεξαρτητα απο το αν θα υπαρχει exception η οχι (κλεισιμο αρχειου)
            try {
                searchB.close();
                searchOB.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Κανουμε ελεγχο και επιστρεφουμε το αντικειμενο που ειχαμε γραψει προηγουμενος στο αρχειο Magazine
    public void readMagazine(String title, String year) throws IOException {

        FileInputStream searchP = new FileInputStream("InsertMagazine.txt");
        ObjectInputStream searchOP = new ObjectInputStream(searchP);
        StringBuilder sm = new StringBuilder(""); // Δημιουργια αντικειμενου StringBuilder
        int i = 0; // Μετρηση των περιοδικων
        
        try {
            while (true) {
                Magazine p = (Magazine) searchOP.readObject();
                // Μπορει ενα ονομα περιοδικου να υπαρχει 2 φορες μεσα στο αρχειο οποτε εμφανιζει συμφωνα με το ονομα 1 ή περισσοτερα παρομοια περιοδικα
                // Και το ετος οπου καποια περιοδικα μπορει να ειναι ιδιου ετους ή διαφορετικο
                if ((!title.equals("") && p.getTitlos().contains(title)) || p.getEtos().equals(year)) {
                    sm.append("Περιοδικό: " + (i + 1) + "\n");
                    sm.append(p.toString() + "\n");
                    sm.append("========================\n");
                    i++;
                }
            }
        } catch (ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {
            if (i != 0) { 
                JOptionPane.showMessageDialog(null, sm); 
            } else {
                JOptionPane.showMessageDialog(null, "Δεν υπάρχει η καταχώρηση του περιοδικού", ".Αποτυχία.", JOptionPane.ERROR_MESSAGE);
            }
            System.out.println("File reading is done");
        } finally {
            try {
                searchP.close();
                searchOP.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
