package eu.ezlife.ezChatPush.database;

import java.sql.*;
import java.util.Properties;

/**
 * Created by ajo on 06.04.17.
 */

public class OpenfireDBService {

    private static final String TABLE_USERS = "ofUser";
    private static final String COLUMN_USER = "username";

    private PropertiesService propertiesService;

    // Database connection
    private static Connection conn = null;

    public OpenfireDBService() {}

    public void connectDatabase() {
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Open a connection
            propertiesService = new PropertiesService();
            Properties props = propertiesService.getProp();
            conn = DriverManager.getConnection(props.getProperty("openfireDatabase"), props.getProperty("openfireDbuser"), props.getProperty("openfireDbpassword"));
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();

        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void disconnectDatabase() {
        // close open connection
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public boolean findUser(String username) {
        boolean hasUser = true;
        connectDatabase();

        Statement stmt;
        ResultSet rs;

        String sql = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER + "=" + "\"" + username + "\"" + " LIMIT 1";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            if (!rs.next()) {
                hasUser = false;
                System.out.println(hasUser);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnectDatabase();
        return hasUser;
    }

}
