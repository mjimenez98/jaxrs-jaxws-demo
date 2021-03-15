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

    public Album find(int isrc){
        Connection connect = DBConnect.connect();
        try{
            String findStr = "SELECT FROM Albums WHERE isrc=" + isrc;
            Statement findAllStatement = connect.createSatement();
            ResultSet table = findAllStatement.executeQuery(findStr);
        }

        if (table.next()){
            String isrc = table.getString("isrc");
            String title = table.getString("title");
            String description = table.getString("description");
            int year = table.getInt("year");
            String firstname = table.getString("firstname");
            String firstname = table.getString("lastname");
            Album album = new Album(isrc,title,description,releaseYear, new Artist(firstname,lastname));
            return album
        }

        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            connect = DBConnect.disconnect();
        }

    }
    public ArrayList <Album> findAll(){
        Connection connect = DBConnect.connect();
        ArrayList<Album> albums = new ArrayList<>();

        try {
            String findAllStr =  "SELECT * FROM Albums";
            Statement findAllStatement = connect.createSatement();
            ResultSet table = findAllStatement.executeQuery(findAllStr);

            while(table.next()){
                String isrc = table.getString("isrc");
                String title = table.getString("title");
                String description = table.getString("description");
                int year = table.getInt("year");
                String firstname = table.getString("firstname");
                String firstname = table.getString("lastname");
                Album album = new Album(isrc,title,description,releaseYear, new Artist(firstname,lastname));
                albums.add(album);
            }

            return albums;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            connect = DBConnect.disconnect();
        }
    }
    public void insert(int isrc, String title, String description, int releaseYear, Artist artist){
        Connection connect = DBConnect.connect();
        try{
            Statement insertStatement = connect.createSatement();
            String insertStr = "INSERT INTO Albums (isrc, title, description, year, firstname, lastname) " +
                    "VALUES (" + isrc + ", " + title + ", " + description + ", " + releaseYear + ", " + artist.getFirstName + ", " + artist.getLastName + ")"
            insertStatement.executeUpdate(insertStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            connect = DBConnect.disconnect();
        }
    }
    public void update(int isrc, String title, String description, int releaseYear, Artist artist){
        Connection connect = DBConnect.connect();
        try{
            Statement updateStatement = connect.createSatement();
            String updateStr = "UPDATE Albums SET isrc=" + isrc + ", " + "title=" + title + ", " + "description=" + description + ", "
                    + "year=" + releaseYear + ", " + "firstname=" + artist.getFirstName + ", " + "lastname=" + artist.getLastName + ", "
                    + "WHERE isrc=" + isrc;
                    updateStatement.executeUpdate(updateStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            connect = DBConnect.disconnect();
        }
    }
    public void delete(int isrc) {
        Connection connect = DBConnect.connect();
        try{
            Statement deleteStatement = connect.createSatement();
            String deleteStr = "DELETE FROM Albums WHERE isrc=" + isrc;
            deleteStatement.executeUpdate(deleteStr);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally{
            connect = DBConnect.disconnect();
        }
    }
}