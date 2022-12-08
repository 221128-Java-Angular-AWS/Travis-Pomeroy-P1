package project;

import project.persistence.UserDao;

public class Main {
    public static void main(String... args) {
        UserDao udao = new UserDao();
        //User currentUser = udao.checkLogin();

       // System.out.println("Currently logged in as: " + currentUser.getFirstname()
        //        + " " + currentUser.getLastname());
       // udao.alterUserRole(3,"Manager");
        udao.register();
/*
        TicketDao dao = new TicketDao();
        Set<Ticket> results = dao.getUserTickets(1, "");

        for (Ticket t : results){
            System.out.println(t);
        }

        UserDao udao = new UserDao();
        Set<User> uResults = udao.getAllUsers();

        for (User u : uResults) {
            System.out.println(u);
        }


        Boolean login = udao.checkLogin("employee@company.com", "password");

        System.out.println("Login was: " + login);
        //dao.alterTicket(1, "Denied");
*/
        /*
        System.out.println("====================================");
        System.out.println("Welcome to the Reimbursement System!");
        System.out.println("====================================");

        System.out.println("Press 1 to log in");
        System.out.println("Press 2 to register");
        System.out.println("Press q to quit");
        */

        /*
        Scanner scan = new Scanner(System.in);

        String string = scan.nextLine();

        Ticket newTicket = new Ticket();
        newTicket.setUserid(1);
        newTicket.setAmount(100.3);
        newTicket.setDescription("expensive dinner");

        dao.insertNewTicket(newTicket);
        */
    }
}
