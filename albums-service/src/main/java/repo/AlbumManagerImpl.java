//source: https://stackoverflow.com/questions/9204287/how-to-return-a-png-image-from-jersey-rest-service-method-to-the-browser
package repo;

import core.Album;
import core.ChangeType;
import core.Cover;
import core.LogEntry;
import exceptions.RepException;

import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlbumManagerImpl implements AlbumManager {

    private ArrayList<Album> albums = new ArrayList<>();

    //need mime type to render image
    //don't need to handle every single MIME type
    //libraries can be used to detect MIME type from extension of the file or from file data


    //Sort log entries chronologically by timestamp
    //All data is received and sent in JSON format


    //Create Album
    public void createAlbum(Album newAlbum) {
        try {
            albums.add(newAlbum);
           logs.add(new LogEntry(new Date(), ChangeType.CREATE, newAlbum.getIsrc()));
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    //Update Album
    @Override
    public void updateAlbum(Album album) {
        try {
            deleteAlbum(album.getIsrc());
            createAlbum(album);
            logs.add(new LogEntry(new Date(), ChangeType.UPDATE, album.getIsrc()));
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    //Delete Album
    @Override
    public void deleteAlbum(String isrc) {
        try {
            albums = albums.stream()
                    .filter(album -> !album.getIsrc().equals(isrc))
                    .collect(Collectors.toCollection(ArrayList::new));
            logs.add(new LogEntry(new Date(), ChangeType.DELETE, isrc));
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    //Get Album Info
    @Override
    public Album getAlbum(String isrc) {
        try {
            Album album = albums.stream()
                    .filter(album1 -> album1.getIsrc().equals(isrc))
                    .findAny().orElse(null);
            return album;
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    //Get Album List
    @Override
    public ArrayList<Album> getAlbums() {
        return albums;
    }


    //Update Album Cover Image
    public void updateAlbumCoverImage(InputStream newCover, String location, MediaType mimeType, String isrc){
        try {
            int read = 0;
            byte[] bytes = new byte[1024];
            FileOutputStream out = new FileOutputStream(location);
            while((read = newCover.read(bytes)) != -1){
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            Album album = getAlbum(isrc);
            if (album != null) {
                album.setCover(new Cover(location, mimeType));
                logs.add(new LogEntry(new Date(), ChangeType.UPDATE, isrc));
            }
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    @Override
    //Delete Album Cover Image
    public void deleteAlbumCoverImage(String isrc){
        try {
            Album album = getAlbum(isrc);
            album.setCover(null);
            logs.add(new LogEntry(new Date(), ChangeType.UPDATE, isrc));
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    @Override
    //Get Album Cover Image
    public Cover getAlbumCoverImage(String isrc) {
        try {
            Album album = getAlbum(isrc);
            if (album != null) {
                Cover cover = album.getCover();
                return cover;
            }
            return null;
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    public ArrayList<LogEntry> logs = new ArrayList<>();

    @Override
    //If no parameters are given, all change logs are returned
    public ArrayList<LogEntry> getChangeLogs(Date from, Date to, ChangeType changeType){
        if(from == null && to == null && changeType == null) return logs;
        Stream<LogEntry> logsToKeepStream = logs.stream();
        if (changeType != null) {
            logsToKeepStream = logs.stream().filter(log -> log.getType() == changeType);
        }
        if(from != null) {
            logsToKeepStream = logs.stream().filter(log -> log.getTimestamp().after(from));
        }
        if(to != null){
            logsToKeepStream = logs.stream().filter(log -> log.getTimestamp().before(to));
        }
        return logsToKeepStream.collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    //currently not available, raises a RepException "the method is not yet supported"
    public void clearLogs() throws RepException {
        throw new RepException("The method is not yet supported") ;
    }
}



