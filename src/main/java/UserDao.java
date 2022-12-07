package src.main.java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserDao {

    public Set<User> getAllUsers() {

        //Connects to the database and prepares the SQL statement
        Connection connection = Database.databaseConnect();
        String sql = "SELECT * FROM users";
        Set<User> users = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("userID"));
                user.setEmail(rs.getString("email"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setPassphrase(rs.getString("passphrase"));
                user.setRole(rs.getString("role"));

                users.add(user);
            }
            connection.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return users;
    }

    public User checkLogin () {
        Connection connection = Database.databaseConnect();

        Scanner scan = new Scanner(System.in);

        String user;
        String pass;
        Boolean notLogin = true;
        User loginUser = new User();

        System.out.println("Please log in");

        //will repeat until a successful login
        do {
            user = scan.nextLine();
            pass = scan.nextLine();

            String sql = "SELECT * FROM users WHERE email = '" + user + "' AND passphrase = '" + pass + "'";

            try {
                PreparedStatement pstmt = connection.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();

                // if the query is not empty will fill in the fields for the user
                if (rs.next()) {
                    loginUser.setUserId(rs.getInt("userID"));
                    loginUser.setEmail(rs.getString("email"));
                    loginUser.setFirstname(rs.getString("firstname"));
                    loginUser.setLastname(rs.getString("lastname"));
                    loginUser.setPassphrase(rs.getString("passphrase"));
                    loginUser.setRole(rs.getString("role"));

                    notLogin = false;
                } else {
                    System.out.println("Incorrect Username or Password");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (notLogin);

        return loginUser;
    }

    public int register() {
        Connection connection = Database.databaseConnect();
        String sql = "INSERT INTO users (email, firstname, lastname, passphrase) VALUES (?,?,?,?)";
        Integer result = -1;

        Scanner scan = new Scanner(System.in);

        System.out.println("Please type email, firstname, lastname, then password");
        String email    = scan.nextLine();
        String first    = scan.nextLine();
        String last     = scan.nextLine();
        String pass     = scan.nextLine();

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstname(first);
        newUser.setLastname(last);
        newUser.setPassphrase(pass);
        try{

            String sql2 = "SELECT * FROM users WHERE email = '" + email + "'";
            PreparedStatement pstmt = connection.prepareStatement(sql2);
            ResultSet rs = pstmt.executeQuery();

            //Checking if the email parameter is valid for the database
            if (email.isEmpty()) {
                System.out.println("Unable to create new user");
                return result;
            }   else if (rs.next()) {
                System.out.println("Email already exists!");
                return result;
            }


            PreparedStatement pstmt2 = connection.prepareStatement(sql);
            pstmt2.setString(1,newUser.getEmail());
            pstmt2.setString(2,newUser.getFirstname());
            pstmt2.setString(3,newUser.getLastname());
            pstmt2.setString(4,newUser.getPassphrase());
            result = pstmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int alterUserRole(Integer id, String role) {
        Connection connection = Database.databaseConnect();
        String sql = "UPDATE users SET role = '" + role + "' WHERE userID = '" + id + "'";
        Integer result = -1;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
