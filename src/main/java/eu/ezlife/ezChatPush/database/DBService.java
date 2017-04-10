package eu.ezlife.ezChatPush.database;

import eu.ezlife.ezChatPush.beans.Token;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by ajo on 05.04.17.
 * This Class handles the Database Access
 */
public class DBService {

    // Database Tables
    private static final String TABLE_TOKENS = "tokens";

    // Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_TOKEN = "token";

    // Database connection
    private static Connection conn = null;

    private PropertiesService propertiesService;

    // Constructor initializes Database and creates tables
    public DBService() {
        initializeDatabase();
    }

    public void connectDatabase() {
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Load Properties
            propertiesService = new PropertiesService();
            Properties props = propertiesService.getProp();
            // Open a connection
            conn = DriverManager.getConnection(props.getProperty("database"), props.getProperty("dbuser"), props.getProperty("dbpassword"));
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    public void initializeDatabase() {
        connectDatabase();
        // create Tokens
        Statement stmt;

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TOKENS
                + "(" + COLUMN_ID + " INTEGER NOT NULL AUTO_INCREMENT, "
                + COLUMN_USERNAME + " VARCHAR(255), "
                + COLUMN_TOKEN + " VARCHAR(255), "
                + "PRIMARY KEY (id))";

        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        disconnectDatabase();

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

    public List<Token> getUserToken(String contactName) {
        connectDatabase();

        List<Token> tokens = new ArrayList<>();

        Statement stmt;
        ResultSet rs;

        String sql = "SELECT * FROM " + TABLE_TOKENS
                + " WHERE " + COLUMN_USERNAME + "=" + "\"" + contactName + "\"";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Token token = new Token();
                token.setId(rs.getInt(COLUMN_ID));
                token.setUsername(rs.getString(COLUMN_USERNAME));
                token.setToken(rs.getString(COLUMN_TOKEN));

                tokens.add(token);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnectDatabase();
        return tokens;
    }

    public boolean tokenExists(String contactName, String token) {
        List<Token> tokens = getUserToken(contactName);

        for (Token curToken : tokens) {
            if (curToken.getToken().equals(token)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasToken(String contactName) {
        List<Token> tokens = getUserToken(contactName);

        if (tokens != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setUserToken(Token token) {
        connectDatabase();
        Statement stmt;

        String sql = "INSERT INTO " + TABLE_TOKENS + " VALUES("
                + null + ", " + "\"" + token.getUsername() + "\""
                + ", " + "\"" + token.getToken() + "\"" + ")";

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnectDatabase();
    }

    public boolean deleteUserToken(Token token) {
        boolean isSuccessful = false;
        List<Token> tokens = getUserToken(token.getUsername());

        for (Token curToken : tokens) {
            if (curToken.getToken().equals(token.getToken())) {

                connectDatabase();
                Statement stmt;

                String sql = "DELETE FROM " + TABLE_TOKENS + " WHERE "
                        + COLUMN_USERNAME + "=" + "\"" + token.getUsername() + "\"";
                try {
                    stmt = conn.createStatement();
                    int deleted = stmt.executeUpdate(sql);
                    stmt.close();

                    if (deleted != 0) {
                        isSuccessful = true;
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                disconnectDatabase();

            }
        }
        return isSuccessful;
    }
}