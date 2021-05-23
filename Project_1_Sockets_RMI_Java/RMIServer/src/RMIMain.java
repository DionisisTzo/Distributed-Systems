
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class RMIMain {

    public static void main(String[] args) {

        try {
            // Γινεται η καταχωρηση του αναφορικου ονοματος του απομακρυσμενου αντικειμενου 
            // ετσι ωστε ο καθε πελατης να αποκτησει την αναφορα προς το επιθυμητο απομακρυσμενο αντικειμενο
            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);
            // We declare in witch address the obejct is and waits for some call 
            Naming.rebind("//localhost/Authentication", new FileManager());

            // Create Admin 
            FileManager.createAdmin();
            System.out.println("Waiting for user...");

            
        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }

    }

}
