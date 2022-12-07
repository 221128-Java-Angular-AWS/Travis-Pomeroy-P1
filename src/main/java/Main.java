import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String... args) {

        Database.databaseConnect();

        System.out.println("====================================");
        System.out.println("Welcome to the Reimbursement System!");
        System.out.println("====================================");

        System.out.println("Press 1 to log in");
        System.out.println("Press 2 to register");
        System.out.println("Press q to quit");
            //put a SQL statement here to use the P1DB ex: USE P1DB;
        Scanner scan = new Scanner(System.in);

        String string = scan.nextLine();


        Database.databaseClose();
    }
}
