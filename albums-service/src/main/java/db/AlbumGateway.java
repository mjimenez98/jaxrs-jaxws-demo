package db;
import core.*;

import javax.activation.MimeType;
import javax.print.attribute.standard.Media;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;

//Using Table Data Gateway, gateway handles all SQL for business layer.
public class AlbumGateway {
    static Connection connect = null;

    static public String extractMimeType(String pathname) {

        switch (pathname.substring(pathname.lastIndexOf(".") + 1)) {
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            default:
                return null;
        }
    }
    public void createAlbum(String isrc, String title, String description, int releaseYear, Artist artist){
        connect = DBConnect.connect();
        try{

            String insertStr = "INSERT INTO Albums (isrc, title, description, year, firstname, lastname) VALUES(?,?,?,?,?,?)";
            PreparedStatement insertStatement = connect.prepareStatement(insertStr);
            insertStatement.setString(1, isrc);
            insertStatement.setString(2, title);
            insertStatement.setString(3, description);
            insertStatement.setInt(4, releaseYear);
            insertStatement.setString(5, artist.getFirstName());
            insertStatement.setString(6, artist.getLastName());
            insertStatement.executeUpdate();
            System.out.println(insertStr);

        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public void createAlbumWithCover(String isrc, String title, String description, int releaseYear, Artist artist, String pathname){
        connect = DBConnect.connect();
        try{
            String insertStr = "INSERT INTO Albums (isrc, title, description, year, firstname, lastname, coverart, mimetype) VALUES(?,?,?,?,?,?,?,?)";
            File artFile = new File(pathname);
            String mimeType = extractMimeType(pathname);
            FileInputStream fileIn = new FileInputStream(artFile);
            PreparedStatement insertStatement = connect.prepareStatement(insertStr);
            insertStatement.setString(1, isrc);
            insertStatement.setString(2, title);
            insertStatement.setString(3, description);
            insertStatement.setInt(4, releaseYear);
            insertStatement.setString(5, artist.getFirstName());
            insertStatement.setString(6, artist.getLastName());
            insertStatement.setBinaryStream(7, fileIn);
            insertStatement.setString(8, mimeType);
            insertStatement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public void updateAlbum(String isrc, String title, String description, int releaseYear, Artist artist){
        connect = DBConnect.connect();
        try{
            String updateStr = "UPDATE Albums SET isrc=?, title=?, description=?, year=?, firstname=?, lastname=? WHERE isrc=?;";
            PreparedStatement updateStatement = connect.prepareStatement(updateStr);
            updateStatement.setString(1, isrc);
            updateStatement.setString(2, title);
            updateStatement.setString(3, description);
            updateStatement.setInt(4, releaseYear);
            updateStatement.setString(5, artist.getFirstName());
            updateStatement.setString(6, artist.getLastName());
            updateStatement.setString(7, isrc);
            updateStatement.executeUpdate();
            System.out.println(updateStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public void deleteAlbum(String isrc) {
        connect = DBConnect.connect();
        try{
            Statement deleteStatement = connect.createStatement();
            String deleteStr = "DELETE FROM Albums WHERE isrc=" + isrc;
            deleteStatement.executeUpdate(deleteStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public Album getAlbumInfo(String isrc) {
        connect = DBConnect.connect();
        try {
            String findStr = "SELECT * FROM Albums WHERE isrc=" + isrc;
            Statement findAllStatement = connect.createStatement();
            ResultSet table = findAllStatement.executeQuery(findStr);

            if (table.next()) {
                String isrc1 = table.getString("isrc");
                String title = table.getString("title");
                String description = table.getString("description");
                int releaseYear = table.getInt("year");
                String firstname = table.getString("firstname");
                String lastname = table.getString("lastname");
                Album album = new Album(isrc1, title, description, releaseYear, new Artist(firstname, lastname), new Cover());
                return album;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBConnect.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
        return null;
    }
    public ArrayList <Album> getAlbumsList(){
        connect = DBConnect.connect();
        ArrayList<Album> albums = new ArrayList<>();

        try {
            String findAllStr =  "SELECT * FROM Albums";
            Statement findAllStatement = connect.createStatement();
            ResultSet table = findAllStatement.executeQuery(findAllStr);

            while(table.next()){
                String isrc = table.getString("isrc");
                String title = table.getString("title");
                String description = table.getString("description");
                int releaseYear = table.getInt("year");
                String firstname = table.getString("firstname");
                String lastname = table.getString("lastname");
                Album album = new Album(isrc,title,description,releaseYear, new Artist(firstname,lastname), new Cover()); //
                albums.add(album);
            }
            return albums;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
        return null;
    }
    public void updateAlbumCover(String isrc, String pathname){
        connect = DBConnect.connect();
        try{
            String updateStr = "UPDATE Albums SET coverart=?, mimetype=? WHERE isrc=?;";
            File artFile = new File(pathname);
            String mimeType = extractMimeType(pathname);
            FileInputStream fileIn = new FileInputStream(artFile);
            PreparedStatement updateStatement = connect.prepareStatement(updateStr);
            updateStatement.setBinaryStream(1, fileIn);
            updateStatement.setString(2, mimeType);
            updateStatement.setString(3, isrc);
            updateStatement.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public void deleteAlbumCover(String isrc) {
        connect = DBConnect.connect();
        try{
            String deleteStr = "UPDATE Albums SET coverart=? WHERE isrc=?;";
            PreparedStatement deleteStatement = connect.prepareStatement(deleteStr);
            deleteStatement.setBinaryStream(1, null);
            deleteStatement.setString(2, isrc);
            deleteStatement.executeUpdate();
            deleteStatement.executeUpdate(deleteStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public Cover getAlbumCover(String isrc) {
        connect = DBConnect.connect();
        try {
            Statement findCoverStatement = connect.createStatement();
            String findStr = "SELECT coverart FROM Albums WHERE isrc=" + isrc;
            ResultSet table = findCoverStatement.executeQuery(findStr);

            if(table.next()){
                Blob blob = table.getBlob("coverart");
                String mimeType = table.getString("mimetype");
                Cover cover = new Cover(blob,mimeType);
                return cover;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                DBConnect.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
        return null;
    }

    // For Testing
    public static void main(String[] args) {
        //Declaring AlbumGateway Object
        AlbumGateway ag = new AlbumGateway();
        //Find Album by ISRC
        //System.out.println(ag.getAlbumInfo("1").getReleaseYear());
        //Find All Albums
        //System.out.println(ag.getAlbumList().get(4).getCover().getFile());
        //Insert Values into Albums Table
        //ag.createAlbum("5","title5","Description 5", 5555, new Artist("firstfive","lastfive"));
        //ag.createAlbumWithCover("5","title5","Description 5", 5555, new Artist("firstfive","lastfive"), "/Users/username/Desktop/sampleCoverArt.jpeg");
        //Update Values of a specific Album in the Albums Table
        //ag.updateAlbum("5","title5","Description 5", 5555, new Artist("NEWfirstfive","NEWlastfive"));
        //Delete specific Album using ISRC from Albums Table
        //ag.deleteAlbum("5");
        //GetAlbumCover
        //System.out.println(ag.getAlbumCover("5"));
        //System.out.println(ag.getAlbumCover("5").getMimeType());

    }
}