
import java.io.Serializable;

public class Book implements Serializable {
    private String titlos,isbn,sygrafeas,etos,eidos,selides,perigrafh,thesi;
    private static final long serialversionUID = 1L;
    
    public Book(String titlos, String isbn, String sygrafeas, String etos, String eidos, String selides, String perigrafh, String thesi){
        this.titlos = titlos;
        this.isbn = isbn;
        this.sygrafeas = sygrafeas;
        this.etos = etos;
        this.eidos = eidos;
        this.selides = selides;
        this.perigrafh = perigrafh;
        this.thesi = thesi;
    }

    public String getTitlos() {
        return titlos;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSygrafeas() {
        return sygrafeas;
    }

    public String getEtos() {
        return etos;
    }

    public String getEidos() {
        return eidos;
    }

    public String getSelides() {
        return selides;
    }

    public String getPerigrafh() {
        return perigrafh;
    }

    public String getThesi() {
        return thesi;
    }

    @Override
    public String toString() {
        return "Τίτλος: " + getTitlos() + "\n" +
               "ISBN: " + getIsbn() + "\n" +
               "Συγγραφέας: " + getSygrafeas() + "\n" +
               "Έτος Έκδοσης: " + getEtos() + "\n" +
               "Έιδος: " + getEidos() + "\n" +
               "Σελίδες: " + getSelides() + "\n" +
               "Περιγραφή: " + getPerigrafh() + "\n" +
               "Θέση βιβλίου: " + getThesi();
    }
}
