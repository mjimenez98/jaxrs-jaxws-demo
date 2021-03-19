package db;

import core.*;
import exceptions.RepException;

import java.sql.*;
import java.util.ArrayList;

// Using Table Data Gateway, gateway handles all SQL for business layer.
public class AlbumGateway {
    static Connection connect = null;

    public void createAlbum(Album album){
        connect = DBConnect.connect();
        try{
            String findStr = "SELECT * FROM Albums WHERE isrc=" + album.getIsrc();
            Statement checkStatement = connect.createStatement();
            ResultSet checkTable = checkStatement.executeQuery(findStr);
            if(!checkTable.next()) {
                String insertStr = "INSERT INTO Albums (isrc, title, description, year, firstname, lastname) VALUES(?,?,?,?,?,?)";
                PreparedStatement insertStatement = connect.prepareStatement(insertStr);
                insertStatement.setString(1, album.getIsrc());
                insertStatement.setString(2, album.getTitle());
                insertStatement.setString(3, album.getDescription());
                insertStatement.setInt(4, album.getReleaseYear());
                insertStatement.setString(5, album.getArtist().getFirstName());
                insertStatement.setString(6, album.getArtist().getLastName());
                insertStatement.executeUpdate();
                System.out.println(insertStr);
            }
            else{
                throw new RepException("Album with isrc: " + album.getIsrc() + ", already exists.");
            }
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

    public void updateAlbum(Album album){
        connect = DBConnect.connect();
        try{
            String updateStr = "UPDATE Albums SET isrc=?, title=?, description=?, year=?, firstname=?, lastname=? WHERE isrc=?;";
            PreparedStatement updateStatement = connect.prepareStatement(updateStr);
            updateStatement.setString(1, album.getIsrc());
            updateStatement.setString(2, album.getTitle());
            updateStatement.setString(3, album.getDescription());
            updateStatement.setInt(4, album.getReleaseYear());
            updateStatement.setString(5, album.getArtist().getFirstName());
            updateStatement.setString(6, album.getArtist().getLastName());
            updateStatement.setString(7, album.getIsrc());
            updateStatement.executeUpdate();
            System.out.println(updateStr);
        }
        catch (Exception e){
            throw new RepException(e.getMessage());
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
        try {
            String findStr = "SELECT * FROM Albums WHERE isrc=" + isrc;
            Statement checkStatement = connect.createStatement();
            ResultSet checkTable = checkStatement.executeQuery(findStr);
            if(checkTable.next()) {
                Statement deleteStatement = connect.createStatement();
                String deleteStr = "DELETE FROM Albums WHERE isrc=" + isrc;
                deleteStatement.executeUpdate(deleteStr);
            }
            else{
                System.out.println("Album with isrc: " + isrc + ", does not exist.");
            }
        }
        catch (Exception e){
            throw new RepException(e.getMessage());
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
            String checkStr = "SELECT * FROM Albums WHERE isrc=" + isrc;
            Statement checkStatement = connect.createStatement();
            ResultSet checkTable = checkStatement.executeQuery(checkStr);
            if(checkTable.next()) {
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
                    Album album = new Album(description, isrc1, title, releaseYear, new Artist(firstname, lastname), new Cover());
                    return album;
                }
                else{
                    System.out.println("Album with isrc: " + isrc + ", does not exist.");
                }
            }
        } catch (Exception e) {
            throw new RepException(e.getMessage());
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
            throw new RepException(e.getMessage());
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

    public void updateAlbumCover(String isrc, Cover cover){
        connect = DBConnect.connect();
        try{
            String findStr = "SELECT * FROM Albums WHERE isrc=" + isrc;
            Statement checkStatement = connect.createStatement();
            ResultSet checkTable = checkStatement.executeQuery(findStr);
            if(checkTable.next()) {
                String updateStr = "UPDATE Albums SET coverart=?, mimetype=? WHERE isrc=?;";
                PreparedStatement updateStatement = connect.prepareStatement(updateStr);
                updateStatement.setBlob(1, cover.getBlob());
                updateStatement.setString(2, cover.getMimeType());
                updateStatement.setString(3, isrc);
                updateStatement.executeUpdate();
            }
            else{
                System.out.println("Album with isrc: " + isrc + ", does not exist.");
            }
        }
        catch (Exception e){
            throw new RepException(e.getMessage());
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
            String findStr = "SELECT * FROM Albums WHERE isrc=" + isrc;
            Statement checkStatement = connect.createStatement();
            ResultSet checkTable = checkStatement.executeQuery(findStr);
            if(checkTable.next()) {
                String deleteStr = "UPDATE Albums SET coverart=?, mimetype=? WHERE isrc=?";
                PreparedStatement deleteStatement = connect.prepareStatement(deleteStr);
                deleteStatement.setNull(1, java.sql.Types.BLOB);
                deleteStatement.setNull(2, Types.VARCHAR);
                deleteStatement.setString(3, isrc);
                System.out.println(deleteStatement);
                deleteStatement.executeUpdate();
                deleteStatement.executeUpdate(deleteStr);
            }
            else{
                System.out.println("Album with isrc: " +  isrc + ", does not exist.");
            }
        }
        catch (Exception e){
            throw new RepException(e.getMessage());
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
            String checkStr = "SELECT * FROM Albums WHERE isrc=" + isrc;
            Statement checkStatement = connect.createStatement();
            ResultSet checkTable = checkStatement.executeQuery(checkStr);
            if(checkTable.next()) {
                Statement findCoverStatement = connect.createStatement();
                String findStr = "SELECT coverart, mimetype FROM Albums WHERE isrc=" + isrc;
                ResultSet table = findCoverStatement.executeQuery(findStr);

                if (table.next()) {
                    Blob blob = table.getBlob("coverart");
                    String mimeType = table.getString("mimetype");
                    Cover cover = new Cover(blob.getBinaryStream(), mimeType);
                    return cover;
                }
            }
            else {
                System.out.println("Album with isrc: " + isrc + ", does not exist");
            }

        } catch (Exception e) {
            throw new RepException(e.getMessage());
        } finally {
            try {
                DBConnect.disconnect();
            } catch (SQLException e) {
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
        return null;
    }
}