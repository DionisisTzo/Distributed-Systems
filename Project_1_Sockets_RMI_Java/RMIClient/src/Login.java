
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.*;

public class Login extends JFrame {

    private JLabel login, userLabel, passLabel;
    private JTextField userName, password;
    private JButton confirm, cancel;

    public Login() {
        super("Login Page");

        //----------------FRAME------------------------------
        setSize(480, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        Container pane = getContentPane();
        pane.setBackground(Color.decode("#BA4261"));
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#F2DB9D")));

        //--------------INITIALIZE_ATTRIBUTES-------------------
        login = new JLabel("PLEASE FILL THE TEXT FIELDS!");
        userLabel = new JLabel("Username");
        passLabel = new JLabel("Password");
        userName = new JTextField(20);
        password = new JPasswordField(20);
        confirm = new JButton("Login");
        cancel = new JButton("Cancel");

        components();

        //--------------ACTION_PERFORMANCE---------------------
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (userName.getText().equals("") && password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        //ClientManager client = new ClientManager();
                        RMIChannel Authentication = (RMIChannel) Naming.lookup("//localhost/Authentication");
                        
                        //Authentication.addClient(client);
                        
                        User loggedInUser = Authentication.login(userName.getText(), password.getText());

                        if (loggedInUser != null) {
                            dispose();
                            new Menu(loggedInUser);
                        } else {
                            JOptionPane.showMessageDialog(null, "Wrong values. Try again!", "Login Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage();
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        pane.add(login);
        pane.add(userLabel);
        pane.add(passLabel);
        pane.add(userName);
        pane.add(password);
        pane.add(confirm);
        pane.add(cancel);

        pane.setLayout(null);
        pane.revalidate();
        setContentPane(pane);
        setVisible(true);
        setResizable(false);

    }

    private void components() {

        login.setFont(new Font("Courier", Font.BOLD, 20));
        login.setBackground(Color.BLACK);
        login.setBounds(90, 40, 330, 30);

        userLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userLabel.setForeground(Color.decode("#F2DB9D"));
        userLabel.setBounds(148, 95, 90, 30);

        passLabel.setFont(new Font("Courier", Font.BOLD, 15));
        passLabel.setForeground(Color.decode("#F2DB9D"));
        passLabel.setBounds(148, 155, 90, 30);

        userName.setFont(new Font("Courier", Font.BOLD, 15));
        userName.setBounds(148, 120, 180, 25);
        userName.setHorizontalAlignment(JTextField.CENTER);

        password.setFont(new Font("Courier", Font.BOLD, 15));
        password.setBounds(148, 180, 180, 25);
        password.setHorizontalAlignment(JTextField.CENTER);

        confirm.setFont(new Font("Courier", Font.BOLD, 15));
        confirm.setBackground(Color.decode("#F2DB9D"));
        confirm.setBounds(130, 260, 100, 30);

        cancel.setFont(new Font("Courier", Font.BOLD, 15));
        cancel.setBackground(Color.decode("#F2DB9D"));
        cancel.setBounds(250, 260, 100, 30);

    }

}
