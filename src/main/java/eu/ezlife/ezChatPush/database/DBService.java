package eu.ezlife.ezChatPush.database;

/**
 * Created by ajo on 05.04.17.
 */

import eu.ezlife.ezChatPush.beans.AppID;
import eu.ezlife.ezChatPush.beans.Token;

import java.sql.*;

public class DBService {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/ezchat";

    //  Database credentials
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

    public Token getUserToken(String contactName) {
        connectDatabase();

        Statement stmt;
        ResultSet rs;
        Token token = new Token();

        String sql = "SELECT * FROM " + TABLE_TOKENS
                + " WHERE " + COLUMN_USERNAME + "=" + "\"" + contactName + "\"";

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                token.setId(rs.getInt(COLUMN_ID));
                token.setUsername(rs.getString(COLUMN_USERNAME));
                token.setResource(rs.getString(COLUMN_RESOURCE));
                token.setToken(rs.getString(COLUMN_TOKEN));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        disconnectDatabase();
        return token;
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

    public boolean deleteUserToken(String contactName) {
        boolean isSuccessful = false;

        connectDatabase();
        Statement stmt;

        String sql = "DELETE FROM " + TABLE_TOKENS + " WHERE "
                + COLUMN_USERNAME + "=" + "\"" + contactName + "\"";
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
        return isSuccessful;
    }

}