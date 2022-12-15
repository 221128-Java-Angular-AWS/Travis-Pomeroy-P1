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

    public Set<Ticket> displayUserTickets(Integer userId) {
        return dao.getUserTickets(userId);
    }

    public Set<Ticket> displayUserTickets(Integer userId, String filter) {

        //a quick check is done that the filter is valid
        if (filter.equals("Pending") || filter.equals("Approved") || filter.equals("Denied")) {
            return dao.getUserTickets(userId, filter);
        }
        System.out.println("Invalid filter!");
        return null;
    }
    public void changeStatus(Ticket ticket) {

        Ticket foundTicket = dao.getTicket(ticket);
        foundTicket.setStatus(ticket.getStatus());

        System.out.println(foundTicket);
        if (foundTicket.getStatus().equals("Approved")) {
            dao.update(foundTicket);

        } else if (foundTicket.getStatus().equals("Denied")) {
            dao.update(foundTicket);

        } else {
            System.out.println("Invalid status!");
        }

    }


}
