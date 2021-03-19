//source: https://stackoverflow.com/questions/9204287/how-to-return-a-png-image-from-jersey-rest-service-method-to-the-browser
package repo;

import core.Album;
import core.ChangeType;
import core.Cover;
import core.LogEntry;
import db.AlbumGateway;
import db.LogGateway;
import exceptions.RepException;
import sun.rmi.runtime.Log;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AlbumManagerImpl implements AlbumManager {

    AlbumGateway ag = new AlbumGateway();

    //Create Album
    public void createAlbum(Album newAlbum) {
        try {
            ag.createAlbum(newAlbum);
            LogGateway.createLog(ChangeType.CREATE.toString(), newAlbum.getIsrc());
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //Update Album
    @Override
    public void updateAlbum(Album album) {
        try {
            ag.updateAlbum(album);
            LogGateway.createLog(ChangeType.UPDATE.toString(), album.getIsrc());
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //Delete Album
    @Override
    public void deleteAlbum(String isrc) {
        try {
            ag.deleteAlbum(isrc);
            LogGateway.createLog(ChangeType.DELETE.toString(), isrc);
        }
        catch(Exception e) {
            throw new RepException(e.getMessage());
        }
    }

    //Get Album Info
    @Override
    public Album getAlbum(String isrc) {
        try {
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
            Album album = getAlbum(isrc);
            if (album != null) {
                ag.updateAlbumCover(isrc, new Cover(newCover, md.toString()));
                LogGateway.createLog(ChangeType.UPDATE.toString(), isrc);
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
            ag.deleteAlbumCover(isrc);
            LogGateway.createLog(ChangeType.UPDATE.toString(), isrc);
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }


    //Get Album Cover Image
    @Override
    public Cover getAlbumCoverImage(String isrc) {
        try {
            return ag.getAlbumCover(isrc);
        }
        catch(Exception e){
            throw new RepException(e.getMessage());
        }
    }

    @Override
    // If no parameters are given, all change logs are returned
    public ArrayList<LogEntry> getChangeLogs(Date from, Date to, ChangeType changeType) {
        ArrayList<LogEntry> logs = LogGateway.getLogs();

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
