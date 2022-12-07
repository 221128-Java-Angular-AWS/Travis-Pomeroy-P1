import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Database {

    private static Connection c;

    private static String user;
    private static String pass;
    private static String url;

    public static Connection databaseConnect() {

        try {
            File file = new File("login.properties");
            Scanner scan = new Scanner(file);

            user = scan.nextLine();
            pass = scan.nextLine();
            url  = scan.nextLine();

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
            System.out.println("No Database to Close");
        }

    }
}
