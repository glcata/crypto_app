package crypto.sql;

// * @author Catalin Glavan

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnect {

    public static Connection con;
    public static Statement st;
    private static String username;
    private static String password;
    private static String database;

    public void DBConnectSQL(String user, String pass, String db) {
        try {
            DBConnect.username = user;
            DBConnect.password = pass;
            DBConnect.database = db;
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://localhost:5432/" + database;
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            st = con.createStatement();
            PreparedStatement create = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS USERS ("
                    + "id SERIAL,"
                    + " name varchar(30),"
                    + " email varchar(30),"
                    + " captcha_code varchar(30),"
                    + " encryption bytea,"
                    + " timestamp timestamp with time zone default current_timestamp,"
                    + "PRIMARY KEY(id))");
            create.executeUpdate();
            create = con.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS history ("
                    + " id SERIAL,"
                    + " user_email varchar(30),"
                    + " mode varchar(30),"
                    + " file_name varchar(30),"
                    + " file_encrypted bytea,"
                    + " date timestamp default current_timestamp,"
                    + " PRIMARY KEY(id))");
            create.executeUpdate();
            System.out.println("PostgreeSQL CONNECTED !");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        DBConnect.con = con;
    }

    public static Statement getSt() {
        return st;
    }

    public static void setSt(Statement st) {
        DBConnect.st = st;
    }
}
