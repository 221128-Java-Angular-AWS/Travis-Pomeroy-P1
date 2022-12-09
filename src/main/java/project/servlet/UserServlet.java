package project.servlet;

import project.persistence.UserDao;
import project.pojo.User;
import project.service.UserService;


import java.util.Scanner;

public class UserServlet {

    private User user;
    private UserDao dao = new UserDao();
    public void newUser() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Please type email, firstname, lastname, then password");
        String email    = scan.nextLine();
        String first    = scan.nextLine();
        String last     = scan.nextLine();
        String pass     = scan.nextLine();

        User user = new User(email, first, last, pass);

        UserService service = new UserService(dao);

        System.out.println("Created new user");
    }
}
