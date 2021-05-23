
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.ArrayList;

public class LibraryDataBase extends UnicastRemoteObject implements LibraryInterface {

    public LibraryDataBase() throws RemoteException {
        super();
    }

    //====================================CREATE_METHODS======================================
    // This Method is used to create the database album and all the albums in it
    synchronized public boolean createAlbum(Album album) throws RemoteException {

        try {
            // Loading JDBC programm
            Class.forName("org.sqlite.JDBC");
            // Connect to the DB and create one if not exists
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");
            // Create Object statment to send commands to the DB
            Statement libStat = libConn.createStatement();

            // This try is used to check if the database was created ot not
            try {
                //libStat.executeUpdate("DROP TABLE IF EXISTS `album`;");
                libStat.executeUpdate("CREATE TABLE IF NOT EXISTS `album` (descript VARCHAR(200), "
                        + "kind VARCHAR(20), year INTEGER, totalsongs INTEGER, ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)"); // Greate ID for each row in the database and set it as primary key

                // This try is to insert the values from the object album
                try {
                    PreparedStatement preparedInsert = libConn.prepareStatement("INSERT INTO `album` (descript,kind,year,totalsongs) VALUES (?,?,?,?);");
                    preparedInsert.setString(1, album.getDescription());
                    preparedInsert.setString(2, album.getKind());
                    preparedInsert.setInt(3, album.getYear());
                    preparedInsert.setInt(4, album.getTotalSongs());
                    preparedInsert.addBatch();
                    preparedInsert.executeBatch();

                    System.out.println("The album succesfully created!\n");
                    libConn.close();
                    return true;

                } catch (SQLException e) {
                    System.out.println("The album values insert failed.\n");
                    e.printStackTrace();
                    return false;
                }

            } catch (SQLException e) {
                System.out.println("The DataBase Album table failed to create.\n");
                e.printStackTrace();
                return false;
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        }

        return false;
    }

    // This method is used to create songs in the current album
    synchronized public boolean createSong(int albumID, Song song) throws RemoteException {

        try {
            // Loading JDBC programm
            Class.forName("org.sqlite.JDBC");
            // Connect to the DB and create one if not exists
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");
            // Create Object statment to send commands to the DB
            Statement libStat = libConn.createStatement();

            try {
                //libStat.executeUpdate("DROP TABLE IF EXISTS `songs`;");
                // This execute is used to create a table songs into the library database with unique title and interpreter for each song and unique album ID 
                // each song will be insert in different albums and the albumID is references to the Primary key of the album table. ID 
                libStat.executeUpdate("CREATE TABLE IF NOT EXISTS `songs` (title VARCHAR(20), interpreter VARCHAR(20), duration INTEGER, "
                        + "AlbumID INTEGER, UNIQUE(title,interpreter,AlbumID), FOREIGN KEY(AlbumID) REFERENCES Library(ID))");

                // this try is used to create the song at the current album by taking the values from the object
                try {
                    PreparedStatement preparedInsert = libConn.prepareStatement("INSERT INTO `songs` (title,interpreter,duration,AlbumID) VALUES (?,?,?,?);");

                    preparedInsert.setString(1, song.getTitle());
                    preparedInsert.setString(2, song.getInterpreter());
                    preparedInsert.setInt(3, song.getDuration());
                    if (checkID(albumID) != -1) { // Check if the album ID exists on the album table to create the additional song in that album
                        preparedInsert.setInt(4, albumID);
                    }

                    preparedInsert.addBatch();
                    preparedInsert.executeBatch();

                    System.out.println("The song succesfully created!\n");
                    libConn.close();
                    return true;

                } catch (SQLException e) {
                    System.out.println("The song values insert failed.\n");
                    //e.printStackTrace();
                    return false;
                }

            } catch (SQLException e) {
                System.out.println("The DataBase Song table failed to create.\n");
                e.printStackTrace();
                return false;
            }

        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        }

        return false;
    }

    //====================================DELETE_METHODS======================================
    // This method is used to delete the album from the database
    synchronized public boolean deleteAlbum(int albumID) throws RemoteException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try {

                if (checkID(albumID) != 1) { // If the ID doesn't exists on the album table 
                    libConn.close();
                    return false;
                } else { // delete from the database the album with the current ID and the songs from it
                    PreparedStatement deleteAlbum = libConn.prepareStatement("DELETE FROM `album` WHERE `ID` = ?;"); // delete the album
                    PreparedStatement deleteAlbumSongs = libConn.prepareStatement("DELETE FROM `songs` WHERE `AlbumID` = ?;"); // delete the songs from the album if exists
                    deleteAlbum.setInt(1, albumID);
                    deleteAlbumSongs.setInt(1, albumID);
                    deleteAlbum.executeUpdate();
                    deleteAlbumSongs.executeUpdate();

                    libConn.close();
                    return true;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    // This method is used to delete the song from the current album from the database
    synchronized public boolean deleteSong(int albumID, String title, String interpreter) throws RemoteException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try {

                if (!checkSong(albumID, title, interpreter)) { // If the song doesn't exists on the songs table 
                    libConn.close();
                    return false;
                } else { // delete from the database the song from the current album ID with the current title and interpreter
                    PreparedStatement deleteAlbumSongs = libConn.prepareStatement("DELETE FROM `songs` WHERE `title` = ? AND `interpreter` = ? AND `AlbumID` = ?;"); // delete the songs from the album if exists
                    deleteAlbumSongs.setString(1, title);
                    deleteAlbumSongs.setString(2, interpreter);
                    deleteAlbumSongs.setInt(3, albumID);
                    deleteAlbumSongs.executeUpdate();

                    libConn.close();
                    return true;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    //====================================UPDATE_METHODS======================================
    // This method is used to update the album values
    synchronized public boolean updateAlbum(String desc, String kind, int year, int totalS, int albumID) throws RemoteException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try {
                PreparedStatement updateAlbum = libConn.prepareStatement("UPDATE `album` SET `descript` = ?, `kind` = ?, `year` = ?, `totalsongs` = ? WHERE `ID` = ?;");

                updateAlbum.setString(1, desc);
                updateAlbum.setString(2, kind);
                updateAlbum.setInt(3, year);
                updateAlbum.setInt(4, totalS);
                updateAlbum.setInt(5, albumID);
                updateAlbum.addBatch();
                updateAlbum.executeBatch();

                System.out.println("The album succesfully updated!\n");
                libConn.close();
                return true;

            } catch (SQLException ex) {
                System.out.println("The album update failed.\n");
                //ex.printStackTrace();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    // This method is used to update the song values from the current album from the database
    synchronized public boolean updateSong(String title, String interpreter, int duration, int albumID, String oldTitle, String oldInterpreter) throws RemoteException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try {
                PreparedStatement updateSong = libConn.prepareStatement("UPDATE `songs` SET `title` = ?, `interpreter` = ?, `duration` = ? WHERE `AlbumID` = ? AND `title` = ? AND `interpreter` = ?;");

                updateSong.setString(1, title);
                updateSong.setString(2, interpreter);
                updateSong.setInt(3, duration);
                updateSong.setInt(4, albumID);
                updateSong.setString(5, oldTitle);
                updateSong.setString(6, oldInterpreter);
                updateSong.addBatch();
                updateSong.executeBatch();

                System.out.println("The song succesfully updated!\n");
                libConn.close();
                return true;

            } catch (SQLException ex) {
                System.out.println("The song update failed.\n");
                //ex.printStackTrace();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    //====================================DISBLAY_METHODS=====================================
    public ArrayList<String> displayAlbums() throws RemoteException {

        ArrayList<String> allAlbums = new ArrayList<>();
        try {

            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");
            Statement libStat = libConn.createStatement();

            try {
                ResultSet albumData = libStat.executeQuery("SELECT * FROM `album`");

                int albumCount = 0;
                while (albumData.next()) {

                    albumCount++; // Count how many albums exist
                    String albums = (albumCount + ") DESCRIPTION: " + albumData.getString(1)
                            + " | KIND: " + albumData.getString(2)
                            + " | YEAR: " + String.valueOf(albumData.getInt(3))
                            + " | TOTAL_SONGS: " + String.valueOf(albumData.getInt(4))
                            + " | ALBUM_ID: " + String.valueOf(albumData.getInt(5)) + "\n");

                    allAlbums.add(albums); // Adding the albums from the DB to arrayList and return it for display
                }

                libConn.close();

            } catch (SQLException e) {
                System.out.println("There is no table Album to display");
                e.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("The connection failed.\n");
            e.printStackTrace();
        }

        return allAlbums;
    }

    public ArrayList<String> displaySongs(int albumID) throws RemoteException {

        ArrayList<String> allSongs = new ArrayList<>();
        try {

            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try {

                PreparedStatement songData = libConn.prepareStatement("SELECT * FROM `songs` WHERE `AlbumID` = ?;"); // display song from the current album 
                songData.setInt(1, albumID);
                ResultSet rs = songData.executeQuery();

                int songCount = 0;
                while (rs.next()) {

                    songCount++; // Count how many songs exist
                    String songs = (songCount + ") TITLE: " + rs.getString(1)
                            + " | INTERPRETER: " + rs.getString(2)
                            + " | DURATION: " + String.valueOf(rs.getInt(3))
                            + " | FROM THE ALBUM_ID: " + String.valueOf(rs.getInt(4)) + "\n");

                    allSongs.add(songs); // Adding the songs from the DB to arrayList and return it for display
                }

                libConn.close();

            } catch (SQLException e) {
                System.out.println("There is no table Song to display");
                e.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("The connection failed.\n");
            e.printStackTrace();
        }

        return allSongs;
    }

    //====================================CHECK_METHODS=======================================
    // This method is used to check if the album exists in database
    public int checkID(int albumID) throws RemoteException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try { // Check if the ID exists on the table album
                PreparedStatement checkID = libConn.prepareStatement("SELECT COUNT(ID) FROM `album` WHERE `ID` = ?;");
                checkID.setInt(1, albumID);
                ResultSet rs = checkID.executeQuery();

                int result = rs.getInt(1); // we need one result (if exists or not)
                if (result != 1) {
                    System.out.println("The album not exists.\n");
                    libConn.close();
                    return -1;
                } else {
                    libConn.close();
                    return result;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return -1;
            }

        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    // This method is used to check if the song exists in database for delete
    public boolean checkSong(int albumID, String title, String interpreter) throws RemoteException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection libConn = DriverManager.getConnection("jdbc:sqlite:Library.db");

            try { // Check if the song exists on the table songs
                PreparedStatement checkSong = libConn.prepareStatement("SELECT COUNT(AlbumID) FROM `songs` WHERE `AlbumID` = ? AND `title` = ? AND `interpreter` = ?;");
                checkSong.setInt(1, albumID);
                checkSong.setString(2, title);
                checkSong.setString(3, interpreter);
                ResultSet rs = checkSong.executeQuery();

                int result = rs.getInt(1); // we need one result (if exists or not)
                if (result != 1) {
                    System.out.println("The song not exists.\n");
                    libConn.close();
                    return false;
                } else {
                    libConn.close();
                    return true;
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }

        } catch (SQLException ex) {
            System.out.println("The connection failed.\n");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return false;
    }

}
