package it.unical.inf.webappes1.persistence;

import it.unical.inf.webappes1.persistence.dao.AlbumDao;
import it.unical.inf.webappes1.persistence.dao.SongDao;
import it.unical.inf.webappes1.persistence.dao.impJDBC.AlbumDaoImp;
import it.unical.inf.webappes1.persistence.dao.impJDBC.SongDaoImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBManager {

    // Singleton instance
    private static DBManager instance = null;

    private DBManager() {}

    // DAO instances
    private SongDao songDao = null;
    private AlbumDao albumDao = null;

    // Database connection
    private Connection con = null;

    // Singleton pattern to get the DBManager instance
    public static DBManager getInstance(){
        if (instance == null){
            instance = new DBManager();
        }
        return instance;
    }

    // Get database connection
    public Connection getConnection(){
        if (con == null){
            try {
                // Change the URL, user, and password based on your database configuration
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Unical", "postgres", "123456");
            } catch (SQLException e) {
                throw new RuntimeException("Error establishing database connection", e);
            }
        }
        return con;
    }

    // Close the database connection
    public void closeConnection(){
        if (con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Get SongDao
    public SongDao getSongDao(){
        if (songDao == null) {
            songDao = new SongDaoImp(getConnection());
        }
        return songDao;
    }

    // Get AlbumDao
    public AlbumDao getAlbumDao(){
        if (albumDao == null) {
            albumDao = new AlbumDaoImp(getConnection());
        }
        return albumDao;
    }

    // Example of querying the database
    public static void main(String[] args) {
        DBManager dbManager = DBManager.getInstance();
        Connection con = dbManager.getConnection();

        // Example to query the song table
        try (var statement = con.createStatement()) {
            var rs = statement.executeQuery("SELECT * FROM song");
            while (rs.next()){
                System.out.println("Song Name: " + rs.getString("name"));
                System.out.println("Album Name: " + rs.getString("album_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.closeConnection();
        }
    }
}