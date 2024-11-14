package it.unical.inf.webappes1.persistence.dao;
import it.unical.inf.webappes1.model.Album;

import java.util.List;

public interface AlbumDao {

    List<Album> findAll();

    Album findByPrimaryKey(String title);

    void save(Album album);

    void delete(Album album);

}