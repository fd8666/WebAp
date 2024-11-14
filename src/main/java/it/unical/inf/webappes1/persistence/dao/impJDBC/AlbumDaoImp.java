package it.unical.inf.webappes1.persistence.dao.impJDBC;


import it.unical.inf.webappes1.model.Song;
import it.unical.inf.webappes1.model.Album;
import it.unical.inf.webappes1.persistence.DBManager;
import it.unical.inf.webappes1.persistence.dao.SongDao;
import it.unical.inf.webappes1.persistence.dao.AlbumDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDaoImp implements AlbumDao {
    Connection connection = null;

    public AlbumDaoImp(Connection conn) {
        this.connection = conn;
    }

    @Override
    public List<Album> findAll() {
        List<Album> albums = new ArrayList<Album>();
        String query = "SELECT * FROM album";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Album album = new AlbumProxy();
                album.setTitolo(rs.getString("title"));
                album.setArtista(rs.getString("artist"));
                albums.add(album);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return albums;
    }

    @Override
    public Album findByPrimaryKey(String title) {
        String query = "SELECT title, artist FROM album WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Album album = new Album();
                album.setTitolo(title);
                album.setArtista(resultSet.getString("artist"));
                return album;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Album album) {
        String query = "INSERT INTO album (title, artist, release_date) VALUES (?, ?, ?) " +
                "ON CONFLICT (title) DO UPDATE SET artist = EXCLUDED.artist, release_date = EXCLUDED.release_date";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, album.getTitolo());
            statement.setString(2, album.getArtista());
            statement.executeUpdate();

            List<Song> songs = album.getSongs();
            if (songs == null || songs.isEmpty()) {
                return;
            }

            // Reset all relations in the join table
            resetRelationsInJoinTable(connection, album.getTitolo());

            SongDao songDao = DBManager.getInstance().getSongDao();

            for (Song song : songs) {
                songDao.save(song);
                insertJoinAlbumSong(connection, album.getTitolo(), song.getTitolo());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetRelationsInJoinTable(Connection connection, String albumTitle) throws SQLException {
        String query = "DELETE FROM album_song WHERE album_title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, albumTitle);
            statement.executeUpdate();
        }
    }

    private void insertJoinAlbumSong(Connection connection, String albumTitle, String songTitle) throws SQLException {
        String query = "INSERT INTO album_song (album_title, song_title) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, albumTitle);
            statement.setString(2, songTitle);
            statement.executeUpdate();
        }
    }

    @Override
    public void delete(Album album) {

    }

    public static void main(String[] args) {
        AlbumDao albumDao = DBManager.getInstance().getAlbumDao();
        List<Album> albums = albumDao.findAll();
        for (Album album : albums) {
            System.out.println("Title: " + album.getTitolo());
            System.out.println("Artist: " + album.getArtista());

        }
    }
}