
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EventDataBase {

    /**
     * This method is used to add events to DataBase Event and return boolean
     */
    public static boolean addEvent(Event event) {

        try {
            // Loading JDBC programm
            Class.forName("org.sqlite.JDBC");
            // Connect to the DB and create one if not exists
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");
            // Create Object statment to send commands to the DB 
            Statement eventStat = eventConn.createStatement();

            // This try is used to check if the database was created ot not
            try {
                eventStat.executeUpdate("CREATE TABLE IF NOT EXISTS `event` (title VARCHAR(50), kind VARCHAR(50), date VARCHAR(12), time VARCHAR(5), tickets INT, cost INT, UNIQUE(title,kind))");
                
                // This try is to insert the values of the object Event that the admin gives
                try {
                    PreparedStatement preparedInsert = eventConn.prepareStatement("INSERT INTO `event` (title,kind,date,time,tickets,cost) VALUES (?,?,?,?,?,?);");

                    preparedInsert.setString(1, event.getTitle());
                    preparedInsert.setString(2, event.getKind());
                    preparedInsert.setString(3, event.getDate().toString()); 
                    preparedInsert.setString(4, String.valueOf(event.getTimeEvent()));
                    preparedInsert.setInt(5, event.getTickets());
                    preparedInsert.setInt(6, event.getCost());
                    preparedInsert.addBatch(); // Add the commands to a list of command
                    preparedInsert.executeBatch(); // Execute the List

                    System.out.println("The event succesfully registered!\n");
                    eventConn.close(); // Close the connection 
                    return true;

                } catch (SQLException e) {
                    System.out.println("The event already exists");
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("The DataBase failed to create.\n");
                return false;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EventDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * This method is used to delete events from database and return boolean
     */
    public static boolean deleteEvent(String title, String kind) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");

            try {
                // Check if the title and kind that admin gave exists on the DB Event (Title, kind Unique)
                PreparedStatement check = eventConn.prepareStatement("SELECT COUNT(title) FROM `event` WHERE `title` = ? AND `kind` = ?;");

                check.setString(1, title);
                check.setString(2, kind);
                ResultSet rs = check.executeQuery();

                int result = rs.getInt(1); // the result is one

                if (result != 1) { // if there is nothing returned from the DB 
                    System.out.println("The " + title + " and " + kind + " you gave doesn't exists.\n");
                    eventConn.close();
                    return false;
                } else { // Delete the event
                    PreparedStatement deleteEvent = eventConn.prepareStatement("DELETE FROM `event` WHERE `title` = ? AND `kind` = ?;");
                    deleteEvent.setString(1, title);
                    deleteEvent.setString(2, kind);
                    deleteEvent.executeUpdate();
                    eventConn.close();
                    return true;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(EventDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * This method is used to display all events that the admin has made, so
     * user can see the events and make the order
     * @return
     */
    public static ArrayList<String> displayEvents() {

        ArrayList<String> allEvents = new ArrayList<>();
        try {

            Class.forName("org.sqlite.JDBC");
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");
            Statement eventStat = eventConn.createStatement();
            
            try {
                ResultSet eventData = eventStat.executeQuery("SELECT * FROM `event`");

                int eventCount = 0;
                while (eventData.next()) {

                    eventCount++; // Count how many events exist
                    String event = (eventCount + ") TITLE: " + eventData.getString(1)
                            + " KIND: " + eventData.getString(2)
                            + " DATE: " + eventData.getString(3)
                            + " TIME: " + eventData.getString(4)
                            + " TICKETS: " + String.valueOf(eventData.getInt(5))
                            + " COST-€: " + String.valueOf(eventData.getInt(6)) + "\n");

                    allEvents.add(event); // Adding the events from the DB to arrayList and return it for display
                }

                eventConn.close();

            } catch (SQLException e) {
                System.out.println("There is no table Event to display");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return allEvents;
    }

    
    
    /**
     * This method is used to return all the tickets witch exist on the current event
     * @param title
     * @param kind
     * @return
     */
    public static int getTickets(String title, String kind) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");

            PreparedStatement check = eventConn.prepareStatement("SELECT `tickets` FROM `event` WHERE `title` = ? AND `kind` = ?;");
            check.setString(1, title);
            check.setString(2, kind);
            ResultSet rs = check.executeQuery();

            int result = rs.getInt(1);
            eventConn.close();
            return result;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }
    
    /**
     * This method is used to return the cost of the current event 
     * @param title
     * @param kind
     * @return 
     */
    public static int getCost(String title, String kind) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");

            PreparedStatement check = eventConn.prepareStatement("SELECT `cost` FROM `event` WHERE `title` = ? AND `kind` = ?;");
            check.setString(1, title);
            check.setString(2, kind);
            ResultSet rs = check.executeQuery();

            int result = rs.getInt(1);
            eventConn.close();
            return result;

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     * This method is used to update the column of the ticket of the current event, witch client make the order
     * @param title
     * @param kind
     * @param tickets 
     */
    public static void updateTickets(String title, String kind, int tickets) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");

            PreparedStatement check = eventConn.prepareStatement("UPDATE `event` SET `tickets` = ? WHERE `title` = ? AND `kind` = ?;");
            check.setInt(1, tickets);
            check.setString(2, title);
            check.setString(3, kind);
            check.executeUpdate();

            eventConn.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
