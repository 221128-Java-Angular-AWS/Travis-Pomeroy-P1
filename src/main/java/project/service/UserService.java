package project.service;

import project.exceptions.PasswordIncorrectException;
import project.exceptions.UserNotFoundException;
import project.persistence.UserDao;
import project.pojo.User;
import java.util.Set;

public class UserService {
    private UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public void registerNewUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("Must have email");
        } else if (user.getPassphrase() == null || user.getPassphrase().isEmpty()) {
            System.out.println("Must have password");
        }else if (dao.checkUser(user)) {
            dao.create(user);
        } else {
            System.out.println("Unable to create new user");
        }
    }

    public void changeUserRole(User user) {

        User foundUser = dao.getUser(user);

        if (foundUser.getRole().equals("Employee")) {
            foundUser.setRole("Manager");
            dao.update(foundUser);
            System.out.println(user.getUserId() + " is now a Manager");

        } else if (foundUser.getRole().equals("Manager")) {
            foundUser.setRole("Employee");
            dao.update(foundUser);
            System.out.println(user.getUserId() + " is now an Employee");

        } else {
            System.out.println("Invalid role!");
        }
    }

    public Set<User> getAllUsers() {
        return dao.getAllUsers();
    }

    public boolean login(User user) {
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                System.out.println("Missing Email");
            } else if (user.getPassphrase() == null || user.getPassphrase().isEmpty()) {
                System.out.println("Missing Password");
            } else {
                dao.authenticate(user.getEmail(), user.getPassphrase());
                return true;
            }
        } catch (UserNotFoundException e) {
            System.out.println("Username not found");
        } catch (PasswordIncorrectException e) {
            System.out.println("Password is incorrect");
        }
        return false;
    }
}
