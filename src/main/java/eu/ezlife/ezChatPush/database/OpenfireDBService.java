package eu.ezlife.ezChatPush.database;

import java.sql.*;

/**
 * Created by ajo on 06.04.17.
 */

public class OpenfireDBService {

    private static PropertiesService props;

    //  Database settings
    private static String DB_URL;
    private static String USER;
    private static String PASS;

    private static final String TABLE_USERS = "ofUser";
    private static final String COLUMN_USER = "username";

    // Database connection
    private static Connection conn = null;

    public OpenfireDBService() {
        loadProperties();
    }

    private void loadProperties() {
        props = new PropertiesService();

        DB_URL = props.loadProperty("openfireDatabase");
        USER = props.loadProperty("openfireDbuser");
        PASS = props.loadProperty("openfireDbpassword");
    }

    public void connectDatabase() {
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
