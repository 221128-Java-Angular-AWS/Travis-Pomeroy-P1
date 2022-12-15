package project;

import io.javalin.Javalin;
import project.persistence.TicketDao;
import project.persistence.UserDao;
import project.pojo.Ticket;
import project.pojo.User;
import project.service.TicketService;
import project.service.UserService;

import java.util.Set;

public class Main {
    public static void main(String... args) {

        Javalin webApp = Javalin.create().start(8080);
        UserService uService = new UserService(new UserDao());
        TicketService tService = new TicketService(new TicketDao());

        //Test get to see if there is a connection to postman
        webApp.get("/ping", (ctx) -> {
            ctx.result("pong!");
            ctx.status(200);
        });

        //creates a new user when a post is sent containing (email, firstName, lastName, passphrase)
        //auto defaults role to Employee
        webApp.post("/registerUser", (ctx) -> {

             User newUser = ctx.bodyAsClass(User.class);
             uService.registerNewUser(newUser);

             ctx.status(201);
        });

        //returns all users in the database
        webApp.get("/getAllUsers", (ctx) -> {
            Set<User> users = uService.getAllUsers();
            ctx.jsonStream(users);
            ctx.status(200);
        });

        //returns a boolean value on whether the email/passphrase combo is in the database
        webApp.get("/login", (ctx) -> {
            User newUser = ctx.bodyAsClass(User.class);
            if (uService.login(newUser)) {
                ctx.result("Login Successful!");
            } else {
                ctx.result("Login has failed");
            }
            ctx.status(200);
        });

        //alters the role of an employee, only need to supply userId as there are only 2 roles
        webApp.post("/alterRole", (ctx) -> {
            User newUser = ctx.bodyAsClass(User.class);
            uService.changeUserRole(newUser);
            ctx.status(201);
        });

        //returns all tickets with the status of pending
        webApp.get("/getPendingTicket", (ctx) -> {
            Set<Ticket> tickets = tService.getPendingTickets();
            ctx.jsonStream(tickets);
            ctx.status(200);
        });

        //submits a new ticket when the post is sent (userId, amount, description)
        //auto defaults to pending
        webApp.post("/submitTicket", (ctx) -> {
            Ticket newTicket = ctx.bodyAsClass(Ticket.class);
            tService.submitNewTicket(newTicket);
            ctx.status(201);
        });

        //alters the status of a ticket to either Approved or Denied
        webApp.post("/changeStatus", (ctx) -> {
            Ticket newTicket = ctx.bodyAsClass(Ticket.class);
            tService.changeStatus(newTicket);
            ctx.result("Attempting to Alter Ticket to " + newTicket.getStatus());
            ctx.status(201);
        });

        //returns all tickets tied to a specific userId
        webApp.get("/getUserTickets", (ctx) -> {
            Integer userId = Integer.valueOf(ctx.formParam("userId"));
            String filter = ctx.formParam("filter");

            Set<Ticket> newTickets;

            if (filter == null) {
                newTickets = tService.displayUserTickets(userId);
            } else {
                newTickets = tService.displayUserTickets(userId, filter);
            }

            ctx.jsonStream(newTickets);
            ctx.status(200);
        });
    }
}
