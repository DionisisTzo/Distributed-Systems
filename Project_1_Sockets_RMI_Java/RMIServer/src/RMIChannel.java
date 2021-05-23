
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RMIChannel extends Remote {

    //========================FROM_SERVER_TO_CLIENT========================================
    //public void addClient(RMIClientChannel clientChannel) throws RemoteException;
    //public void removeClient(RMIClientChannel clientChannel) throws RemoteException;
    
    //========================FROM_CLIENT_TO_SERVER========================================
    public boolean register(User user) throws RemoteException;
    public User login(String loginName,String password) throws RemoteException;
    public boolean deleteUser(User user) throws RemoteException;
    
    public boolean addEvent(Event event) throws RemoteException;   
    public boolean deleteEvent(String title, String kind) throws RemoteException;
    public ArrayList<String> displayEvents() throws RemoteException;
    
    public int addOrder(Order order) throws RemoteException;
    public boolean deleteOrder(String userName, String title, String kind) throws RemoteException;
    public ArrayList<String> displayOrders(String userName) throws RemoteException;
}
