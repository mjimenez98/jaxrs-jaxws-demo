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

    public Album find(int isrc) {
        Connection connect = DBConnect.connect();
        try {
            String findStr = "SELECT FROM Albums WHERE isrc=" + isrc;
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
    public void insert(int isrc, String title, String description, int releaseYear, Artist artist){
        connect = DBConnect.connect();
        try{
            Statement insertStatement = connect.createStatement();
            String insertStr = "INSERT INTO Albums (isrc, title, description, year, firstname, lastname) " +
                    "VALUES (" + isrc + ", " + title + ", " + description + ", " + releaseYear + ", " + artist.getFirstName() + ", " + artist.getLastName() + ")";
            insertStatement.executeUpdate(insertStr);
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
    public void update(int isrc, String title, String description, int releaseYear, Artist artist){
        connect = DBConnect.connect();
        try{
            Statement updateStatement = connect.createStatement();
            String updateStr = "UPDATE Albums SET isrc=" + isrc + ", " + "title=" + title + ", " + "description=" + description + ", "
                    + "year=" + releaseYear + ", " + "firstname=" + artist.getFirstName() + ", " + "lastname=" + artist.getLastName() + ", "
                    + "WHERE isrc=" + isrc;
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
    public void delete(int isrc) {
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
    public static void main(String[] args){
        AlbumGateway ag = new AlbumGateway();
        System.out.println(ag.find(1));
    }
}