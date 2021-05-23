
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class ServerMain {

    public static void main(String[] args) {

        //Create and install a security manager
//        if (System.getSecurityManager() == null) {
//            System.setSecurityManager(new RMISecurityManager());
//        }
        try {

            Registry r = java.rmi.registry.LocateRegistry.createRegistry(1099);

            Naming.rebind("//localhost/Music", new LibraryDataBase());
            System.out.println("RMI Server is ready...");

        } catch (MalformedURLException | RemoteException e) {
            e.printStackTrace();
        }
    }
}
