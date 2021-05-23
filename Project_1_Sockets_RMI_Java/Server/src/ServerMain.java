
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.*;

public class ServerMain {

    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(4444);
            System.out.println("Server connection beggin...");
            System.out.println("Local Address: " + server.getInetAddress() + " Port: " + server.getLocalPort());
            boolean success;

            Socket rmiSocket = server.accept(); //  We accept the request from the client to the server 

            ObjectOutputStream objectOutput = new ObjectOutputStream(rmiSocket.getOutputStream());
            ObjectInputStream objectInput = new ObjectInputStream(rmiSocket.getInputStream());
            
            //=============================INPUT-OUTPUT========================//
            while (true) {
                System.out.println("Waiting for the mode...");

                String respond;
                respond = (String) objectInput.readObject(); 

                switch (respond) {
                    case "addEvent": {
                        Event newEvent = (Event) objectInput.readObject();
                        success = EventDataBase.addEvent(newEvent);
                        objectOutput.writeObject(success);
                        System.out.println("Admin add event");
                        break;
                    }
                    case "deleteEvent": {
                        String title = (String) objectInput.readObject();
                        String kind = (String) objectInput.readObject();
                        success = EventDataBase.deleteEvent(title, kind);
                        objectOutput.writeObject(success);
                        System.out.println("Admin delete event");
                        break;
                    }
                    case "displayEvent": {
                        ArrayList<String> allEvents = EventDataBase.displayEvents();
                        objectOutput.writeObject(allEvents);
                        System.out.println("Display events");
                        break;
                    }
                    case "addOrder": {
                        Order newOrder = (Order) objectInput.readObject();
                        int cost = OrderDataBase.addOrder(newOrder);
                        objectOutput.writeObject(cost);
                        objectOutput.flush();
                        //int tickets = EventDataBase.getTickets(newOrder.getTitle(), newOrder.getKind());
                        //objectOutput.writeObject(tickets<10);
                        //objectOutput.flush();
                        
                        System.out.println("User add order");
                        break;
                    }
                    case "deleteOrder": {
                        String userName = (String) objectInput.readObject();
                        String title = (String) objectInput.readObject();
                        String kind = (String) objectInput.readObject();
                        success = OrderDataBase.deleteOrder(userName, title, kind);
                        objectOutput.writeObject(success);
                        System.out.println("User delete order");
                        break;
                    }
                    case "displayOrder": {
                        String userName = (String) objectInput.readObject();
                        ArrayList<String> userOrders = OrderDataBase.displayUserOrders(userName);
                        objectOutput.writeObject(userOrders);
                        System.out.println("User display orders");
                        break;
                    }
                    default:
                        System.out.println("Value Error");
                        break;
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerMain.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
