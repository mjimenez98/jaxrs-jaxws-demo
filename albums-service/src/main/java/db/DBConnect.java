package db;
import java.sql.*;

public class DBConnect {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DBURL = "jdbc:mysql://localhost:3306/AlbumsDB"; //Ensure schema is called AlbumsDB
    static final String DBUSER = "root";
    static final String DBPASS = "123";
    static Connection connect = null;

    public static Connection connect() {

        try {
            Class.forName(JDBC_DRIVER);
            connect = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            return connect;
        } catch (SQLException e){
            throw new RuntimeException("ERROR: Failed to connect.", e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("ERROR: Class not found", e);
        }
    }

    public static void disconnect() throws SQLException {
        if(connect != null){
            connect.close();
        }
    }
}
