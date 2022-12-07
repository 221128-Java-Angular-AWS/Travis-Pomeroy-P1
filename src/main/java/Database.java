package src.main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Database {

    private static Connection c;

    static String user;
    static String pass;
    static String url;

    private static Connection databaseConnect() {

        Properties props = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("login.properties");

        try {
            props.load(inputStream);

            user = props.getProperty("user");
            pass = props.getProperty("pass");
            url  = props.getProperty("url");

            props.load(inputStream);

            System.out.println(props.getProperty("user"));
            System.out.println(user);

            try {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(url, user, pass);
                System.out.println("Opened database successfully");
                return c;

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    /*
    try {
        File file = new File("src/main/resources/login.properties");
        Scanner scan = new Scanner(file);

        user = scan.nextLine();
        pass = scan.nextLine();
        url  = scan.nextLine();
*/

        /*
    } catch (FileNotFoundException e){
        System.out.println("No file!");
        e.printStackTrace();
    }
    */
    return c;

}

    static void databaseClose () {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("No src.main.java.Database to Close");
        }

    }
}
