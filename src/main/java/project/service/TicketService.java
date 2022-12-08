package project.service;

import project.persistence.TicketDao;
import project.pojo.Ticket;

public class TicketService {

    private TicketDao dao;

    public TicketService(TicketDao dao) {
        this.dao = dao;
    }

    public void registerNewUser(Ticket ticket) {
        //we can add business logic
        //validation - user input
        //logging
        dao.create(ticket);
    }
}
