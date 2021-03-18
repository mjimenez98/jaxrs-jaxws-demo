//source: https://stackoverflow.com/questions/9204287/how-to-return-a-png-image-from-jersey-rest-service-method-to-the-browser
package repo;

import core.Album;
import core.ChangeType;
import core.Cover;
import core.LogEntry;
import db.AlbumGateway;
import exceptions.RepException;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlbumManagerImpl implements AlbumManager {

    AlbumGateway ag = new AlbumGateway();
    //private ArrayList<Album> albums = new ArrayList<>();

    //Create Album
    public void createAlbum(Album newAlbum) {
        try {
            ag.createAlbum(newAlbum);
           // albums.add(newAlbum);
            logs.add(new LogEntry(new Date(), ChangeType.CREATE, newAlbum.getIsrc()));
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //Update Album
    @Override
    public void updateAlbum(Album album) {
        try {
            //deleteAlbum(album.getIsrc());
            //createAlbum(album);
            ag.updateAlbum(album);
            logs.add(new LogEntry(new Date(), ChangeType.UPDATE, album.getIsrc()));
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //Delete Album
    @Override
    public void deleteAlbum(String isrc) {
        try {
            /*
            albums = albums.stream()
                    .filter(album -> !album.getIsrc().equals(isrc))
                    .collect(Collectors.toCollection(ArrayList::new));
            logs.add(new LogEntry(new Date(), ChangeType.DELETE, isrc));
            */
            ag.deleteAlbum(isrc);
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //Get Album Info
    @Override
    public Album getAlbum(String isrc) {
        try {
            /*
            Album album = albums.stream()
                    .filter(album1 -> album1.getIsrc().equals(isrc))
                    .findAny().orElse(null);
             */
            return  ag.getAlbumInfo(isrc);
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    //Get Album List
    @Override
    public ArrayList<Album> getAlbums() {
        return ag.getAlbumsList();
    }


    //Update Album Cover Image
    @Override
    public void updateAlbumCoverImage(InputStream newCover, String isrc, MediaType md){
        try {
            /*
            int read = 0;
            byte[] bytes = new byte[1024];
            FileOutputStream out = new FileOutputStream(location);
            while((read = newCover.read(bytes)) != -1){
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
            */
            Album album = getAlbum(isrc);
            if (album != null) {
                ag.updateAlbumCover(isrc, new Cover(newCover, md.toString()));
                //album.setCover(new Cover(newCover, md.toString()));
                logs.add(new LogEntry(new Date(), ChangeType.UPDATE, isrc));
            }
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }


    //Delete Album Cover Image
    @Override
    public void deleteAlbumCoverImage(String isrc){
        try {
            /*
            Album album = getAlbum(isrc);
            System.out.println(album.toString());
            album.setCover(new Cover("", null));
            */
            ag.deleteAlbumCover(isrc);
            logs.add(new LogEntry(new Date(), ChangeType.UPDATE, isrc));
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }


    //Get Album Cover Image
    @Override
    public Cover getAlbumCoverImage(String isrc) {
        try {
            /*
            Album album = getAlbum(isrc);
            if (album != null) {
                Cover cover = album.getCover();
                return cover;
            }
            return null;
            */
            return ag.getAlbumCover(isrc);
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    public ArrayList<LogEntry> logs = new ArrayList<>();

    @Override
    // If no parameters are given, all change logs are returned
    public ArrayList<LogEntry> getChangeLogs(Date from, Date to, ChangeType changeType){
        if (from == null && to == null && changeType == null)
            return logs;

        Stream<LogEntry> logsToKeepStream = logs.stream();
        if (changeType != null) {
            logsToKeepStream = logsToKeepStream.filter(log -> log.getType() == changeType);
        }
        if (from != null) {
            logsToKeepStream = logsToKeepStream.filter(log -> log.getTimestamp().after(from));
        }
        if (to != null){
            logsToKeepStream = logsToKeepStream.filter(log -> log.getTimestamp().before(to));
        }

        return logsToKeepStream.collect(Collectors.toCollection(ArrayList::new));
    }
    @Override
    //currently not available, raises a RepException "the method is not yet supported"
    public void clearLogs() throws RepException {
        throw new RepException("The method is not yet supported");
    }
}
