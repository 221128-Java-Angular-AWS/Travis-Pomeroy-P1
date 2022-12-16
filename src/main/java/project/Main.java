package project;

import io.javalin.Javalin;
import project.javalin.JavalinApp;
import project.persistence.TicketDao;
import project.persistence.UserDao;
import project.pojo.Ticket;
import project.pojo.User;
import project.service.TicketService;
import project.service.UserService;

import java.util.Set;

public class Main {
    public static void main(String... args) {

        Javalin webApp = JavalinApp.getApp(8080);

    }
}
