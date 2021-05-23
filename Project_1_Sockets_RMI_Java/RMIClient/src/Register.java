
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Register extends JFrame {

    private JLabel register, nameLabel, sureNameLabel, phoneLabel, mailLabel, userNameLabel, passwordLabel;
    private JTextField nameText, sureNameText, phoneText, mailText, userNameText, passwordText;
    private JButton confirm, cancel;

    public Register() {
        super("Register Page");

        //----------------FRAME------------------------------
        setSize(480, 510);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        Container pane = getContentPane();
        pane.setBackground(Color.decode("#BA4261"));
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.decode("#F2DB9D")));

        //--------------INITIALIZE_ATTRIBUTES-------------------
        register = new JLabel("PLEASE FILL THE TEXT FIELDS!");
        nameLabel = new JLabel("Name");
        sureNameLabel = new JLabel("Surname");
        phoneLabel = new JLabel("Phone");
        mailLabel = new JLabel("Mail");
        userNameLabel = new JLabel("UserName");
        passwordLabel = new JLabel("Password");

        nameText = new JTextField(20);
        sureNameText = new JTextField(20);
        phoneText = new JTextField(20);
        mailText = new JTextField(20);
        userNameText = new JTextField(20);
        passwordText = new JPasswordField(20);

        confirm = new JButton("Register");
        cancel = new JButton("Cancel");

        components();

        //--------------ACTION_PERFORMANCE---------------------
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (nameText.getText().equals("") || sureNameText.getText().equals("") || phoneText.getText().equals("")
                        || mailText.getText().equals("") || userNameText.getText().equals("") || passwordText.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    User user = new User(nameText.getText(),sureNameText.getText(),phoneText.getText(),mailText.getText(),userNameText.getText(),passwordText.getText());
                    
                    try {
                        RMIChannel Authentication = (RMIChannel) Naming.lookup("//localhost/Authentication");
                        
                        if(Authentication.register(user)){
                            JOptionPane.showMessageDialog(null, "Successul Register!");
                            dispose();
                            new Login();
                        } else {
                            JOptionPane.showMessageDialog(null, "Register Failed. Try again!", "Register Error", JOptionPane.ERROR_MESSAGE);
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
        pane.add(register);
        pane.add(nameLabel);
        pane.add(sureNameLabel);
        pane.add(phoneLabel);
        pane.add(mailLabel);
        pane.add(userNameLabel);
        pane.add(passwordLabel);
        pane.add(nameText);
        pane.add(sureNameText);
        pane.add(phoneText);
        pane.add(mailText);
        pane.add(userNameText);
        pane.add(passwordText);
        pane.add(confirm);
        pane.add(cancel);

        pane.setLayout(null);
        setContentPane(pane);
        setVisible(true);
        pane.revalidate();
        setResizable(false);

    }

    private void components() {

        register.setFont(new Font("Courier", Font.BOLD, 20));
        register.setForeground(Color.BLACK);
        register.setBounds(80, 40, 330, 30);

        //--------------NAME-----------------------
        nameLabel.setFont(new Font("Courier", Font.BOLD, 15));
        nameLabel.setForeground(Color.decode("#F2DB9D"));
        nameLabel.setBounds(138, 75, 90, 30);

        nameText.setFont(new Font("Courier", Font.BOLD, 15));
        nameText.setBounds(138, 100, 180, 25);
        nameText.setHorizontalAlignment(JTextField.CENTER);

        //--------------SURNAME-----------------------
        sureNameLabel.setFont(new Font("Courier", Font.BOLD, 15));
        sureNameLabel.setForeground(Color.decode("#F2DB9D"));
        sureNameLabel.setBounds(138, 125, 90, 30);

        sureNameText.setFont(new Font("Courier", Font.BOLD, 15));
        sureNameText.setBounds(138, 150, 180, 25);
        sureNameText.setHorizontalAlignment(JTextField.CENTER);

        //--------------PHONE-----------------------
        phoneLabel.setFont(new Font("Courier", Font.BOLD, 15));
        phoneLabel.setForeground(Color.decode("#F2DB9D"));
        phoneLabel.setBounds(138, 175, 90, 30);

        phoneText.setFont(new Font("Courier", Font.BOLD, 15));
        phoneText.setBounds(138, 200, 180, 25);
        phoneText.setHorizontalAlignment(JTextField.CENTER);

        //--------------MAIL-----------------------
        mailLabel.setFont(new Font("Courier", Font.BOLD, 15));
        mailLabel.setForeground(Color.decode("#F2DB9D"));
        mailLabel.setBounds(138, 225, 90, 30);

        mailText.setFont(new Font("Courier", Font.BOLD, 15));
        mailText.setBounds(138, 250, 180, 25);
        mailText.setHorizontalAlignment(JTextField.CENTER);

        //--------------USERNAME-----------------------
        userNameLabel.setFont(new Font("Courier", Font.BOLD, 15));
        userNameLabel.setForeground(Color.decode("#F2DB9D"));
        userNameLabel.setBounds(138, 275, 90, 30);

        userNameText.setFont(new Font("Courier", Font.BOLD, 15));
        userNameText.setBounds(138, 300, 180, 25);
        userNameText.setHorizontalAlignment(JTextField.CENTER);

        //--------------PASSWORD-----------------------
        passwordLabel.setFont(new Font("Courier", Font.BOLD, 15));
        passwordLabel.setForeground(Color.decode("#F2DB9D"));
        passwordLabel.setBounds(138, 325, 90, 30);

        passwordText.setFont(new Font("Courier", Font.BOLD, 15));
        passwordText.setBounds(138, 350, 180, 25);
        passwordText.setHorizontalAlignment(JTextField.CENTER);

        //--------------CONFIRM_BUTTON-----------------------
        confirm.setFont(new Font("Courier", Font.BOLD, 15));
        confirm.setBackground(Color.decode("#F2DB9D"));
        confirm.setBounds(117, 410, 100, 30);

        //--------------CANCEL_BUTTON-----------------------
        cancel.setFont(new Font("Courier", Font.BOLD, 15));
        cancel.setBackground(Color.decode("#F2DB9D"));
        cancel.setBounds(237, 410, 100, 30);

    }

}
