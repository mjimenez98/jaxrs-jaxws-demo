package db;

import core.ChangeType;
import core.LogEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

public class LogGateway {
    private static String query = null;
    private static PreparedStatement st = null;

    public static String createLog(String changeType, String isrc) {
        try {
            if (changeType.equals("") || isrc.equals(""))
                return null;

            // Initialize the database
            Connection con = DBConnect.connect();

            // SQL query
            query = "INSERT INTO Logs (isrc, change_type) values(?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, isrc);
            st.setString(2, changeType);

            // Execute the insert command using executeUpdate() to make changes in database
            st.executeUpdate();

            // Close all the connections
            st.close();
            con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "Success";
    }

    public static ArrayList<LogEntry> getLogs() {
        ArrayList<LogEntry> logs = new ArrayList<>();

        try {
            // Initialize the database
            Connection con = DBConnect.connect();

            // SQL query
            query = "SELECT * FROM Logs ORDER BY created_at DESC";
            st = con.prepareStatement(query);

            ResultSet rs = st.executeQuery();

            // Go through every result in set, create Post object and add it to linked list
            while (rs.next()) {
                assert false;

                String isrc = rs.getString("isrc");
                Date createdAt = rs.getTimestamp("created_at");

                ChangeType changeType;
                String type = rs.getString("change_type");
                switch (type) {
                    case "CREATE":
                        changeType = ChangeType.CREATE;
                        break;
                    case "UPDATE":
                        changeType = ChangeType.UPDATE;
                        break;
                    case "DELETE":
                        changeType = ChangeType.DELETE;
                        break;
                    default:
                        changeType = null;
                        break;
                }

                LogEntry log = new LogEntry(
                        createdAt,
                        changeType,
                        isrc);

                logs.add(log);
            }

            // Close all connections
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }
}
