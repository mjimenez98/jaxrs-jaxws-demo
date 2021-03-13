import java.sql.*

public class DBConnect {
    static final DBURL = "jsbc:mysql://localhost:8888/AlbumsDB"
    static final DBUSER = "root"
    static final DBPASS = ""

    public static Connection connect() {
        try {
            static Connection conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
            return conn;
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
