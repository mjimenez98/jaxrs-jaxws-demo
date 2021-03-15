package db;
import java.sql.*;
import java.util.List;

public class DBConnect {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final DBURL = "jsbc:mysql://localhost:3306/AlbumsDB"; //Ensure schema is called AlbumsDB
    static final DBUSER = "root";
    static final DBPASS = "";

    public static Connection connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            static Connection connect = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            return connect;
        } catch (SQLException e){
            throw new RuntimeException("ERROR: Failed to connect.", e);
        } catch (ClassNotFoundException e){
            throw new RuntimeException("ERROR: Class not found", e);
        }
    }

    public static void disconnect() throws SQLException {
        if(conn != null){
            conn.close();
        }
    }
}
