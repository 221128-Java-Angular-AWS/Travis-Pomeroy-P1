package src.main.java.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {

    private static Connection c;

    static Connection databaseConnect() {

        if (c == null) {
            try {
                File file = new File("src/main/resources/login.properties");
                Scanner scan = new Scanner(file);

                String user = scan.nextLine();
                String pass = scan.nextLine();
                String url = scan.nextLine();
                String driver = scan.nextLine();

                try {
                    Class.forName(driver);
                    c = DriverManager.getConnection(url, user, pass);
                    System.out.println("Opened database successfully");
                    return c;

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println(e.getClass().getName() + ": " + e.getMessage());
                    System.exit(0);

                }
            } catch (FileNotFoundException e) {
                System.out.println("No file!");
                e.printStackTrace();
            }
        }
        return c;

    }

    static void databaseClose () {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println("No Database to Close");
        }

    }
}
