
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Menu extends JFrame {

    //-----------------------------------------FRAME-------------------------------------------------------------------------
    private JPanel adminPanel, userPanel, addEventPanel, deleteEventPanel, addOrderPanel, deleteOrderPanel;
    private JLabel welcome;
    private JButton userDisplayEvents, userDeleteOrder, userMakeOrder, userDisplayOrder, userDeleteAccount, userLogOut;
    private JButton adminDisplayEvents, adminAddEvent, adminDeleteEvent, adminLogOut;
    //-----------------------------------------ADMIN-------------------------------------------------------------------------
    private JButton adminConfirmAddEv, adminCancelAddEv, adminAddNewEvent, adminConfirmDeleteEv, adminCancelDeleteEv;
    private JLabel adminTitleLabel, adminKindLabel, adminDateLabel, adminTimeLabel, adminTicketLabel, adminCostLabel, adminDeleteTitleLabel, adminDeleteKindLabel;
    private JTextField adminTitleField, adminKindField, adminDateField, adminTimeField, adminTicketField, adminCostField, adminDeleteTitleField, adminDeleteKindField;
    //------------------------------------------USER------------------------------------------------------------------------
    private JButton userConfirmAddOr, userCancelAddOr, userAddNewOrder, userConfirmDeleteOr, userCancelDeleteOr;
    private JLabel userTitleLabel, userKindLabel, userDateLabel, userTicketLabel, userDeleteTitleLabel, userDeleteKindLabel;
    private JTextField userTitleField, userKindField, userDateField, userTicketField, userDeleteTitleField, userDeleteKindField;

    private Container pane;

    public Menu(User currentUser) {
        super("Menu Page");

        //----------------FRAME------------------------------
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pane = getContentPane();
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#F2DB9D")));

        // If the checkbox is true then admin is going to login so the additional frame / The same for the user if false then additional frame
        if (currentUser.getIsAdmin()) {
            adminMenu(currentUser);
        } else {
            userMenu(currentUser);
        }

        setContentPane(pane);
        setVisible(true);
        pane.revalidate();
        setResizable(false);
    }

    //====================================ADMIN_MENU_TOOLS================================================================
    
    /**
     * This method is used to create the admin frame with his components and Action-Listener
     */
    private void adminMenu(User admin) {

        //--------------INITIALIZE_ATTRIBUTES-------------------
        welcome = new JLabel("Welcome, " + admin.getLoginName(), JLabel.CENTER);
        adminAddEvent = new JButton("Add Event");
        adminDisplayEvents = new JButton("Display Events");
        adminDeleteEvent = new JButton("Delete Event");
        adminLogOut = new JButton("LogOut");
        JPanel buttonPanel = new JPanel();
        adminPanel = new JPanel();

        //--------------DESIGN-------------------
        welcome.setFont(new Font("Courier", Font.BOLD, 30));
        welcome.setBackground(Color.black);
        welcome.setBounds(WIDTH, 80, 550, 50);

        adminAddEvent.setFont(new Font("Courier", Font.BOLD, 15));
        adminAddEvent.setBackground(Color.decode("#F2DB9D"));
        adminDisplayEvents.setFont(new Font("Courier", Font.BOLD, 15));
        adminDisplayEvents.setBackground(Color.decode("#F2DB9D"));
        adminDeleteEvent.setFont(new Font("Courier", Font.BOLD, 15));
        adminDeleteEvent.setBackground(Color.decode("#F2DB9D"));
        adminLogOut.setFont(new Font("Courier", Font.BOLD, 15));
        adminLogOut.setBackground(Color.decode("#F2DB9D"));

        buttonPanel.setBackground(Color.decode("#BA4261"));
        buttonPanel.setBounds(WIDTH, 140, 550, 45);
        adminPanel.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        adminAddEvent.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setAdminAddEvent();} });
        adminDisplayEvents.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { displayEvents(); }});
        adminDeleteEvent.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setAdminDeleteEvent(); } });
        adminLogOut.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new HomePage(); } });

        //--------------ADD_COMPONENTS-------------------------
        buttonPanel.add(adminAddEvent);
        buttonPanel.add(adminDisplayEvents);
        buttonPanel.add(adminDeleteEvent);
        buttonPanel.add(adminLogOut);

        adminPanel.add(welcome);
        adminPanel.add(buttonPanel);

        adminPanel.setLayout(null);
        pane.add(adminPanel);
        pane.revalidate();
    }

    /**
     * This method is used for the admin to add e new event
     */
    private void setAdminAddEvent() {

        setTitle("Add New Event");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES-------------------
        adminConfirmAddEv = new JButton("Confirm");
        adminCancelAddEv = new JButton("Back");
        adminAddNewEvent = new JButton("Add New +");
        adminTitleLabel = new JLabel("Title");
        adminKindLabel = new JLabel("Kind");
        adminDateLabel = new JLabel("Event Date");
        adminTimeLabel = new JLabel("Event Time");
        adminTicketLabel = new JLabel("Tickets");
        adminCostLabel = new JLabel("Cost - €");
        adminTitleField = new JTextField(20);
        adminKindField = new JTextField(20);
        adminDateField = new JTextField(20);
        adminTimeField = new JTextField(20);
        adminTicketField = new JTextField(20);
        adminCostField = new JTextField(20);
        addEventPanel = new JPanel();

        //--------------DESIGN/TITLE---------------------
        adminTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminTitleLabel.setForeground(Color.decode("#F2DB9D"));
        adminTitleLabel.setBounds(30, 40, 60, 30);

        adminTitleField.setFont(new Font("Courier", Font.BOLD, 15));
        adminTitleField.setBounds(30, 70, 140, 25);

        //--------------KIND-----------------------
        adminKindLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminKindLabel.setForeground(Color.decode("#F2DB9D"));
        adminKindLabel.setBounds(190, 40, 60, 30);

        adminKindField.setFont(new Font("Courier", Font.BOLD, 15));
        adminKindField.setBounds(190, 70, 140, 25);

        //--------------EVENT_DATE-----------------
        adminDateLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminDateLabel.setForeground(Color.decode("#F2DB9D"));
        adminDateLabel.setBounds(350, 40, 120, 30);

        adminDateField.setFont(new Font("Courier", Font.BOLD, 15));
        adminDateField.setBounds(350, 70, 140, 25);

        adminDateField.setText("Day/Month/Year");
        adminDateField.setForeground(Color.LIGHT_GRAY);
        adminDateField.addFocusListener(new FocusListener() {
            private boolean showingPlaceholder2 = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder2 || adminDateField.getText().equals("Day/Month/Year")) {
                    showingPlaceholder2 = false;
                    adminDateField.setForeground(Color.BLACK);
                    adminDateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (adminDateField.getText().isEmpty()) {
                    adminDateField.setText("Day/Month/Year");
                    adminDateField.setForeground(Color.LIGHT_GRAY);
                    showingPlaceholder2 = true;

                }
            }
        });

        //--------------EVENT_TIME-----------------
        adminTimeLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminTimeLabel.setForeground(Color.decode("#F2DB9D"));
        adminTimeLabel.setBounds(30, 100, 120, 30);

        adminTimeField.setFont(new Font("Courier", Font.BOLD, 15));
        adminTimeField.setBounds(30, 130, 140, 25);

        adminTimeField.setText("Hour:minutes");
        adminTimeField.setForeground(Color.LIGHT_GRAY);
        adminTimeField.addFocusListener(new FocusListener() {
            private boolean showingPlaceholder1 = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder1 || adminTimeField.getText().equals("Hour:minutes")) {
                    showingPlaceholder1 = false;
                    adminTimeField.setForeground(Color.BLACK);
                    adminTimeField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (adminTimeField.getText().isEmpty()) {
                    adminTimeField.setText("Hour:minutes");
                    adminTimeField.setForeground(Color.LIGHT_GRAY);
                    showingPlaceholder1 = true;
                }
            }
        });

        //--------------TICKETS--------------------
        adminTicketLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminTicketLabel.setForeground(Color.decode("#F2DB9D"));
        adminTicketLabel.setBounds(190, 100, 120, 30);

        adminTicketField.setFont(new Font("Courier", Font.BOLD, 15));
        adminTicketField.setBounds(190, 130, 140, 25);
        
        adminTicketField.setText("Integer");
        adminTicketField.setForeground(Color.LIGHT_GRAY);
        adminTicketField.addFocusListener(new FocusListener() {
            private boolean showingPlaceholder3 = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder3 || adminTicketField.getText().equals("Integer")) {
                    showingPlaceholder3 = false;
                    adminTicketField.setForeground(Color.BLACK);
                    adminTicketField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (adminTicketField.getText().isEmpty()) {
                    adminTicketField.setText("Integer");
                    adminTicketField.setForeground(Color.LIGHT_GRAY);
                    showingPlaceholder3 = true;

                }
            }
        });

        //--------------COST-----------------------
        adminCostLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminCostLabel.setForeground(Color.decode("#F2DB9D"));
        adminCostLabel.setBounds(350, 100, 120, 30);

        adminCostField.setFont(new Font("Courier", Font.BOLD, 15));
        adminCostField.setBounds(350, 130, 140, 25);
        
        adminCostField.setText("Integer");
        adminCostField.setForeground(Color.LIGHT_GRAY);
        adminCostField.addFocusListener(new FocusListener() {
            private boolean showingPlaceholder4 = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder4 || adminCostField.getText().equals("Integer")) {
                    showingPlaceholder4 = false;
                    adminCostField.setForeground(Color.BLACK);
                    adminCostField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (adminCostField.getText().isEmpty()) {
                    adminCostField.setText("Integer");
                    adminCostField.setForeground(Color.LIGHT_GRAY);
                    showingPlaceholder4 = true;

                }
            }
        });

        //--------------BUTTONS--------------------
        adminConfirmAddEv.setFont(new Font("Courier", Font.BOLD, 15));
        adminConfirmAddEv.setBackground(Color.decode("#F2DB9D"));
        adminConfirmAddEv.setBounds(70, 250, 180, 30);

        adminCancelAddEv.setFont(new Font("Courier", Font.BOLD, 15));
        adminCancelAddEv.setBackground(Color.decode("#F2DB9D"));
        adminCancelAddEv.setBounds(270, 250, 180, 30);

        adminAddNewEvent.setFont(new Font("Courier", Font.BOLD, 15));
        adminAddNewEvent.setBackground(Color.decode("#F2DB9D"));
        adminAddNewEvent.setBounds(200, 190, 120, 30);

        addEventPanel.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        adminConfirmAddEv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                // If the textfields are empty else make the event
                if (adminTitleField.getText().equals("") || adminKindField.getText().equals("") || adminDateField.getText().equals("")
                        || adminTimeField.getText().equals("") || adminTicketField.getText().equals("") || adminCostField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {

                    try {
                        
                        Event addEvent = new Event(adminTitleField.getText(), adminKindField.getText(), 
                                new UsefulDate(adminDateField.getText()), 
                                LocalTime.parse(adminTimeField.getText()),
                                Integer.parseInt(adminTicketField.getText()), 
                                Integer.parseInt(adminCostField.getText()));

                        RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");
                        
                        if (mode.addEvent(addEvent)) { // The method returns boolean
                            JOptionPane.showMessageDialog(null, "The Event added Successully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Event add Failed. Try again!", "Event Error", JOptionPane.ERROR_MESSAGE);
                        }

                    }catch(DateTimeParseException ex){
                        JOptionPane.showMessageDialog(null, "Invalid hour input!!","Invalid Error", JOptionPane.WARNING_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid tickets or cost input!!","Invalid Error", JOptionPane.WARNING_MESSAGE);
                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        ex.printStackTrace();
                    } 
                }
            }
        });

        adminCancelAddEv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(adminPanel);
                pane.repaint();
                pane.revalidate();
                setTitle("Menu Page");
            }
        });

        adminAddNewEvent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                adminTitleField.setText("");
                adminKindField.setText("");
                adminDateField.setText("");
                adminTimeField.setText("");
                adminTicketField.setText("");
                adminCostField.setText("");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        addEventPanel.add(adminConfirmAddEv);
        addEventPanel.add(adminCancelAddEv);
        addEventPanel.add(adminAddNewEvent);
        addEventPanel.add(adminTitleLabel);
        addEventPanel.add(adminTitleField);
        addEventPanel.add(adminKindLabel);
        addEventPanel.add(adminKindField);
        addEventPanel.add(adminDateLabel);
        addEventPanel.add(adminDateField);
        addEventPanel.add(adminTimeLabel);
        addEventPanel.add(adminTimeField);
        addEventPanel.add(adminTicketLabel);
        addEventPanel.add(adminTicketField);
        addEventPanel.add(adminCostLabel);
        addEventPanel.add(adminCostField);

        addEventPanel.setLayout(null);
        pane.add(addEventPanel);
        pane.repaint();
        pane.revalidate();
    }

    /**
     * This method is used for the admin to delete event
     */
    private void setAdminDeleteEvent() {

        setTitle("Delete Event");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES-------------------
        adminConfirmDeleteEv = new JButton("Confirm");
        adminCancelDeleteEv = new JButton("Back");
        adminDeleteTitleLabel = new JLabel("Insert Title");
        adminDeleteKindLabel = new JLabel("Insert Kind");
        adminDeleteTitleField = new JTextField(20);
        adminDeleteKindField = new JTextField(20);
        deleteEventPanel = new JPanel();

        //--------------DESIGN--------------------------------
        adminDeleteTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminDeleteTitleLabel.setForeground(Color.decode("#F2DB9D"));
        adminDeleteTitleLabel.setBounds(110, 60, 120, 30);

        adminDeleteTitleField.setFont(new Font("Courier", Font.BOLD, 15));
        adminDeleteTitleField.setBounds(110, 90, 140, 25);

        adminDeleteKindLabel.setFont(new Font("Courier", Font.BOLD, 15));
        adminDeleteKindLabel.setForeground(Color.decode("#F2DB9D"));
        adminDeleteKindLabel.setBounds(270, 60, 120, 30);

        adminDeleteKindField.setFont(new Font("Courier", Font.BOLD, 15));
        adminDeleteKindField.setBounds(270, 90, 140, 25);

        adminConfirmDeleteEv.setFont(new Font("Courier", Font.BOLD, 15));
        adminConfirmDeleteEv.setBackground(Color.decode("#F2DB9D"));
        adminConfirmDeleteEv.setBounds(130, 230, 120, 30);

        adminCancelDeleteEv.setFont(new Font("Courier", Font.BOLD, 15));
        adminCancelDeleteEv.setBackground(Color.decode("#F2DB9D"));
        adminCancelDeleteEv.setBounds(270, 230, 120, 30);

        deleteEventPanel.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        adminConfirmDeleteEv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (adminDeleteTitleField.getText().equals("") || adminDeleteKindField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {

                    try {
                        RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");

                        if (mode.deleteEvent(adminDeleteTitleField.getText(), adminDeleteKindField.getText())) {
                            JOptionPane.showMessageDialog(null, "The Event deleted Successully!");
                            pane.removeAll();
                            pane.add(adminPanel);
                            pane.repaint();
                            pane.revalidate();
                            setTitle("Menu Page");
                        } else {
                            JOptionPane.showMessageDialog(null, "The Event deletion failed!", "Delete Error", JOptionPane.ERROR_MESSAGE);
                            adminDeleteTitleField.setText("");
                            adminDeleteKindField.setText("");
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        adminCancelDeleteEv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(adminPanel);
                pane.repaint();
                pane.revalidate();
                setTitle("Menu Page");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        deleteEventPanel.add(adminConfirmDeleteEv);
        deleteEventPanel.add(adminCancelDeleteEv);
        deleteEventPanel.add(adminDeleteTitleLabel);
        deleteEventPanel.add(adminDeleteKindLabel);
        deleteEventPanel.add(adminDeleteTitleField);
        deleteEventPanel.add(adminDeleteKindField);

        deleteEventPanel.setLayout(null);
        pane.add(deleteEventPanel);
        pane.repaint();
        pane.revalidate();
    }

    //=======================================DISPLAY_METHODS_FOR_EVENTS & ORDERS============================================
    /**
     * This method is used to display all events in a Text Area
     */
    private void displayEvents() {

        JFrame events = new JFrame("All Events");
        events.setSize(630, 400);
        events.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        events.setLocationRelativeTo(null);
        events.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.decode("#F2DB9D")));

        JTextArea area_Event = new JTextArea(40, 40);
        JScrollPane scroll = new JScrollPane(area_Event);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        try {
            RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");

            ArrayList<String> allEvents = mode.displayEvents(); // Put to the arrayList all events witch came from the server

            for (String event : allEvents) { // run the list and display to TextArea the events
                area_Event.append(event);
            }

        } catch (RemoteException | MalformedURLException | NotBoundException ex) {
            ex.printStackTrace();
        }

        area_Event.setEditable(false);
        events.add(scroll);
        events.revalidate();
        events.setVisible(true);
    }

    /**
     * This method is used to display all orders in a Text Area
     */
    private void displayOrders(String userName) {

        JFrame events = new JFrame("All " + userName + " Orders");
        events.setSize(630, 400);
        events.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        events.setLocationRelativeTo(null);
        events.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.decode("#F2DB9D")));

        JTextArea area_Orders = new JTextArea(40, 40);
        JScrollPane scroll = new JScrollPane(area_Orders);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        try {
            RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");

            ArrayList<String> allOrders = mode.displayOrders(userName);// Put to the arrayList the orders that the current user has made

            for (String order : allOrders) { // Run the list and display the orders to the Text Area
                area_Orders.append(order);
            }

        } catch (RemoteException | MalformedURLException | NotBoundException ex) {
            ex.printStackTrace();
        }

        area_Orders.setEditable(false);
        events.add(scroll);
        events.revalidate();
        events.setVisible(true);
    }

    //====================================USER_MENU_TOOLS=================================================================
    /**
     * This method is used to create user frame with his components and Action-Listener
     */
    private void userMenu(User user) {
        //--------------INITIALIZE_ATTRIBUTES-------------------
        welcome = new JLabel("Welcome, " + user.getLoginName());
        userDisplayEvents = new JButton("Display Events");
        userMakeOrder = new JButton("Make Order");
        userDeleteAccount = new JButton("Delete Account");
        userDisplayOrder = new JButton("Display Orders");
        userDeleteOrder = new JButton("Delete Order");
        userLogOut = new JButton("Log Out");
        userPanel = new JPanel();

        //--------------DESIGN-------------------
        welcome.setFont(new Font("Courier", Font.BOLD, 30));
        welcome.setBackground(Color.black);
        welcome.setBounds(155, 50, 550, 60);

        userDisplayEvents.setFont(new Font("Courier", Font.BOLD, 15));
        userDisplayEvents.setBackground(Color.decode("#F2DB9D"));
        userDisplayEvents.setBounds(30, 140, 140, 30);

        userMakeOrder.setFont(new Font("Courier", Font.BOLD, 15));
        userMakeOrder.setBackground(Color.decode("#F2DB9D"));
        userMakeOrder.setBounds(190, 140, 140, 30);

        userDeleteAccount.setFont(new Font("Courier", Font.BOLD, 15));
        userDeleteAccount.setBackground(Color.decode("#F2DB9D"));
        userDeleteAccount.setBounds(350, 140, 150, 30);

        userDisplayOrder.setFont(new Font("Courier", Font.BOLD, 15));
        userDisplayOrder.setBackground(Color.decode("#F2DB9D"));
        userDisplayOrder.setBounds(30, 200, 140, 30);

        userDeleteOrder.setFont(new Font("Courier", Font.BOLD, 15));
        userDeleteOrder.setBackground(Color.decode("#F2DB9D"));
        userDeleteOrder.setBounds(190, 200, 140, 30);

        userLogOut.setFont(new Font("Courier", Font.BOLD, 15));
        userLogOut.setBackground(Color.decode("#F2DB9D"));
        userLogOut.setBounds(350, 200, 150, 30);

        userPanel.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        userDisplayEvents.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { displayEvents(); } });
        userMakeOrder.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setUserMakeOrder(user.getLoginName()); } });

        userDeleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");

                    int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete your account?", "Delete Account", JOptionPane.YES_NO_OPTION);

                    if (dialogResult == JOptionPane.YES_OPTION) {
                        if (mode.deleteUser(user)) {
                            JOptionPane.showMessageDialog(null, "Your " + user.getLoginName() + " Account deleted successfuly!");
                            dispose();
                            new HomePage();
                        } else {
                            JOptionPane.showMessageDialog(null, "Delete account error!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        userDisplayOrder.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { displayOrders(user.getLoginName()); } });
        userDeleteOrder.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { setUserDeleteOrder(user.getLoginName()); } });
        userLogOut.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new HomePage(); } });

        //--------------ADD_COMPONENTS-------------------------
        userPanel.add(welcome);
        userPanel.add(userDisplayEvents);
        userPanel.add(userMakeOrder);
        userPanel.add(userDeleteAccount);
        userPanel.add(userDisplayOrder);
        userPanel.add(userDeleteOrder);
        userPanel.add(userLogOut);

        userPanel.setLayout(null);
        pane.add(userPanel);
        pane.revalidate();
    }

    /**
     * This Method is used for the user to delete the order he maid
     */
    private void setUserDeleteOrder(String userName) {

        setTitle("Delete Order");
        pane.removeAll();
        //--------------INITIALIZE_ATTRIBUTES-------------------
        userConfirmDeleteOr = new JButton("Confirm");
        userCancelDeleteOr = new JButton("Back");
        userDeleteTitleLabel = new JLabel("Insert Title");
        userDeleteKindLabel = new JLabel("Insert Kind");
        userDeleteTitleField = new JTextField(20);
        userDeleteKindField = new JTextField(20);
        deleteOrderPanel = new JPanel();

        //--------------DESIGN--------------------------------
        userDeleteTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userDeleteTitleLabel.setForeground(Color.decode("#F2DB9D"));
        userDeleteTitleLabel.setBounds(110, 60, 120, 30);

        userDeleteTitleField.setFont(new Font("Courier", Font.BOLD, 15));
        userDeleteTitleField.setBounds(110, 90, 140, 25);

        userDeleteKindLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userDeleteKindLabel.setForeground(Color.decode("#F2DB9D"));
        userDeleteKindLabel.setBounds(270, 60, 120, 30);

        userDeleteKindField.setFont(new Font("Courier", Font.BOLD, 15));
        userDeleteKindField.setBounds(270, 90, 140, 25);

        userConfirmDeleteOr.setFont(new Font("Courier", Font.BOLD, 15));
        userConfirmDeleteOr.setBackground(Color.decode("#F2DB9D"));
        userConfirmDeleteOr.setBounds(130, 230, 120, 30);

        userCancelDeleteOr.setFont(new Font("Courier", Font.BOLD, 15));
        userCancelDeleteOr.setBackground(Color.decode("#F2DB9D"));
        userCancelDeleteOr.setBounds(270, 230, 120, 30);

        deleteOrderPanel.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        userConfirmDeleteOr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (userDeleteTitleField.getText().equals("") || userDeleteKindField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {

                    try {
                        RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");

                        if (mode.deleteOrder(userName, userDeleteTitleField.getText(), userDeleteKindField.getText())) {
                            JOptionPane.showMessageDialog(null, "Your order deleted successfuly!");
                            pane.removeAll();
                            pane.add(userPanel);
                            pane.repaint();
                            pane.revalidate();
                            setTitle("Menu Page");
                        } else {
                            JOptionPane.showMessageDialog(null, "The deletion order failed!", "Delete Error", JOptionPane.WARNING_MESSAGE);
                            userDeleteTitleField.setText("");
                            userDeleteKindField.setText("");
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        
        userCancelDeleteOr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(userPanel);
                pane.repaint();
                pane.revalidate();
                setTitle("Menu Page");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        deleteOrderPanel.add(userConfirmDeleteOr);
        deleteOrderPanel.add(userCancelDeleteOr);
        deleteOrderPanel.add(userDeleteTitleLabel);
        deleteOrderPanel.add(userDeleteKindLabel);
        deleteOrderPanel.add(userDeleteTitleField);
        deleteOrderPanel.add(userDeleteKindField);

        deleteOrderPanel.setLayout(null);
        pane.add(deleteOrderPanel);
        pane.repaint();
        pane.revalidate();
    }

    private void setUserMakeOrder(String userName) {

        setTitle("Make New Order");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES-------------------
        userConfirmAddOr = new JButton("Confirm");
        userCancelAddOr = new JButton("Cancel");
        userAddNewOrder = new JButton("Add New +");
        userTitleLabel = new JLabel("Title");
        userKindLabel = new JLabel("Kind");
        userDateLabel = new JLabel("Event Date");
        userTicketLabel = new JLabel("Tickets");
        userTitleField = new JTextField(20);
        userKindField = new JTextField(20);
        userDateField = new JTextField(20);
        userTicketField = new JTextField(20);
        addOrderPanel = new JPanel();

        //--------------DESIGN/TITLE-------------------
        userTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userTitleLabel.setForeground(Color.decode("#F2DB9D"));
        userTitleLabel.setBounds(110, 40, 120, 30);

        userTitleField.setFont(new Font("Courier", Font.BOLD, 15));
        userTitleField.setBounds(110, 70, 140, 25);

        //--------------KIND-----------------------
        userKindLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userKindLabel.setForeground(Color.decode("#F2DB9D"));
        userKindLabel.setBounds(270, 40, 120, 30);

        userKindField.setFont(new Font("Courier", Font.BOLD, 15));
        userKindField.setBounds(270, 70, 140, 25);

        //--------------EVENT_DATE-----------------
        userDateLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userDateLabel.setForeground(Color.decode("#F2DB9D"));
        userDateLabel.setBounds(110, 100, 120, 30);

        userDateField.setFont(new Font("Courier", Font.BOLD, 15));
        userDateField.setBounds(110, 130, 140, 25);

        userDateField.setText("Day/Month/Year");
        userDateField.setForeground(Color.LIGHT_GRAY);
        userDateField.addFocusListener(new FocusListener() {
            private boolean showingPlaceholder1 = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder1 || userDateField.getText().equals("Day/Month/Year")) {
                    showingPlaceholder1 = false;
                    userDateField.setForeground(Color.BLACK);
                    userDateField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userDateField.getText().isEmpty()) {
                    userDateField.setText("Day/Month/Year");
                    userDateField.setForeground(Color.LIGHT_GRAY);
                    showingPlaceholder1 = true;

                }
            }
        });

        //--------------TICKETS--------------------
        userTicketLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userTicketLabel.setForeground(Color.decode("#F2DB9D"));
        userTicketLabel.setBounds(270, 100, 120, 30);

        userTicketField.setFont(new Font("Courier", Font.BOLD, 15));
        userTicketField.setBounds(270, 130, 140, 25);
        
        userTicketField.setText("Integer");
        userTicketField.setForeground(Color.LIGHT_GRAY);
        userTicketField.addFocusListener(new FocusListener() {
            private boolean showingPlaceholder2 = true;

            @Override
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder2 || userTicketField.getText().equals("Integer")) {
                    showingPlaceholder2 = false;
                    userTicketField.setForeground(Color.BLACK);
                    userTicketField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userTicketField.getText().isEmpty()) {
                    userTicketField.setText("Integer");
                    userTicketField.setForeground(Color.LIGHT_GRAY);
                    showingPlaceholder2 = true;

                }
            }
        });

        //--------------BUTTONS--------------------
        userConfirmAddOr.setFont(new Font("Courier", Font.BOLD, 15));
        userConfirmAddOr.setBackground(Color.decode("#F2DB9D"));
        userConfirmAddOr.setBounds(70, 250, 180, 30);

        userCancelAddOr.setFont(new Font("Courier", Font.BOLD, 15));
        userCancelAddOr.setBackground(Color.decode("#F2DB9D"));
        userCancelAddOr.setBounds(270, 250, 180, 30);

        userAddNewOrder.setFont(new Font("Courier", Font.BOLD, 15));
        userAddNewOrder.setBackground(Color.decode("#F2DB9D"));
        userAddNewOrder.setBounds(200, 190, 120, 30);

        addOrderPanel.setBackground(Color.decode("#BA4261"));

        //--------------ACTION_PERFORMANCE---------------------
        userConfirmAddOr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if (userTitleField.getText().equals("") || userKindField.getText().equals("") || userDateField.getText().equals("")
                        || userTicketField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {

                    try {
                        Order addOrder = new Order(userName, userTitleField.getText(), 
                                userKindField.getText(), 
                                new UsefulDate(userDateField.getText()),
                                Integer.parseInt(userTicketField.getText()));

                        RMIChannel mode = (RMIChannel) Naming.lookup("//localhost/Authentication");

                        int cost = mode.addOrder(addOrder);

                        if (cost != -1) {
                            JOptionPane.showMessageDialog(null, "Your order added successfuly!\nCost: " + cost + "€");
                            pane.removeAll();
                            pane.add(userPanel);
                            pane.repaint();
                            pane.revalidate();
                            setTitle("Menu Page");
                        } else {
                            JOptionPane.showMessageDialog(null, "The Order add failed!", "Order Error", JOptionPane.WARNING_MESSAGE);
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid tickets or date input!!","Invalid Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        userAddNewOrder.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                userTitleField.setText("");
                userKindField.setText("");
                userDateField.setText("");
                userTicketField.setText("");
            }
        });

        userCancelAddOr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(userPanel);
                pane.repaint();
                pane.revalidate();
                setTitle("Menu Page");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        addOrderPanel.add(userConfirmAddOr);
        addOrderPanel.add(userCancelAddOr);
        addOrderPanel.add(userAddNewOrder);
        addOrderPanel.add(userTitleLabel);
        addOrderPanel.add(userKindLabel);
        addOrderPanel.add(userDateLabel);
        addOrderPanel.add(userTicketLabel);
        addOrderPanel.add(userTitleField);
        addOrderPanel.add(userKindField);
        addOrderPanel.add(userDateField);
        addOrderPanel.add(userTicketField);

        addOrderPanel.setLayout(null);
        pane.add(addOrderPanel);
        pane.repaint();
        pane.revalidate();
    }

}
