package it.unical.inf.webappes1.persistence.dao;

import it.unical.inf.webappes1.model.Song;

import java.util.List;

public interface SongDao {
    List<Song> findAll();
    Song findByPrimaryKey(String title);

    void save(Song song); // Salva una nuova canzone
    void delete(Song song); // Elimina una canzone

    List<Song> findAllByAlbumTitle(String albumTitle);
}