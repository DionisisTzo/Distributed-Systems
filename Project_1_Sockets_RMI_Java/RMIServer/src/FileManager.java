
import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

// Using UnicastRemoteObject we gives the opportunity to the class to create objects witch is gonna be used for the connection
public class FileManager extends UnicastRemoteObject implements RMIChannel {

    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    private static Socket serverConnect;

    //private ArrayList<RMIClientChannel> clients; // All the logged clients
    
    public FileManager() throws RemoteException {
        super();
        //clients = new ArrayList<>(); // Initialize ArrayList
        connectSocket(); 
    }

    // Start the connection with the Main Server using sockets and TCP protocol
    private void connectSocket() {
        try {

            serverConnect = new Socket("127.0.0.1", 4444);
            input = new ObjectInputStream(serverConnect.getInputStream());
            output = new ObjectOutputStream(serverConnect.getOutputStream());

            System.out.println("Connecting to: " + serverConnect.getInetAddress() + " and port: " + serverConnect.getPort());
            System.out.println("Local Address: " + serverConnect.getLocalAddress() + " Port: " + serverConnect.getLocalPort());

        } catch (IOException ex) {
            Logger.getLogger(RMIMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //===============================================USER_FILES========================================================
    /**
     * This method is used to register users in file UserDetails as objects and
     * return boolean
     */
    public boolean register(User user) throws RemoteException {

        File file = new File("UserDetails.txt");
        ObjectOutputStream out = null;
        FileOutputStream user_Reg = null;

        try {
            if (file.exists()) {
                if (!checkIfUserExists(user.getLoginName())) { // if the user name not exist on the file
                    user_Reg = new FileOutputStream(file, true);
                    out = new ObjectOutputStream(user_Reg) {
                        public void writeStreamHeader() {
                        }
                    };
                    out.writeObject(user);
                    return true;
                }
            } else {
                user_Reg = new FileOutputStream(file);
                out = new ObjectOutputStream(user_Reg);
                out.writeObject(user);
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    user_Reg.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; // Register failed
    }

    //----------------------------------------------------------------------------------------------------------------
    /**
     * This method is used to find if the username and the password that user
     * gives exists in the file and return User
     */
    public User login(String loginName, String password) throws RemoteException {

        ObjectInputStream in = null;

        try {
            if (Files.exists(Paths.get("UserDetails.txt"))) {
                in = new ObjectInputStream(new FileInputStream("UserDetails.txt"));
                User tempUser = null;

                while (true) {
                    try {
                        tempUser = (User) in.readObject();
                        if (tempUser != null) {

                            if (tempUser.getLoginName().equals(loginName) && tempUser.getPassword().equals(password)) {
                                System.out.println(loginName + " Logged.");
                                return tempUser;
                            }
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
            } else {
                System.out.println("UserDetails FILE not found.");
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(loginName + " Failed to log in!");
        return null; // If login failed return null
    }

    //----------------------------------------------------------------------------------------------------------------
    /**
     * This method is used to delete user from file and return boolean
     */
    public boolean deleteUser(User user) throws RemoteException {

        ArrayList<User> storedUsers = new ArrayList<>();
        boolean found = false;
        ObjectInputStream in = null;

        try {
            if (Files.exists(Paths.get("UserDetails.txt"))) {
                in = new ObjectInputStream(new FileInputStream("UserDetails.txt"));

                while (true) {
                    try {
                        User tempUser = (User) in.readObject(); // Read user objects from file
                        if (!tempUser.equals(user)) { // if User is not the user i use from the method parameter
                            storedUsers.add(tempUser); // Then add the users from the file to the arraylist
                        } else {
                            found = true; // If the users pair then make found true
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (EOFException e) {
                        break;
                    }
                }
            } else {
                System.out.println("UserDetails FILE not found.");
            }

            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (found) { // if true
                Files.deleteIfExists(Paths.get("UserDetails.txt")); // delete old user file
                for (User removeUser : storedUsers) {
                    register(removeUser); // Re-register the users from the arraylist to file
                }
                System.out.println("The delete was successful.");
                return true;
            } else {
                System.out.println("The delete failed");
            }

        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    //----------------------------------------------------------------------------------------------------------------
    /**
     * This methods is used to find if the login name already exists in the file
     * so the user can register or not and return boolean
     */
    private boolean checkIfUserExists(String username) {
        ObjectInputStream in = null;

        try {
            if (Files.exists(Paths.get("UserDetails.txt"))) {
                in = new ObjectInputStream(new FileInputStream("UserDetails.txt"));
                while (true) {
                    try {
                        User user = (User) in.readObject(); // Read object user from file
                        if (user.getLoginName().equals(username)) { // if login name exists return true
                            return true;
                        }
                    } catch (EOFException e) {
                        break;
                    }
                }
            } else {
                System.out.println("UserDetails FILE not found.");
            }

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false; // if not exists return false
    }

    //===============================================ADMIN_FILES========================================================
    /**
     * This method writes admin to file ones after the RMIServer starts running.
     *
     * @param admin
     */
    public static void createAdmin() {

        User admin = new User("admin", "1234");
        ObjectOutputStream out = null;

        if (!Files.exists(Paths.get("UserDetails.txt"))) {
            try {
                out = new ObjectOutputStream(new FileOutputStream("UserDetails.txt"));
                out.writeObject(admin);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //===============================================SOCKETS========================================================
    @Override
    public boolean addEvent(Event event) throws RemoteException {

        try {
            output.writeObject("addEvent");
            output.flush();
            output.writeObject(event);
            output.flush();

            return (boolean) input.readObject();

        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteEvent(String title, String kind) throws RemoteException {

        try {
            output.writeObject("deleteEvent");
            output.flush();
            output.writeObject(title);
            output.flush();
            output.writeObject(kind);
            output.flush();

            return (boolean) input.readObject();

        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public ArrayList<String> displayEvents() throws RemoteException {

        try {
            output.writeObject("displayEvent");
            output.flush();

            return (ArrayList<String>) input.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    synchronized public int addOrder(Order order) throws RemoteException {

        try {
            output.writeObject("addOrder");
            output.flush();
            output.writeObject(order);
            output.flush();

            return (int) input.readObject();
            
//         for(RMIClientChannel client:clients){
//             client.offer(order.getTitle(),order.getKind());
//         }

        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    @Override
    synchronized public boolean deleteOrder(String userName, String title, String kind) throws RemoteException {

        try {
            output.writeObject("deleteOrder");
            output.flush();
            output.writeObject(userName);
            output.flush();
            output.writeObject(title);
            output.flush();
            output.writeObject(kind);
            output.flush();

            return (boolean) input.readObject();

        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public ArrayList<String> displayOrders(String userName) throws RemoteException {

        try {
            output.writeObject("displayOrder");
            output.flush();
            output.writeObject(userName);
            output.flush();

            return (ArrayList<String>) input.readObject();

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //===============================================RMI_CLIENT_CHANNEL========================================================
    
    
    /**
     * This method is used to add the logged clients to the arrayList
     * @param clientChannel
     * @throws RemoteException 
     */
//    @Override
//    public void addClient(RMIClientChannel clientChannel) throws RemoteException {
//        clients.add(clientChannel);
//    }
    
    
     /**
     * This method is used to remove the logged clients from the arrayList
     * @param clientChannel     
     * @throws RemoteException 
     */
//    @Override
//    public void removeClient(RMIClientChannel clientChannel) throws RemoteException {
//        clients.remove(clientChannel);
//    }

}
