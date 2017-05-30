package dataaccess.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Bertram
public class DBConnection {
    private String url;
    private String user;
    private String password;

    //Takes Endpoint, username and password for Database.
    public DBConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    //Creates connection to DB
    public Connection getConnection(){
        try {
            return DriverManager.getConnection(url, user, password);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}