package repo;

import core.Album;
import core.ChangeType;
import core.Cover;
import core.LogEntry;
import exceptions.RepException;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public interface AlbumManager {
    void updateAlbum(Album album);
    void deleteAlbum(String isrc);
    Album getAlbum(String isrc);
    ArrayList<Album> getAlbums();

    //Update Album Cover Image
    void updateAlbumCoverImage(InputStream newCover, String isrc, MediaType md);
    void deleteAlbumCoverImage(String isrc);
    Cover getAlbumCoverImage(String isrc);
    ArrayList<LogEntry> getChangeLogs(Date from, Date to, ChangeType changeType);
    void clearLogs() throws RepException;
}
