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
    public void updateAlbum(Album album);
    public void deleteAlbum(String isrc);
    public Album getAlbum(String isrc);
    public ArrayList<Album> getAlbums();

    //Update Album Cover Image
    public void updateAlbumCoverImage(InputStream newCover, String isrc, MediaType md);
    public void deleteAlbumCoverImage(String isrc);
    public Cover getAlbumCoverImage(String isrc);
    public ArrayList<LogEntry> getChangeLogs(Date from, Date to, ChangeType changeType);
    public void clearLogs() throws RepException;
}
