package project;

import io.javalin.Javalin;
import project.persistence.TicketDao;
import project.persistence.UserDao;
import project.pojo.User;
import project.service.TicketService;
import project.service.UserService;

import java.io.InputStream;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Main {
    public static void main(String... args) {

        Javalin webApp = Javalin.create().start(8080);
        UserService uService = new UserService(new UserDao());
        TicketService tService = new TicketService(new TicketDao());

        webApp.get("/ping", (ctx) -> {
            ctx.result("pong!");
            ctx.status(200);
        });

        webApp.post("/registerUser", (ctx) -> {

             User newUser = ctx.bodyAsClass(User.class);
             uService.registerNewUser(newUser);

             ctx.status(201);
        });

        webApp.get("/getAllUsers", (ctx) -> {
            Set<User> users = uService.getAllUsers();
            ctx.jsonStream(users);
            ctx.status(200);
        });

        webApp.get("/login", (ctx) -> {
            User newUser = ctx.bodyAsClass(User.class);
            if (uService.login(newUser)) {
                ctx.result("Login Successful!");
            } else {
                ctx.result("Login has failed");
            }
            ctx.status(200);
        });

        webApp.post("/alterRole", (ctx) -> {
            User newUser = ctx.bodyAsClass(User.class);
            uService.changeUserRole(newUser);
            ctx.status(201);
        });

    }
}
