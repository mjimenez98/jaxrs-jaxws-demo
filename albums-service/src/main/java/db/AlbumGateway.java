package db;

import core.*;
import exceptions.RepException;

import java.sql.*;
import java.util.ArrayList;

// Using Table Data Gateway, gateway handles all SQL for business layer.
public class AlbumGateway {
    static Connection connect = null;
    private static String query = null;
    private static PreparedStatement st = null;

    public void createAlbum(Album album){
        try {
            connect = DBConnect.connect();

            query = "SELECT * FROM albums WHERE isrc = ?";
            st = connect.prepareStatement(query);
            st.setString(1, album.getIsrc());
            ResultSet checkTable = st.executeQuery();

            if (!checkTable.next()) {
                query = "INSERT INTO albums (isrc, title, description, year, firstname, lastname) VALUES(?,?,?,?,?,?)";
                PreparedStatement insertStatement = connect.prepareStatement(query);

                insertStatement.setString(1, album.getIsrc());
                insertStatement.setString(2, album.getTitle());
                insertStatement.setString(3, album.getDescription());
                insertStatement.setInt(4, album.getReleaseYear());
                insertStatement.setString(5, album.getArtist().getFirstName());
                insertStatement.setString(6, album.getArtist().getLastName());
                insertStatement.executeUpdate();

                System.out.println(query);
            }
            else {
                throw new RepException("Album with isrc: " + album.getIsrc() + ", already exists.");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }

    public void updateAlbum(Album album){
        try {
            connect = DBConnect.connect();

            query = "UPDATE albums SET isrc=?, title=?, description=?, year=?, firstname=?, lastname=? WHERE isrc=?";
            st = connect.prepareStatement(query);

            st.setString(1, album.getIsrc());
            st.setString(2, album.getTitle());
            st.setString(3, album.getDescription());
            st.setInt(4, album.getReleaseYear());
            st.setString(5, album.getArtist().getFirstName());
            st.setString(6, album.getArtist().getLastName());
            st.setString(7, album.getIsrc());
            st.executeUpdate();

            System.out.println(query);
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
        try {
            connect = DBConnect.connect();

            query = "SELECT * FROM albums WHERE isrc = ?";
            st = connect.prepareStatement(query);
            st.setString(1, isrc);
            ResultSet checkTable = st.executeQuery();

            if (checkTable.next()) {
                query = "DELETE FROM albums WHERE isrc=?";
                st = connect.prepareStatement(query);
                st.setString(1, isrc);

                st.executeUpdate();
            }
            else {
                System.out.println("Album with isrc: " + isrc + ", does not exist.");
            }
        }
        catch (Exception e) {
            throw new RepException(e.getMessage());
        }
        finally {
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
            query = "SELECT * FROM albums WHERE isrc = ?";
            st = connect.prepareStatement(query);
            st.setString(1, isrc);
            ResultSet checkTable = st.executeQuery();

            if (checkTable.next()) {
                query = "SELECT * FROM albums WHERE isrc = ?";
                st = connect.prepareStatement(query);
                st.setString(1, isrc);
                ResultSet table = st.executeQuery();

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
                else {
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
        ArrayList<Album> albums = new ArrayList<>();

        try {
            connect = DBConnect.connect();

            query =  "SELECT * FROM albums";
            st = connect.prepareStatement(query);
            ResultSet table = st.executeQuery();

            while(table.next()) {
                String isrc = table.getString("isrc");
                String title = table.getString("title");
                String description = table.getString("description");
                int releaseYear = table.getInt("year");
                String firstname = table.getString("firstname");
                String lastname = table.getString("lastname");

                Album album = new Album(description,isrc,title,releaseYear, new Artist(firstname,lastname), new Cover());
                albums.add(album);
            }
            return albums;
        }
        catch (Exception e) {
            throw new RepException(e.getMessage());
        }
        finally {
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e) {
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }

    public void updateAlbumCover(String isrc, Cover cover) {
        try {
            connect = DBConnect.connect();

            query = "SELECT * FROM albums WHERE isrc = ?";
            st = connect.prepareStatement(query);
            st.setString(1, isrc);
            ResultSet checkTable = st.executeQuery();

            if(checkTable.next()) {
                query = "UPDATE albums SET coverart=?, mimetype=? WHERE isrc=?;";
                st = connect.prepareStatement(query);
                st.setBlob(1, cover.getBlob());
                st.setString(2, cover.getMimeType());
                st.setString(3, isrc);
                st.executeUpdate();
            }
            else {
                System.out.println("Album with isrc: " + isrc + ", does not exist.");
            }
        }
        catch (Exception e) {
            throw new RepException(e.getMessage());
        }
        finally {
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e){
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public void deleteAlbumCover(String isrc) {
        try {
            connect = DBConnect.connect();

            query = "SELECT * FROM albums WHERE isrc = ?";
            st = connect.prepareStatement(query);
            st.setString(1, isrc);
            ResultSet checkTable = st.executeQuery();

            if(checkTable.next()) {
                query = "UPDATE albums SET coverart=?, mimetype=? WHERE isrc=?";
                st = connect.prepareStatement(query);
                st.setNull(1, java.sql.Types.BLOB);
                st.setNull(2, Types.VARCHAR);
                st.setString(3, isrc);
                st.executeUpdate();

                System.out.println(query);
            }
            else {
                System.out.println("Album with isrc: " +  isrc + ", does not exist.");
            }
        }
        catch (Exception e) {
            throw new RepException(e.getMessage());
        }
        finally {
            try {
                DBConnect.disconnect();
            }
            catch (SQLException e) {
                throw new RuntimeException("ERROR: Failed to connect.", e);
            }
        }
    }
    public Cover getAlbumCover(String isrc) {
        try {
            connect = DBConnect.connect();

            query = "SELECT coverart, mimetype FROM albums WHERE isrc = ?";
            st = connect.prepareStatement(query);
            st.setString(1, isrc);
            ResultSet checkTable = st.executeQuery();

            if(checkTable.next()) {
                Blob blob = checkTable.getBlob("coverart");
                String mimeType = checkTable.getString("mimetype");

                return new Cover(blob.getBinaryStream(), mimeType);
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
