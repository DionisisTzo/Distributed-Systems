
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDataBase {

    /**
     * This method is user to add orders from user on dataBase and returns
     * boolean
     */
    public static int addOrder(Order order) {
        
        Connection orderConn = null;
        int tickets = EventDataBase.getTickets(order.getTitle(),order.getKind());
        
        // if the tickets are of the event are less than the client wants to make then the order cant be make
        if (tickets<order.getTicketsNum()){
            return -1;
        }
        
        try {
            Class.forName("org.sqlite.JDBC");
            orderConn = DriverManager.getConnection("jdbc:sqlite:Order.db");
            Statement orderStat = orderConn.createStatement();

            // This try is used to check if the database was created ot not
            try {
                orderStat.executeUpdate("CREATE TABLE IF NOT EXISTS `order` (username VARCHAR(50), title VARCHAR(50), kind VARCHAR(50), date VARCHAR(12), tickets INTEGER)");

                if (checkOrderDetails(order.getTitle(), order.getKind(), order.getDate().toString())) {
                    PreparedStatement preparedInsert = orderConn.prepareStatement("INSERT INTO `order` (username,title,kind,date,tickets) VALUES (?,?,?,?,?);");

                    preparedInsert.setString(1, order.getUserName());
                    preparedInsert.setString(2, order.getTitle());
                    preparedInsert.setString(3, order.getKind());
                    preparedInsert.setString(4, order.getDate().toString());
                    preparedInsert.setInt(5, order.getTicketsNum());
                    preparedInsert.addBatch();
                    preparedInsert.executeBatch();

                    System.out.println("The order succesfully registered.\n");
                    orderConn.close();
                    
                    // Update the tickets num of the current event of the database 
                    EventDataBase.updateTickets(order.getTitle(),order.getKind(), tickets - order.getTicketsNum());
                    
                    // return the cost. Multiply with how many tickets the client has made with the current cost event
                    return EventDataBase.getCost(order.getTitle(),order.getKind())*order.getTicketsNum();
                } else {
                    System.out.println("The " + order.getTitle() + " and " + order.getKind() + " you gave doesn't exists.\n");
                    return -1;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                orderConn.close();
                System.out.println("The DataBase failed to create.\n");
                return -1;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    private static boolean checkOrderDetails(String title, String kind, String date) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection eventConn = DriverManager.getConnection("jdbc:sqlite:Events.db");

            // Check if the title and kind that client gave exist on the database order
            PreparedStatement check = eventConn.prepareStatement("SELECT COUNT(title) FROM `event` WHERE `title` = ? AND `kind` = ? AND `date` = ?;");
            check.setString(1, title);
            check.setString(2, kind);
            check.setString(3, date);
            ResultSet rs = check.executeQuery();

            int result = rs.getInt(1); // The result is one
            eventConn.close();
            if (result != 1) { // if there is no value on the result. Not 1, Return false Not exist
                return false;
            } else {
                return true;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * This method is used to delete orders from database and return boolean
     */
    public static boolean deleteOrder(String userName, String title, String kind) {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection orderConn = DriverManager.getConnection("jdbc:sqlite:Order.db");

            try {
                // Check if the title and kind exist of the order from the current user and return the SUM of the tickets from the orders
                PreparedStatement preparedCheck = orderConn.prepareStatement("SELECT SUM(tickets) FROM `order` WHERE `username`=? AND `title`=? AND `kind`=?;");
                preparedCheck.setString(1, userName);
                preparedCheck.setString(2, title);
                preparedCheck.setString(3, kind);
                ResultSet rs = preparedCheck.executeQuery();

                int result = rs.getInt(1);

                if (result == 0) { // if there is no result
                    System.out.println("The " + title + " and " + kind + " you gave doesn't exists.\n");
                    orderConn.close();
                    return false;
                } else {
                    // Delete the order from the DB 
                    PreparedStatement preparedDelete = orderConn.prepareStatement("DELETE FROM `order` WHERE `username`=? AND `title`=? AND `kind`=?;");
                    preparedDelete.setString(1, userName);
                    preparedDelete.setString(2, title);
                    preparedDelete.setString(3, kind);
                    preparedDelete.executeUpdate();
                    orderConn.close();
                    
                    // Get the tickets from the event database 
                    int tickets = EventDataBase.getTickets(title, kind); 
                    // Update the tickets column of the event DataBase 
                    // (tickets + result = how many tickets left on the DB + the sum of the orders client has made from the current event)
                    EventDataBase.updateTickets(title, kind, tickets + result);
                    return true;
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDataBase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    /**
     * This method is used to display the orders that user have made
     */
    public static ArrayList<String> displayUserOrders(String userName) {

        ArrayList<String> userOrders = new ArrayList<>();
        try {

            Class.forName("org.sqlite.JDBC");
            Connection orderConn = DriverManager.getConnection("jdbc:sqlite:Order.db");

            try {

                PreparedStatement prepareInsert = orderConn.prepareStatement("SELECT * FROM `order` WHERE `username` = ?;");
                prepareInsert.setString(1, userName);
                ResultSet rs = prepareInsert.executeQuery();

                int orderCount = 0;
                while (rs.next()) {
                    orderCount++;
                    String order = (orderCount + ") USER: " + rs.getString(1)
                            + " TITLE: " + rs.getString(2)
                            + " KIND: " + rs.getString(3)
                            + " DATE: " + rs.getString(4)
                            + " TICKETS: " + String.valueOf(rs.getInt(5)) + "\n");

                    userOrders.add(order);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("There is no table Order to display");
            }

            orderConn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return userOrders;
    }
    
    
}
