
import java.io.Serializable;

public class Magazine implements Serializable {
    private String titlos,num_tomou,num_teyxous,etos,thematikh,selides,thesi;
    private static final long serialversionUID = 1L;
    
    public Magazine(String titlos, String num_tomou, String num_teyxous, String etos, String thematikh, String selides, String thesi){
        this.titlos = titlos;
        this.num_tomou = num_tomou;
        this.num_teyxous = num_teyxous;
        this.etos = etos;
        this.thematikh = thematikh;
        this.selides = selides;
        this.thesi = thesi;
    }

    public String getTitlos() {
        return titlos;
    }

    public String getNum_tomou() {
        return num_tomou;
    }

    public String getNum_teyxous() {
        return num_teyxous;
    }

    public String getEtos() {
        return etos;
    }

    public String getThematikh() {
        return thematikh;
    }

    public String getSelides() {
        return selides;
    }

    public String getThesi() {
        return thesi;
    }

    @Override
    public String toString() {
        return "Τίτλος: " + getTitlos() + "\n" +
               "Αριθμός τόμου: " + getNum_tomou() + "\n" +
               "Αριθμός τεύχους: " + getNum_teyxous() + "\n" +
               "Έτος Έκδοσης: " + getEtos() + "\n" +
               "Θεματική: " + getThematikh() + "\n" +
               "Σελίδες: " + getSelides() + "\n" +
               "Θέση περιοδικού: " + getThesi();
    }
}
