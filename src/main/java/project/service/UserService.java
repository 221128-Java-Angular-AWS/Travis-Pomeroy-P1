package project.service;

import project.persistence.UserDao;
import project.pojo.User;

public class UserService {
    private UserDao dao;

    public UserService(UserDao dao) {
        this.dao = dao;
    }

    public void registerNewUser(User user) {
        //we can add business logic
        //validation - user input
        //logging
        dao.create(user);
    }
}
