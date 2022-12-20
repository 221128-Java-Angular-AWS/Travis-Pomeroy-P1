package project.persistence;

import project.exceptions.PasswordIncorrectException;
import project.exceptions.UserNotFoundException;
import project.pojo.User;
import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class UserDao {

    private Connection connection;

    public UserDao() {
        this.connection = Database.getConnection();
    }
    public void create(User user) {
        try {
            String sql = "INSERT INTO users (email, first_name, last_name, passphrase) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getPassphrase());

            pstmt.executeUpdate();

            System.out.println("Created new User");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(User user) {
        //Prepares the SQL statment
        String sql = "SELECT * FROM users WHERE user_id = ?";

        //Submits the query and fills the Ticket Set with the result from the users table
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                User foundUser = new User(rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("passphrase"),
                        rs.getString("role"));

                return foundUser;
            }
            System.out.println("Cant find user");
            return null;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    public Set<User> getAllUsers() {

        //Prepares the SQL statment
        String sql = "SELECT * FROM users ORDER BY user_id";
        Set<User> users = new LinkedHashSet<>();

        //Submits the query and fills the Ticket Set with the result from the users table
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                User user = new User(rs.getInt("user_id"),
                        rs.getString("email"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("passphrase"),
                        rs.getString("role"));

                users.add(user);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return users;
    }
    public boolean checkUser(User user) {

        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, user.getEmail());
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
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
            String sql = "UPDATE users SET email = ?, first_name = ?, last_name = ?, passphrase = ?, role = ? WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getPassphrase());
            pstmt.setString(5, user.getRole());
            pstmt.setInt(6, user.getUserId());

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
                                rs.getString("passphrase"),
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
}