package db;
import core.*;
import java.sql.*;
import java.util.ArrayList;

//International Standard Recording Code (ISRC): unique
//Title
//Content description: optional
//Release year
//Artist's first and last names^
//Cover image: optional^^

//Using Table Data Gateway, gateway handles all SQL for business layer.

public class AlbumGateway {
    static Connection connect = null;

    //Returns an album object
    public Album find(String isrc) {
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

    //Returns an ArrayList of album objects
    public ArrayList <Album> findAll(){
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
                Album album = new Album(isrc,title,description,releaseYear, new Artist(firstname,lastname), new Cover());
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
    public void insert(String isrc, String title, String description, int releaseYear, Artist artist){
        connect = DBConnect.connect();
        try{
            Statement insertStatement = connect.createStatement();
            String insertStr = "INSERT INTO Albums (isrc, title, description, year, firstname, lastname) " +
                    "VALUES ('" + isrc + "', '" + title + "', '" + description + "', " + releaseYear + ", '" + artist.getFirstName() + "', '" + artist.getLastName() + "');";
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
    public void update(String isrc, String title, String description, int releaseYear, Artist artist){
        connect = DBConnect.connect();
        try{
            Statement updateStatement = connect.createStatement();
            String updateStr = "UPDATE Albums SET isrc='" + isrc + "', " + "title='" + title + "', " + "description='" + description + "', "
                    + "year=" + releaseYear + ", " + "firstname='" + artist.getFirstName() + "', " + "lastname='" + artist.getLastName() + "' "
                    + "WHERE isrc=" + isrc + ";";
            System.out.println(updateStr);
                    updateStatement.executeUpdate(updateStr);

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
    public void delete(String isrc) {
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
    // For Testing
    public static void main(String[] args) {
        //Declaring AlbumGateway Object
        //AlbumGateway ag = new AlbumGateway();
        //Find Album by ISRC
        //System.out.println(ag.find(1).getReleaseYear());
        //Find All Albums
        //System.out.println(ag.findAll().get(0).getReleaseYear());
        //Insert Values into Albums Table
        //ag.insert("5","title5","Description 5", 5555, new Artist("firstfive","lastfive"));
        //Update Values of a specific Album in the Albums Table
        //ag.update("5","title5","Description 5", 5555, new Artist("NEWfirstfive","NEWlastfive"));
        //Delete specific Album using ISRC from Albums Table
        //ag.delete("5");
    }
}