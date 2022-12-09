package project.service;

import project.persistence.UserDao;
import project.pojo.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserService {
    private UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public void registerNewUser(User user) {
        if (user.getEmail() == null) {
            System.out.println("Invalid email");
        } else if (dao.checkUser(user)) {
            dao.create(user);
        } else {
            System.out.println("Unable to create new user");
        }
    }

    public void changeUserRole(User user, String role) {

        if (role.equals("Employee")) {
            user.setRole("Employee");
            dao.update(user);

        } else if (role.equals("Manager")) {
            user.setRole("Manager");
            dao.update(user);

        } else {
            System.out.println("Invalid role!");
        }
    }
}
