package src.main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Database {

    private static Connection c;

    static Connection databaseConnect() {

        try {
            File file = new File("src/main/resources/login.properties");
            Scanner scan = new Scanner(file);

            String user = scan.nextLine();
            String pass = scan.nextLine();
            String url  = scan.nextLine();

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
        } catch (FileNotFoundException e){
            System.out.println("No file!");
            e.printStackTrace();
        }
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
