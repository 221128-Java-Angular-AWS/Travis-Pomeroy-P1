package project.persistence;

import project.exceptions.PasswordIncorrectException;
import project.exceptions.UserNotFoundException;
import project.pojo.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserDao {

    private Connection connection;

    public UserDao() {
        this.connection = Database.getConnection();
    }
    public void create(User user) {
        try {
            String sql = "INSERT INTO users (email, first_name, last_name, passphrase) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getPassphrase());

            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()) {
                System.out.println(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(User user) {

        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, user.getEmail());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void delete(User user) {
        try {
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(User user) {
        try {
            String sql = "UPDATE users SET email = ?, first_name = ?, last_name = ?, passphrase = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getPassphrase());
            pstmt.setInt(5, user.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User authenticate(String username, String password) throws UserNotFoundException, PasswordIncorrectException{
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if(!rs.next()) {
                throw new UserNotFoundException("Username was not found");
            }

            User user = new User(rs.getInt("user_id"),
                                rs.getString("email"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("password"),
                                rs.getString("role"));

            if(user.getPassphrase().equals(password)) {
                return user;
            }
            throw new PasswordIncorrectException("Password was incorrect");


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Set<User> getAllUsers() {

        //Prepares the SQL statment
        String sql = "SELECT * FROM users";
        Set<User> users = new HashSet<>();

        //Submits the query and fills the Ticket Set with the result from the users table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                User user = new User(rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("password"),
                        rs.getString("role"));

                users.add(user);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return users;
    }

    public User checkLogin () {
        //The logic from this may be used later
        Scanner scan = new Scanner(System.in);

        String user;
        String pass;
        boolean notLogin = true;
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
                    loginUser.setUserId(rs.getInt("user_id"));
                    loginUser.setEmail(rs.getString("email"));
                    loginUser.setFirstName(rs.getString("first_name"));
                    loginUser.setLastName(rs.getString("last_name"));
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
        System.out.println("Logged in as: " + loginUser.getFirstName() + " " + loginUser.getLastName());
        return loginUser;
    }

    public int register() {

        //The logic from this may be used later
        String sql = "INSERT INTO users (email, first_name, last_name, passphrase) VALUES (?,?,?,?)";
        Integer result = -1;

        Scanner scan = new Scanner(System.in);

        System.out.println("Please type email, firstname, lastname, then password");
        String email    = scan.nextLine();
        String first    = scan.nextLine();
        String last     = scan.nextLine();
        String pass     = scan.nextLine();

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setFirstName(first);
        newUser.setLastName(last);
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
            pstmt2.setString(2,newUser.getFirstName());
            pstmt2.setString(3,newUser.getLastName());
            pstmt2.setString(4,newUser.getPassphrase());
            result = pstmt2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int alterUserRole(User user, String newRole) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        Integer result = -1;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, newRole);
            pstmt.setInt(2, user.getUserId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

