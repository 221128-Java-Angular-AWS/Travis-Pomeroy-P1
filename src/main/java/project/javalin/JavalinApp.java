package project.javalin;

import io.javalin.Javalin;
import io.javalin.http.util.CookieStore;
import project.persistence.TicketDao;
import project.persistence.UserDao;
import project.pojo.Ticket;
import project.pojo.User;
import project.service.TicketService;
import project.service.UserService;
import io.javalin.http.Context;

import java.util.Set;

public class JavalinApp {

    private static Javalin app;
    private static UserService uService;
    private static TicketService tService;

    private JavalinApp() {

    }

    public static Javalin getApp (int port) {
        if (app == null) {
            uService = new UserService(new UserDao());
            tService = new TicketService(new TicketDao());
            init(port);
        }
        return app;
    }

    private static void init (int port) {
        app = Javalin.create().start(port);

        //Generic commands
        app.get("/ping", JavalinApp::ping);
        app.get("/getAllUsers", JavalinApp::getAllUsers);

        //Login/Register
        app.post("/registerUser", JavalinApp::registerUser);
        app.get("/login", JavalinApp::login);

        //Employee Commands
        app.post("/submitTicket", JavalinApp::submitTicket);
        app.get("/getUserTickets", JavalinApp::getUserTickets);
        app.post("/updateInfo", JavalinApp::updateInfo);

        //Manager Commands
        app.post("/alterRole", JavalinApp::alterRole);
        app.get("/getPendingTicket", JavalinApp::getPendingTicket);
        app.post("/changeStatus", JavalinApp::changeStatus);

    }

    public static void ping (Context ctx) {
        ctx.result("pong!");
        ctx.status(200);
    }

    public static void registerUser (Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);

        if (!uService.verifyUser(newUser))
            ctx.result("Username already in use!");
        else {
            uService.registerNewUser(newUser);
            ctx.result("Successfully created user!");
        }
        ctx.status(201);
    }

    public static void getAllUsers (Context ctx) {
        Set<User> users = uService.getAllUsers();
        ctx.jsonStream(users);
        ctx.status(200);
    }

    public static void login (Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        if (!uService.verifyUser(newUser)) {

            newUser = uService.login(newUser);

            if (newUser == null) {
                ctx.result("Password is incorrect");
                ctx.status(401);
            }
            else {
                ctx.json(newUser);
                ctx.status(200);
            }
        }
        else {
            ctx.result("Username not found");
            ctx.status(404);
        }


    }

    public static void alterRole (Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        uService.changeUserRole(newUser);
        ctx.status(201);
    }

    public static void getPendingTicket (Context ctx) {
        Set<Ticket> tickets = tService.getPendingTickets();
        ctx.jsonStream(tickets);
        ctx.status(200);
    }

    public static void submitTicket (Context ctx) {
        Ticket newTicket = ctx.bodyAsClass(Ticket.class);

        if (newTicket.getAmount() == null) {
            ctx.result("Please enter an amount");
            ctx.status(400);
        }
        else if (newTicket.getDescription() == null || newTicket.getDescription().isEmpty()) {
            ctx.result("Please enter a description");
            ctx.status(400);
        }
        else {
            tService.submitNewTicket(newTicket);
            ctx.result("Created new Ticket Submission");
            ctx.status(201);
        }
    }

    public static void changeStatus (Context ctx) {
        Ticket newTicket = ctx.bodyAsClass(Ticket.class);
        tService.changeStatus(newTicket);
        ctx.result("Altering Ticket to " + newTicket.getStatus());
        ctx.status(202);
    }

    public static void getUserTickets (Context ctx) {
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
    }

    public static void updateInfo (Context ctx) {
        User newUser = ctx.bodyAsClass(User.class);
        ctx.json(uService.updateInfo(newUser));
        ctx.status(200);
    }

}
