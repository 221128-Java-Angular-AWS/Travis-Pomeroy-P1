package project.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    private static Connection c;

    private static Connection connectionBuilder() {

        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("login.properties");
            Properties props = new Properties();
            props.load(input);

            String user     = props.getProperty("user");
            String pass     = props.getProperty("pass");
            String url      = props.getProperty("url");
            String driver   = props.getProperty("driver");

            Class.forName(driver);
            c = DriverManager.getConnection(url, user, pass);
            System.out.println("Opened database successfully");
            return c;

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unable to find file");

        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to connect to database");

        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Unable to find driver");

        }
        return c;
    }

    public static Connection getConnection() {
        if (c == null)
            return connectionBuilder();
        return c;
    }

    public static void databaseClose () {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("No Database to Close");
        }
    }
}
