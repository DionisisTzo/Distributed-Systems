
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface LibraryInterface extends Remote{ // All remoting methods 
     public boolean createAlbum(Album album) throws RemoteException;
     public boolean createSong(int albumID, Song song) throws RemoteException;
     public boolean deleteAlbum(int albumID) throws RemoteException;
     public boolean deleteSong(int albumID, String title, String interpreter) throws RemoteException;
     public boolean updateAlbum(String desc, String kind, int year, int totalS, int albumID) throws RemoteException;
     public boolean updateSong(String title, String interpreter, int duration, int albumID, String oldtitle, String oldinterpreter) throws RemoteException;
     public ArrayList<String> displayAlbums() throws RemoteException;
     public ArrayList<String> displaySongs(int albumID) throws RemoteException;
     public int checkID(int albumID) throws RemoteException;
     public boolean checkSong(int albumID, String title, String interpreter) throws RemoteException;
}
