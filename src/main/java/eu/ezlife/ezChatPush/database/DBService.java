package eu.ezlife.ezChatPush.database;

import eu.ezlife.ezChatPush.beans.AppID;
import eu.ezlife.ezChatPush.beans.Token;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajo on 05.04.17.
 *
 * This Class handles the Database Access
 */
public class DBService {

    //  Database settings
    private static final String DB_URL = "jdbc:mysql://localhost/ezchat";
    private static final String USER = "ezchat";
    private static final String PASS = "ezchatpw";

    // Database Tables
    private static final String TABLE_TOKENS = "tokens";
    private static final String TABLE_APPID = "appid";

    // Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_RESOURCE = "resource";
    private static final String COLUMN_TOKEN = "token";
    private static final String COLUMN_APPID = "appid";

    // Database connection
    private static Connection conn = null;

    // Constructor initializes Database and creates tables
    public DBService() {
        connectDatabase();
        initializeDatabase();
        disconnectDatabase();
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

    public void initializeDatabase() {
        // create Tokens and AppId Table
        Statement stmt;

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TOKENS
                + "(" + COLUMN_ID + " INTEGER NOT NULL AUTO_INCREMENT, "
                + COLUMN_USERNAME + " VARCHAR(255), "
                + COLUMN_RESOURCE + " VARCHAR(255), "
                + COLUMN_TOKEN + " VARCHAR(255), "
                + "PRIMARY KEY (id))";

        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_APPID
                + "(" + COLUMN_ID + " INTEGER NOT NULL AUTO_INCREMENT, "
                + COLUMN_APPID + " VARCHAR(255), "
                + "PRIMARY KEY (id))";

        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.execute(sql2);
            stmt.close();
        } catch (SQLException e) {
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

    public AppID getAppID() {
        connectDatabase();

        Statement stmt;
        ResultSet rs;
        AppID appID = new AppID();

        String sql = "SELECT * FROM " + TABLE_APPID + " LIMIT 1";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                appID.setId(rs.getInt(COLUMN_ID));
                appID.setAppId(rs.getString(COLUMN_APPID));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnectDatabase();
        return appID;
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

            while(rs.next()) {
                Token token = new Token();
                token.setId(rs.getInt(COLUMN_ID));
                token.setUsername(rs.getString(COLUMN_USERNAME));
                token.setResource(rs.getString(COLUMN_RESOURCE));
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

        if(tokens != null) {
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
                + ", " + "\"" + token.getResource() + "\""
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

        for(Token curToken : tokens) {
            if(curToken.getToken().equals(token.getToken())) {

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