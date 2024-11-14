package it.unical.inf.webappes1.persistence.dao.impJDBC;
import java.util.List;
import it.unical.inf.webappes1.model.Song;
import it.unical.inf.webappes1.model.Album;
import it.unical.inf.webappes1.persistence.DBManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class AlbumProxy extends Album {

    // Caricamento lazy delle canzoni
    @Override
    public List<Song> getSongs() {
        if (this.songs == null) {
            this.songs = DBManager.getInstance().getSongDao().findAllByAlbumTitle(this.getTitolo());
        }
        return this.songs;
    }
}