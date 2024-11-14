package it.unical.inf.webappes1.persistence.dao.impJDBC;

import it.unical.inf.webappes1.model.Song;
import it.unical.inf.webappes1.persistence.DBManager;
import it.unical.inf.webappes1.persistence.dao.SongDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDaoImp implements SongDao {

    Connection connection;

    public SongDaoImp(Connection conn) {
        this.connection = conn;
    }

    @Override
    public List<Song> findAll() {
        List<Song> songs = new ArrayList<Song>();
        String query = "SELECT * FROM song";
        Statement st = null;
        try {
            st = connection.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Song song = new Song();
                song.setTitolo(rs.getString("title"));
                song.setDurata(rs.getString("duration"));
                songs.add(song);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return songs;
    }

    @Override
    public Song findByPrimaryKey(String title) {
        String query = "SELECT title, duration FROM song WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Song(resultSet.getString("title"), resultSet.getString("duration"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void save(Song song) {
        String query = "INSERT INTO song (title, duration) VALUES (?, ?) " +
                "ON CONFLICT (title) DO UPDATE SET duration = EXCLUDED.duration";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, song.getTitolo());
            statement.setString(2, song.getDurata());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Song song) {
        String query = "DELETE FROM song WHERE title = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, song.getTitolo());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Song> findAllByAlbumTitle(String albumTitle) {
        List<Song> songs = new ArrayList<>();
        String query = "SELECT s.title, s.duration FROM song s " +
                "JOIN album_song a ON s.title = a.song_title " +
                "WHERE a.album_title = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, albumTitle);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String duration = resultSet.getString("duration");
                songs.add(new Song(title, duration));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static void main(String[] args) {
        SongDao songDao = DBManager.getInstance().getSongDao();
        List<Song> songs = songDao.findAll();
        for (Song song : songs) {
            System.out.println(song.getTitolo());
            System.out.println(song.getDurata());
        }
    }
}