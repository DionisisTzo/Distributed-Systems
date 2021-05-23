
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Library extends JFrame {

    private JPanel dis1Panel, dis2Panel, add1Panel, add2Panel, add3Panel, edit1Panel, edit2Panel, edit3Panel, edit4Panel, del1Panel, del2Panel, del3Panel;
    //-----------------------------------------DISPLAY-------------------------------------------------------------------------
    private JButton disAllAlbums, disAlbumSongs, disConfirm, disBack, disCancel;
    private JTextField disAlbumID;
    private JLabel disLabel, disLabel2;
    //-----------------------------------------ADD-----------------------------------------------------------------------------
    private JButton addOkAlbum, addOkSong, addCancelAlbum, addCancelSong, addAlbum, addSong, addBack;
    private JTextField addAlbumKind, addAlbumYear, addAlbumSongs, addSongTitle, addSongInter, addSongTime, addAlbumID;
    private JTextArea addAlbumDesc;
    private JLabel addLabel1, addSTitleLabel, addSInterLabel, addSTimeLabel, addAlbumIDLabel, addADescLabel, addAKindLabel, addAYearLabel, addASongsLabel;
    //-----------------------------------------EDIT----------------------------------------------------------------------------
    private JButton editBack, editConfirm, editOkAlbum, editCancelAlbum, editOkSong, editCancelSong, selectOkSong, selectCancelSong;
    private JLabel editLabel1, selectLabel, editAlbumID, editADescLabel, editAKindLabel, editAYearLabel, editASongsLabel, editSTitleLabel, editSInterLabel, editSTimeLabel, selectSTitleLabel, selectSInterLabel;
    private JTextField editIDField, editAlbumKind, editAlbumYear, editAlbumSongs, editSongTitle, editSongInter, editSongTime, selectSongTitle, selectSongInter;
    private JTextArea editAlbumDesc;
    private JRadioButton albumR, songR;
    //-----------------------------------------DELETE--------------------------------------------------------------------------
    private JButton delBack, delAlbum, delSong, delOkAlbum, delCancelAlbum, delOkSong, delCancelSong;
    private JLabel delLabel, delAlbumID, delSongTitle, delSongInter;
    private JTextField delIDField, delTitleField, delInterFields;

    private Container pane;

    public Library(int pages) {
        super();

        //----------------FRAME------------------------------
        setSize(550, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        pane = getContentPane();
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));

        if (pages == 1) { // choices for each button that user wants to press
            displayAlbums();
        } else if (pages == 2) {
            add();
        } else if (pages == 3) {
            edit();
        } else if (pages == 4) {
            delete();
        }

        setVisible(true);
        setContentPane(pane);
        pane.revalidate();
        setResizable(false);
    }

    //====================================DISPLAY_MENU=================================================================
    private void displayAlbums() {

        setTitle("Display Album / Songs");
        //--------------INITIALIZE_ATTRIBUTES-------------------
        disAllAlbums = new JButton("All Albums");
        disAlbumSongs = new JButton("Album Songs");
        disBack = new JButton("Back");
        disLabel = new JLabel("Display Albums or Songs", JLabel.CENTER);
        dis1Panel = new JPanel();

        //--------------DESIGN---------------------
        dis1Panel.setBackground(Color.decode("#484848"));

        disLabel.setFont(new Font("Courier", Font.BOLD, 33));
        disLabel.setForeground(Color.white);
        disLabel.setBounds(WIDTH, 50, 550, 50);

        disAllAlbums.setFont(new Font("Courier", Font.BOLD, 15));
        disAllAlbums.setBackground(Color.decode("#E8E8E8"));
        disAllAlbums.setBounds(180, 130, 180, 30);

        disAlbumSongs.setFont(new Font("Courier", Font.BOLD, 15));
        disAlbumSongs.setBackground(Color.decode("#E8E8E8"));
        disAlbumSongs.setBounds(180, 170, 180, 30);

        disBack.setFont(new Font("Courier", Font.BOLD, 15));
        disBack.setBackground(Color.decode("#E8E8E8"));
        disBack.setBounds(193, 235, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        disAllAlbums.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    JFrame albums = new JFrame("All Albums");
                    albums.setSize(630, 360);
                    albums.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    albums.setLocationRelativeTo(null);
                    albums.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));

                    JTextArea area_album = new JTextArea(40, 40);
                    JScrollPane scroll = new JScrollPane(area_album);
                    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

                    LibraryInterface display = (LibraryInterface) Naming.lookup("//localhost/Music");
                    ArrayList<String> allAlbum = display.displayAlbums(); // Put to the arrayList all albums witch came from the server

                    for (String allA : allAlbum) { // run the list and display to TextArea the albums
                        area_album.append(allA);
                    }

                    area_album.setEditable(false);
                    albums.add(area_album);
                    albums.setVisible(true);

                } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                    JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                    //ex.printStackTrace();
                }
            }
        });

        disAlbumSongs.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { displayAlbumSongs(); }});
        disBack.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new HomePage(); }});

        //--------------ADD_COMPONENTS-------------------------
        dis1Panel.add(disAllAlbums);
        dis1Panel.add(disAlbumSongs);
        dis1Panel.add(disBack);
        dis1Panel.add(disLabel);

        dis1Panel.setLayout(null);
        pane.add(dis1Panel);
        pane.revalidate();
    }

    private void displayAlbumSongs() {
        pane.removeAll();
        setTitle("Album Songs");

        //--------------INITIALIZE_ATTRIBUTES-------------------
        disAlbumID = new JTextField(20);
        disConfirm = new JButton("Confirm");
        disCancel = new JButton("Cancel");
        disLabel2 = new JLabel("Album ID");
        dis2Panel = new JPanel();

        //--------------DESIGN---------------------
        dis2Panel.setBackground(Color.decode("#484848"));

        disLabel2.setFont(new Font("Courier", Font.BOLD, 20));
        disLabel2.setForeground(Color.white);
        disLabel2.setBounds(230, 80, 150, 30);

        disAlbumID.setFont(new Font("Courier", Font.BOLD, 15));
        disAlbumID.setBounds(195, 120, 150, 25);
        disAlbumID.setHorizontalAlignment(JTextField.CENTER);

        disConfirm.setFont(new Font("Courier", Font.BOLD, 15));
        disConfirm.setBackground(Color.decode("#E8E8E8"));
        disConfirm.setBounds(110, 220, 150, 30);

        disCancel.setFont(new Font("Courier", Font.BOLD, 15));
        disCancel.setBackground(Color.decode("#E8E8E8"));
        disCancel.setBounds(290, 220, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        disConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (disAlbumID.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface display = (LibraryInterface) Naming.lookup("//localhost/Music");
                        int songsResult = display.checkID(Integer.parseInt(disAlbumID.getText())); // CheckID returns the song witch exists on the current album 

                        if (songsResult != -1) { // if the song exists
                            JFrame song = new JFrame("All Album ID " + disAlbumID.getText() + " Songs");
                            song.setSize(630, 360);
                            song.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            song.setLocationRelativeTo(null);
                            song.getRootPane().setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));

                            JTextArea area_song = new JTextArea(40, 40);
                            JScrollPane scroll = new JScrollPane(area_song);
                            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

                            ArrayList<String> allSong = display.displaySongs(Integer.parseInt(disAlbumID.getText()));
                            for (String allS : allSong) { // run the list and display to TextArea the songs from the current album
                                area_song.append(allS);
                            }

                            area_song.setEditable(false);
                            song.add(area_song);
                            song.setVisible(true);
                            disAlbumID.setText(null);
                        } else {
                            JOptionPane.showMessageDialog(null, "There is no Album ID: " + disAlbumID.getText(), "ID Error", JOptionPane.WARNING_MESSAGE);
                            disAlbumID.setText(null);
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input!! \nInsert Album ID.", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                        disAlbumID.setText(null);
                    }

                }
            }
        });

        disCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(dis1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Display Album / Songs");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        dis2Panel.add(disLabel2);
        dis2Panel.add(disAlbumID);
        dis2Panel.add(disConfirm);
        dis2Panel.add(disCancel);

        dis2Panel.setLayout(null);
        pane.add(dis2Panel);
        pane.repaint();
        pane.revalidate();
    }

    //====================================ADD_MENU=====================================================================
    private void add() {

        setTitle("Add Album / Songs");
        //--------------INITIALIZE_ATTRIBUTES-------------------
        addAlbum = new JButton("New Album");
        addSong = new JButton("New Song");
        addBack = new JButton("Back");
        addLabel1 = new JLabel("Add Album or Song", JLabel.CENTER);
        add1Panel = new JPanel();

        //--------------DESIGN---------------------
        add1Panel.setBackground(Color.decode("#484848"));

        addLabel1.setFont(new Font("Courier", Font.BOLD, 33));
        addLabel1.setForeground(Color.white);
        addLabel1.setBounds(WIDTH, 50, 550, 50);

        addAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        addAlbum.setBackground(Color.decode("#E8E8E8"));
        addAlbum.setBounds(180, 130, 180, 30);

        addSong.setFont(new Font("Courier", Font.BOLD, 15));
        addSong.setBackground(Color.decode("#E8E8E8"));
        addSong.setBounds(180, 170, 180, 30);

        addBack.setFont(new Font("Courier", Font.BOLD, 15));
        addBack.setBackground(Color.decode("#E8E8E8"));
        addBack.setBounds(193, 235, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        addAlbum.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { addAlbum(); }});
        addSong.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { addSong(); }});
        addBack.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new HomePage(); }});

        //--------------ADD_COMPONENTS-------------------------
        add1Panel.add(addAlbum);
        add1Panel.add(addSong);
        add1Panel.add(addBack);
        add1Panel.add(addLabel1);

        add1Panel.setLayout(null);
        pane.add(add1Panel);
        pane.revalidate();
    }

    private void addAlbum() {
        setTitle("Add New Album");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES------------------
        addOkAlbum = new JButton("Confirm");
        addCancelAlbum = new JButton("Cancel");
        addAlbumKind = new JTextField(20);
        addAlbumYear = new JTextField(20);
        addAlbumSongs = new JTextField(20);
        addAlbumDesc = new JTextArea();
        addADescLabel = new JLabel("Description");
        addAKindLabel = new JLabel("Kind");
        addAYearLabel = new JLabel("Year");
        addASongsLabel = new JLabel("Songs");
        add3Panel = new JPanel();

        //--------------DESIGN---------------------------------
        add3Panel.setBackground(Color.decode("#484848"));

        addADescLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addADescLabel.setForeground(Color.white);
        addADescLabel.setBounds(80, 20, 150, 30);

        addAlbumDesc.setFont(new Font("Courier", Font.PLAIN, 15));
        addAlbumDesc.setBounds(80, 50, 180, 90);

        addAKindLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addAKindLabel.setForeground(Color.white);
        addAKindLabel.setBounds(290, 20, 150, 30);

        addAlbumKind.setFont(new Font("Courier", Font.BOLD, 15));
        addAlbumKind.setBounds(290, 50, 150, 25);
        addAlbumKind.setHorizontalAlignment(JTextField.CENTER);

        addAYearLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addAYearLabel.setForeground(Color.white);
        addAYearLabel.setBounds(290, 80, 150, 30);

        addAlbumYear.setFont(new Font("Courier", Font.BOLD, 15));
        addAlbumYear.setBounds(290, 110, 150, 25);
        addAlbumYear.setHorizontalAlignment(JTextField.CENTER);

        addASongsLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addASongsLabel.setForeground(Color.white);
        addASongsLabel.setBounds(290, 140, 150, 30);

        addAlbumSongs.setFont(new Font("Courier", Font.BOLD, 15));
        addAlbumSongs.setBounds(290, 170, 150, 25);
        addAlbumSongs.setHorizontalAlignment(JTextField.CENTER);

        addOkAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        addOkAlbum.setBackground(Color.decode("#E8E8E8"));
        addOkAlbum.setBounds(110, 270, 150, 30);

        addCancelAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        addCancelAlbum.setBackground(Color.decode("#E8E8E8"));
        addCancelAlbum.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        addOkAlbum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (addAlbumKind.getText().equals("")
                        || addAlbumYear.getText().equals("")
                        || addAlbumSongs.getText().equals("")
                        || addAlbumDesc.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface add = (LibraryInterface) Naming.lookup("//localhost/Music");
                        Album album = new Album(addAlbumDesc.getText(), addAlbumKind.getText(), Integer.parseInt(addAlbumYear.getText()), Integer.parseInt(addAlbumSongs.getText()));

                        if (add.createAlbum(album) == true) { // if the album added succesfully to the database 
                            JOptionPane.showMessageDialog(null, "The album added successfuly!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Album failed to create.", "Add Error", JOptionPane.WARNING_MESSAGE);
                        }

                        addAlbumDesc.setText(null);
                        addAlbumKind.setText(null);
                        addAlbumYear.setText(null);
                        addAlbumSongs.setText(null);

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) { // check if the fields are not numbers 

                        String str = addAlbumYear.getText();
                        String str1 = addAlbumSongs.getText();

                        // If the textfield is not integer and contains only letter then 
                        if (str.matches("^[a-zA-Z]+$")) { // invalid input for the year 
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nIntegers (Year)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            addAlbumYear.setText(null);
                        } else if (str1.matches("^[a-zA-Z]+$")) { // invalid input for the song 
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nIntegers (Songs)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            addAlbumSongs.setText(null);
                        }
                    }
                }
            }
        });

        addCancelAlbum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(add1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Add Album / Songs");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        add3Panel.add(addOkAlbum);
        add3Panel.add(addCancelAlbum);

        add3Panel.add(addADescLabel);
        add3Panel.add(addAKindLabel);
        add3Panel.add(addAYearLabel);
        add3Panel.add(addASongsLabel);

        add3Panel.add(addAlbumDesc);
        add3Panel.add(addAlbumKind);
        add3Panel.add(addAlbumYear);
        add3Panel.add(addAlbumSongs);

        add3Panel.setLayout(null);
        pane.add(add3Panel);
        pane.repaint();
        pane.revalidate();
    }

    private void addSong() {
        setTitle("Add New Song");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES------------------
        addOkSong = new JButton("Confirm");
        addCancelSong = new JButton("Cancel");
        addAlbumID = new JTextField(20);
        addSongTitle = new JTextField(20);
        addSongInter = new JTextField(20);
        addSongTime = new JTextField(20);
        addAlbumIDLabel = new JLabel("Album ID");
        addSTitleLabel = new JLabel("Title");
        addSInterLabel = new JLabel("Interpreter");
        addSTimeLabel = new JLabel("Duration:Sec");
        add2Panel = new JPanel();

        //--------------DESIGN---------------------------------
        add2Panel.setBackground(Color.decode("#484848"));

        addAlbumIDLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addAlbumIDLabel.setForeground(Color.white);
        addAlbumIDLabel.setBounds(195, 10, 150, 30);

        addAlbumID.setFont(new Font("Courier", Font.BOLD, 15));
        addAlbumID.setBounds(195, 40, 150, 25);
        addAlbumID.setHorizontalAlignment(JTextField.CENTER);

        addSTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addSTitleLabel.setForeground(Color.white);
        addSTitleLabel.setBounds(195, 70, 150, 30);

        addSongTitle.setFont(new Font("Courier", Font.BOLD, 15));
        addSongTitle.setBounds(195, 100, 150, 25);
        addSongTitle.setHorizontalAlignment(JTextField.CENTER);

        addSInterLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addSInterLabel.setForeground(Color.white);
        addSInterLabel.setBounds(195, 130, 150, 30);

        addSongInter.setFont(new Font("Courier", Font.BOLD, 15));
        addSongInter.setBounds(195, 160, 150, 25);
        addSongInter.setHorizontalAlignment(JTextField.CENTER);

        addSTimeLabel.setFont(new Font("Courier", Font.BOLD, 15));
        addSTimeLabel.setForeground(Color.white);
        addSTimeLabel.setBounds(195, 190, 150, 30);

        addSongTime.setFont(new Font("Courier", Font.BOLD, 15));
        addSongTime.setBounds(195, 220, 150, 25);
        addSongTime.setHorizontalAlignment(JTextField.CENTER);

        addOkSong.setFont(new Font("Courier", Font.BOLD, 15));
        addOkSong.setBackground(Color.decode("#E8E8E8"));
        addOkSong.setBounds(110, 270, 150, 30);

        addCancelSong.setFont(new Font("Courier", Font.BOLD, 15));
        addCancelSong.setBackground(Color.decode("#E8E8E8"));
        addCancelSong.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        addOkSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (addAlbumID.getText().equals("")
                        || addSongTitle.getText().equals("")
                        || addSongInter.getText().equals("")
                        || addSongTime.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface add = (LibraryInterface) Naming.lookup("//localhost/Music");

                        Song song = new Song(addSongTitle.getText(), addSongInter.getText(), Integer.parseInt(addSongTime.getText()));

                        if (add.createSong(Integer.parseInt(addAlbumID.getText()), song) == true) { // if the song create succesfully to the database
                            JOptionPane.showMessageDialog(null, "The song added successfuly!");
                        } else {
                            JOptionPane.showMessageDialog(null, "The song failed to create.\nThe album ID doesn't exists OR\nThe song exists to the album.", "Add Error", JOptionPane.WARNING_MESSAGE);
                        }

                        addAlbumID.setText(null);
                        addSongTitle.setText(null);
                        addSongInter.setText(null);
                        addSongTime.setText(null);

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) { // check if the fields are not number

                        String str = addAlbumID.getText();
                        String str1 = addSongTime.getText();

                        // If the textfield is not integer and contains only letter then 
                        if (str.matches("^[a-zA-Z]+$")) { // invalid input 
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nInsert Album ID.", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            addAlbumID.setText(null);
                        } else if (str1.matches("^[a-zA-Z]+$")) {
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nInteger (Duration)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            addSongTime.setText(null);
                        }

                    }
                }
            }
        });

        addCancelSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(add1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Add Album / Songs");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        add2Panel.add(addOkSong);
        add2Panel.add(addCancelSong);

        add2Panel.add(addAlbumID);
        add2Panel.add(addSongTitle);
        add2Panel.add(addSongInter);
        add2Panel.add(addSongTime);

        add2Panel.add(addAlbumIDLabel);
        add2Panel.add(addSTitleLabel);
        add2Panel.add(addSInterLabel);
        add2Panel.add(addSTimeLabel);

        add2Panel.setLayout(null);
        pane.add(add2Panel);
        pane.repaint();
        pane.revalidate();
    }

    //====================================EDIT_MENU====================================================================
    private void edit() {
        setTitle("Edit Album / Songs");

        //--------------INITIALIZE_ATTRIBUTES------------------
        editConfirm = new JButton("Confirm");
        editBack = new JButton("Back");
        editLabel1 = new JLabel("Edit Album or Song", JLabel.CENTER);
        editAlbumID = new JLabel("Album ID");
        editIDField = new JTextField(20);
        ButtonGroup G1 = new ButtonGroup();
        albumR = new JRadioButton("Album");
        songR = new JRadioButton("Song");
        edit1Panel = new JPanel();

        //--------------DESIGN---------------------------------
        edit1Panel.setBackground(Color.decode("#484848"));

        //---------------Label---------
        editLabel1.setFont(new Font("Courier", Font.BOLD, 33));
        editLabel1.setForeground(Color.white);
        editLabel1.setBounds(WIDTH, 20, 550, 50);

        editAlbumID.setFont(new Font("Courier", Font.BOLD, 20));
        editAlbumID.setForeground(Color.white);
        editAlbumID.setBounds(230, 80, 150, 30);

        editIDField.setFont(new Font("Courier", Font.BOLD, 15));
        editIDField.setBounds(195, 120, 150, 25);
        editIDField.setHorizontalAlignment(JTextField.CENTER);

        albumR.setFont(new Font("Courier", Font.BOLD, 15));
        albumR.setForeground(Color.white);
        albumR.setBackground(Color.decode("#484848"));
        albumR.setBounds(230, 170, 100, 20);

        songR.setFont(new Font("Courier", Font.BOLD, 15));
        songR.setForeground(Color.white);
        songR.setBackground(Color.decode("#484848"));
        songR.setBounds(230, 210, 100, 20);

        G1.add(albumR);
        G1.add(songR);
        //---------------Buttons----------
        editConfirm.setFont(new Font("Courier", Font.BOLD, 15));
        editConfirm.setBackground(Color.decode("#E8E8E8"));
        editConfirm.setBounds(110, 270, 150, 30);

        editBack.setFont(new Font("Courier", Font.BOLD, 15));
        editBack.setBackground(Color.decode("#E8E8E8"));
        editBack.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        editConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editIDField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface edit = (LibraryInterface) Naming.lookup("//localhost/Music");
                        int IDCheck = edit.checkID(Integer.parseInt(editIDField.getText())); // call the method from the server to check if the ID that user gave exists

                        if (IDCheck != -1) { // if the ID exists 
                            if (albumR.isSelected()) {
                                editAlbum(Integer.parseInt(editIDField.getText())); // display the frame to edit album
                                editIDField.setText(null);
                            } else if (songR.isSelected()) {
                                selectSong(Integer.parseInt(editIDField.getText())); // else display the frame to edit song
                                editIDField.setText(null);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "There is no Album ID: " + editIDField.getText(), "ID Error", JOptionPane.WARNING_MESSAGE);
                            editIDField.setText(null);
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input!! \nInsert Album ID.", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                        editIDField.setText(null);
                    }

                }
            }
        });

        editBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new HomePage();
            }
        });
        //--------------ADD_COMPONENTS-------------------------
        edit1Panel.add(editLabel1);
        edit1Panel.add(editAlbumID);
        edit1Panel.add(editIDField);
        edit1Panel.add(albumR);
        edit1Panel.add(songR);

        edit1Panel.add(editConfirm);
        edit1Panel.add(editBack);

        edit1Panel.setLayout(null);
        pane.add(edit1Panel);
        pane.revalidate();
    }

    private void editAlbum(int AlbumID) {
        setTitle("Edit Album");
        pane.removeAll();
        //--------------INITIALIZE_ATTRIBUTES------------------
        editOkAlbum = new JButton("Confirm");
        editCancelAlbum = new JButton("Cancel");
        editAlbumKind = new JTextField(20);
        editAlbumYear = new JTextField(20);
        editAlbumSongs = new JTextField(20);
        editAlbumDesc = new JTextArea();
        editADescLabel = new JLabel("Description");
        editAKindLabel = new JLabel("Kind");
        editAYearLabel = new JLabel("Year");
        editASongsLabel = new JLabel("Songs");
        edit2Panel = new JPanel();
        //--------------DESIGN---------------------------------
        edit2Panel.setBackground(Color.decode("#484848"));

        editADescLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editADescLabel.setForeground(Color.white);
        editADescLabel.setBounds(80, 20, 150, 30);

        editAlbumDesc.setFont(new Font("Courier", Font.PLAIN, 15));
        editAlbumDesc.setBounds(80, 50, 180, 90);

        editAKindLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editAKindLabel.setForeground(Color.white);
        editAKindLabel.setBounds(290, 20, 150, 30);

        editAlbumKind.setFont(new Font("Courier", Font.BOLD, 15));
        editAlbumKind.setBounds(290, 50, 150, 25);
        editAlbumKind.setHorizontalAlignment(JTextField.CENTER);

        editAYearLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editAYearLabel.setForeground(Color.white);
        editAYearLabel.setBounds(290, 80, 150, 30);

        editAlbumYear.setFont(new Font("Courier", Font.BOLD, 15));
        editAlbumYear.setBounds(290, 110, 150, 25);
        editAlbumYear.setHorizontalAlignment(JTextField.CENTER);

        editASongsLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editASongsLabel.setForeground(Color.white);
        editASongsLabel.setBounds(290, 140, 150, 30);

        editAlbumSongs.setFont(new Font("Courier", Font.BOLD, 15));
        editAlbumSongs.setBounds(290, 170, 150, 25);
        editAlbumSongs.setHorizontalAlignment(JTextField.CENTER);

        editOkAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        editOkAlbum.setBackground(Color.decode("#E8E8E8"));
        editOkAlbum.setBounds(110, 270, 150, 30);

        editCancelAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        editCancelAlbum.setBackground(Color.decode("#E8E8E8"));
        editCancelAlbum.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        editOkAlbum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (editAlbumKind.getText().equals("")
                        || editAlbumYear.getText().equals("")
                        || editAlbumSongs.getText().equals("")
                        || editAlbumDesc.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface edit = (LibraryInterface) Naming.lookup("//localhost/Music");

                        if (edit.updateAlbum(editAlbumDesc.getText(), editAlbumKind.getText(),
                                Integer.parseInt(editAlbumYear.getText()),
                                Integer.parseInt(editAlbumSongs.getText()), AlbumID) == true) {

                            JOptionPane.showMessageDialog(null, "The album updated successfuly!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Album failed to update.", "Update Error", JOptionPane.WARNING_MESSAGE);
                        }

                        pane.removeAll();
                        pane.add(edit1Panel);
                        pane.repaint();
                        pane.revalidate();
                        setTitle("Edit Album / Songs");

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) {

                        String str = editAlbumYear.getText();
                        String str1 = editAlbumSongs.getText();

                        // If the textfield is not integer and contains only letter then 
                        if (str.matches("^[a-zA-Z]+$")) { // invalid input for the year 
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nIntegers (Year)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            editAlbumYear.setText(null);
                        } else if (str1.matches("^[a-zA-Z]+$")) { // invalid input for the song 
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nIntegers (Songs)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            editAlbumSongs.setText(null);
                        }
                    }
                }
            }
        });

        editCancelAlbum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(edit1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Edit Album / Songs");
            }
        });
        //--------------ADD_COMPONENTS-------------------------
        edit2Panel.add(editOkAlbum);
        edit2Panel.add(editCancelAlbum);

        edit2Panel.add(editADescLabel);
        edit2Panel.add(editAKindLabel);
        edit2Panel.add(editAYearLabel);
        edit2Panel.add(editASongsLabel);

        edit2Panel.add(editAlbumDesc);
        edit2Panel.add(editAlbumKind);
        edit2Panel.add(editAlbumYear);
        edit2Panel.add(editAlbumSongs);

        edit2Panel.setLayout(null);
        pane.add(edit2Panel);
        pane.repaint();
        pane.revalidate();
    }

    private void selectSong(int AlbumID) {
        setTitle("Select the Song");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES------------------
        selectOkSong = new JButton("Confirm");
        selectCancelSong = new JButton("Cancel");
        selectSongTitle = new JTextField(20);
        selectSongInter = new JTextField(20);
        selectSTitleLabel = new JLabel("Title");
        selectSInterLabel = new JLabel("Interpreter");
        selectLabel = new JLabel("Select the song for edit", JLabel.CENTER);
        edit3Panel = new JPanel();

        //--------------DESIGN---------------------------------
        edit3Panel.setBackground(Color.decode("#484848"));

        selectLabel.setFont(new Font("Courier", Font.BOLD, 33));
        selectLabel.setForeground(Color.white);
        selectLabel.setBounds(WIDTH, 30, 550, 50);

        selectSTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        selectSTitleLabel.setForeground(Color.white);
        selectSTitleLabel.setBounds(195, 100, 150, 30);

        selectSongTitle.setFont(new Font("Courier", Font.BOLD, 15));
        selectSongTitle.setBounds(195, 130, 150, 25);
        selectSongTitle.setHorizontalAlignment(JTextField.CENTER);

        selectSInterLabel.setFont(new Font("Courier", Font.BOLD, 15));
        selectSInterLabel.setForeground(Color.white);
        selectSInterLabel.setBounds(195, 160, 150, 30);

        selectSongInter.setFont(new Font("Courier", Font.BOLD, 15));
        selectSongInter.setBounds(195, 190, 150, 25);
        selectSongInter.setHorizontalAlignment(JTextField.CENTER);

        selectOkSong.setFont(new Font("Courier", Font.BOLD, 15));
        selectOkSong.setBackground(Color.decode("#E8E8E8"));
        selectOkSong.setBounds(110, 270, 150, 30);

        selectCancelSong.setFont(new Font("Courier", Font.BOLD, 15));
        selectCancelSong.setBackground(Color.decode("#E8E8E8"));
        selectCancelSong.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        selectOkSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectSongTitle.getText().equals("") || selectSongInter.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface selectSong = (LibraryInterface) Naming.lookup("//localhost/Music");

                        if (selectSong.checkSong(AlbumID, selectSongTitle.getText(), selectSongInter.getText()) == true) { // if the song exists 
                            editSong(AlbumID, selectSongTitle.getText(), selectSongInter.getText()); // call the method to update the song
                        } else {
                            JOptionPane.showMessageDialog(null, "There is no such Song.", "Song Error", JOptionPane.WARNING_MESSAGE);
                        }

                        selectSongTitle.setText(null);
                        selectSongInter.setText(null);

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    }
                }
            }
        });

        selectCancelSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(edit1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Edit Album / Songs");
            }
        });
        //--------------ADD_COMPONENTS-------------------------
        edit3Panel.add(selectOkSong);
        edit3Panel.add(selectCancelSong);

        edit3Panel.add(selectSTitleLabel);
        edit3Panel.add(selectSInterLabel);

        edit3Panel.add(selectLabel);
        edit3Panel.add(selectSongTitle);
        edit3Panel.add(selectSongInter);

        edit3Panel.setLayout(null);
        pane.add(edit3Panel);
        pane.repaint();
        pane.revalidate();
    }

    private void editSong(int AlbumID, String oldTitle, String oldInterpreter) {
        setTitle("Edit Song");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES------------------
        editOkSong = new JButton("Confirm");
        editCancelSong = new JButton("Cancel");
        editSongTitle = new JTextField(20);
        editSongInter = new JTextField(20);
        editSongTime = new JTextField(20);
        editSTitleLabel = new JLabel("Title");
        editSInterLabel = new JLabel("Interpreter");
        editSTimeLabel = new JLabel("Duration:Sec");
        edit4Panel = new JPanel();

        //--------------DESIGN---------------------------------
        edit4Panel.setBackground(Color.decode("#484848"));

        editSTitleLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editSTitleLabel.setForeground(Color.white);
        editSTitleLabel.setBounds(195, 40, 150, 30);

        editSongTitle.setFont(new Font("Courier", Font.BOLD, 15));
        editSongTitle.setBounds(195, 70, 150, 25);
        editSongTitle.setHorizontalAlignment(JTextField.CENTER);

        editSInterLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editSInterLabel.setForeground(Color.white);
        editSInterLabel.setBounds(195, 100, 150, 30);

        editSongInter.setFont(new Font("Courier", Font.BOLD, 15));
        editSongInter.setBounds(195, 130, 150, 25);
        editSongInter.setHorizontalAlignment(JTextField.CENTER);

        editSTimeLabel.setFont(new Font("Courier", Font.BOLD, 15));
        editSTimeLabel.setForeground(Color.white);
        editSTimeLabel.setBounds(195, 160, 150, 30);

        editSongTime.setFont(new Font("Courier", Font.BOLD, 15));
        editSongTime.setBounds(195, 190, 150, 25);
        editSongTime.setHorizontalAlignment(JTextField.CENTER);

        editOkSong.setFont(new Font("Courier", Font.BOLD, 15));
        editOkSong.setBackground(Color.decode("#E8E8E8"));
        editOkSong.setBounds(110, 270, 150, 30);

        editCancelSong.setFont(new Font("Courier", Font.BOLD, 15));
        editCancelSong.setBackground(Color.decode("#E8E8E8"));
        editCancelSong.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        editOkSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (editSongTitle.getText().equals("")
                        || editSongInter.getText().equals("")
                        || editSongTime.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface edit = (LibraryInterface) Naming.lookup("//localhost/Music");

                        if (edit.updateSong(editSongTitle.getText(), editSongInter.getText(), Integer.parseInt(editSongTime.getText()), AlbumID, oldTitle, oldInterpreter) == true) {
                            JOptionPane.showMessageDialog(null, "The song updated successfuly!");
                        } else {
                            JOptionPane.showMessageDialog(null, "The song failed to update.", "Update Error", JOptionPane.WARNING_MESSAGE);
                        }

                        pane.removeAll();
                        pane.add(edit1Panel);
                        pane.repaint();
                        pane.revalidate();
                        setTitle("Edit Album / Songs");

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input!! \nInteger (Duration)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                        editSongTime.setText(null);
                    }
                }
            }
        });

        editCancelSong.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(edit1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Edit Album / Songs");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        edit4Panel.add(editOkSong);
        edit4Panel.add(editCancelSong);

        edit4Panel.add(editSongInter);
        edit4Panel.add(editSongTitle);
        edit4Panel.add(editSongTime);

        edit4Panel.add(editSTitleLabel);
        edit4Panel.add(editSInterLabel);
        edit4Panel.add(editSTimeLabel);

        edit4Panel.setLayout(null);
        pane.add(edit4Panel);
        pane.repaint();
        pane.revalidate();
    } // failed song update error

    //====================================DELETE_MENU==================================================================
    private void delete() {
        setTitle("Delete Album / Songs");

        //--------------INITIALIZE_ATTRIBUTES------------------
        delAlbum = new JButton("Delete Album");
        delSong = new JButton("Delete Song");
        delBack = new JButton("Back");
        delLabel = new JLabel("Delete Albums or Songs", JLabel.CENTER);
        del1Panel = new JPanel();

        //--------------DESIGN---------------------
        del1Panel.setBackground(Color.decode("#484848"));

        delLabel.setFont(new Font("Courier", Font.BOLD, 33));
        delLabel.setForeground(Color.white);
        delLabel.setBounds(WIDTH, 50, 550, 50);

        delAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        delAlbum.setBackground(Color.decode("#E8E8E8"));
        delAlbum.setBounds(180, 130, 180, 30);

        delSong.setFont(new Font("Courier", Font.BOLD, 15));
        delSong.setBackground(Color.decode("#E8E8E8"));
        delSong.setBounds(180, 170, 180, 30);

        delBack.setFont(new Font("Courier", Font.BOLD, 15));
        delBack.setBackground(Color.decode("#E8E8E8"));
        delBack.setBounds(193, 235, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        delAlbum.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { deleteAlbum(); }});
        delSong.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { deleteSong(); }});
        delBack.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e) { dispose(); new HomePage(); }});

        //--------------ADD_COMPONENTS-------------------------
        del1Panel.add(delLabel);

        del1Panel.add(delAlbum);
        del1Panel.add(delSong);
        del1Panel.add(delBack);

        del1Panel.setLayout(null);
        pane.add(del1Panel);
        pane.revalidate();
    }

    private void deleteAlbum() {
        setTitle("Delete Album");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES------------------
        delOkAlbum = new JButton("Confirm");
        delCancelAlbum = new JButton("Cancel");
        delAlbumID = new JLabel("Album ID");
        delIDField = new JTextField(20);
        del2Panel = new JPanel();
        //--------------DESIGN---------------------------------
        del2Panel.setBackground(Color.decode("#484848"));

        delAlbumID.setFont(new Font("Courier", Font.BOLD, 20));
        delAlbumID.setForeground(Color.white);
        delAlbumID.setBounds(230, 80, 150, 30);

        delIDField.setFont(new Font("Courier", Font.BOLD, 15));
        delIDField.setBounds(195, 120, 150, 25);
        delIDField.setHorizontalAlignment(JTextField.CENTER);

        delOkAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        delOkAlbum.setBackground(Color.decode("#E8E8E8"));
        delOkAlbum.setBounds(110, 220, 150, 30);

        delCancelAlbum.setFont(new Font("Courier", Font.BOLD, 15));
        delCancelAlbum.setBackground(Color.decode("#E8E8E8"));
        delCancelAlbum.setBounds(290, 220, 150, 30);
        //--------------ACTION_PERFORMANCE---------------------

        delOkAlbum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delIDField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface delete = (LibraryInterface) Naming.lookup("//localhost/Music");

                        if (delete.deleteAlbum(Integer.parseInt(delIDField.getText())) == true) {
                            JOptionPane.showMessageDialog(null, "The album deleted successfuly!");
                            pane.removeAll();
                            pane.add(del1Panel);
                            pane.repaint();
                            pane.revalidate();
                            setTitle("Delete Album / Songs");
                        } else {
                            JOptionPane.showMessageDialog(null, "There is no Album ID: " + delIDField.getText(), "Delete Error", JOptionPane.WARNING_MESSAGE);
                            delIDField.setText(null);
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input!! \nInsert Album ID.", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                        delIDField.setText(null);
                    }
                }
            }
        });

        delCancelAlbum.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(del1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Delete Album / Songs");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        del2Panel.add(delOkAlbum);
        del2Panel.add(delCancelAlbum);

        del2Panel.add(delAlbumID);
        del2Panel.add(delIDField);

        del2Panel.setLayout(null);
        pane.add(del2Panel);
        pane.repaint();
        pane.revalidate();
    }

    private void deleteSong() {
        setTitle("Delete Song");
        pane.removeAll();

        //--------------INITIALIZE_ATTRIBUTES------------------
        delOkSong = new JButton("Confirm");
        delCancelSong = new JButton("Cancel");
        delAlbumID = new JLabel("Album ID");
        delSongTitle = new JLabel("Title");
        delSongInter = new JLabel("Interpreter");
        delIDField = new JTextField(20);
        delTitleField = new JTextField(20);
        delInterFields = new JTextField(20);
        del3Panel = new JPanel();

        //--------------DESIGN---------------------------------
        del3Panel.setBackground(Color.decode("#484848"));

        delAlbumID.setFont(new Font("Courier", Font.BOLD, 15));
        delAlbumID.setForeground(Color.white);
        delAlbumID.setBounds(195, 40, 150, 30);

        delIDField.setFont(new Font("Courier", Font.BOLD, 15));
        delIDField.setBounds(195, 70, 150, 25);
        delIDField.setHorizontalAlignment(JTextField.CENTER);

        delSongTitle.setFont(new Font("Courier", Font.BOLD, 15));
        delSongTitle.setForeground(Color.white);
        delSongTitle.setBounds(195, 100, 150, 30);

        delTitleField.setFont(new Font("Courier", Font.BOLD, 15));
        delTitleField.setBounds(195, 130, 150, 25);
        delTitleField.setHorizontalAlignment(JTextField.CENTER);

        delSongInter.setFont(new Font("Courier", Font.BOLD, 15));
        delSongInter.setForeground(Color.white);
        delSongInter.setBounds(195, 160, 150, 30);

        delInterFields.setFont(new Font("Courier", Font.BOLD, 15));
        delInterFields.setBounds(195, 190, 150, 25);
        delInterFields.setHorizontalAlignment(JTextField.CENTER);

        delOkSong.setFont(new Font("Courier", Font.BOLD, 15));
        delOkSong.setBackground(Color.decode("#E8E8E8"));
        delOkSong.setBounds(110, 270, 150, 30);

        delCancelSong.setFont(new Font("Courier", Font.BOLD, 15));
        delCancelSong.setBackground(Color.decode("#E8E8E8"));
        delCancelSong.setBounds(290, 270, 150, 30);

        //--------------ACTION_PERFORMANCE---------------------
        delOkSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (delIDField.getText().equals("") || delTitleField.getText().equals("") || delInterFields.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields.", "TextField Error", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        LibraryInterface delete = (LibraryInterface) Naming.lookup("//localhost/Music");

                        if (delete.deleteSong(Integer.parseInt(delIDField.getText()), delTitleField.getText(), delInterFields.getText()) == true) {
                            JOptionPane.showMessageDialog(null, "The song deleted successfuly!");
                            pane.removeAll();
                            pane.add(del1Panel);
                            pane.repaint();
                            pane.revalidate();
                            setTitle("Delete Album / Songs");
                        } else {
                            JOptionPane.showMessageDialog(null, "The song failed to delete.\nThe song may does not exist or ID.", "Delete Error", JOptionPane.WARNING_MESSAGE);
                            delIDField.setText(null);
                            delTitleField.setText(null);
                            delInterFields.setText(null);
                        }

                    } catch (RemoteException | MalformedURLException | NotBoundException ex) {
                        JOptionPane.showMessageDialog(null, "The Server is under construction.\nPlease, try again later!", "Connection Error", JOptionPane.ERROR_MESSAGE);
                        //ex.printStackTrace();
                    } catch (NumberFormatException ex) {

                        String str = delIDField.getText();

                        if (str.matches("^[a-zA-Z]+$")) {
                            JOptionPane.showMessageDialog(null, "Invalid input!! \nInteger (ID)", "Invalid Error", JOptionPane.WARNING_MESSAGE);
                            delIDField.setText(null);
                        }
                    }
                }
            }
        });

        delCancelSong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pane.removeAll();
                pane.add(del1Panel);
                pane.repaint();
                pane.revalidate();
                setTitle("Delete Album / Songs");
            }
        });

        //--------------ADD_COMPONENTS-------------------------
        del3Panel.add(delOkSong);
        del3Panel.add(delCancelSong);

        del3Panel.add(delAlbumID);
        del3Panel.add(delSongTitle);
        del3Panel.add(delSongInter);

        del3Panel.add(delIDField);
        del3Panel.add(delTitleField);
        del3Panel.add(delInterFields);

        del3Panel.setLayout(null);
        pane.add(del3Panel);
        pane.repaint();
        pane.revalidate();
    }
}
