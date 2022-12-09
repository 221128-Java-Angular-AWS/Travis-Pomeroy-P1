package project.service;

import project.persistence.TicketDao;
import project.pojo.Ticket;
import project.pojo.User;

import java.util.Set;

public class TicketService {

    private TicketDao dao;

    public TicketService(TicketDao dao) {
        this.dao = dao;
    }

    public void submitNewTicket(Ticket ticket) {
        dao.create(ticket);
    }

    public Set<Ticket> getPendingTickets() {
        return dao.getAllPendingTickets();
    }

    public Set<Ticket> displayUserTickets(User user) {
        return dao.getUserTickets(user);
    }

    public Set<Ticket> displayUserTickets(User user, String filter) {
        if (filter.equals("Pending") || filter.equals("Accepted") || filter.equals("Denied")) {
            return dao.getUserTickets(user, filter);
        }
        return null;
    }
    public void changeStatus(Ticket ticket, String status) {

        // this method updates the status of the selected ticket
        if (status.equals("Accepted")) {
            ticket.setStatus("Accepted");
            dao.update(ticket);

        } else if (status.equals("Denied")) {
            ticket.setStatus("Denied");
            dao.update(ticket);

        } else {
            System.out.println("Invalid status!");
        }

    }


}
