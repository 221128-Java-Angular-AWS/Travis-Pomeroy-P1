package project.persistence;

import project.pojo.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class TicketDao {

    public Set<Ticket> getAllTickets() {

        //Connects to the database and prepares the SQL statement
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM tickets";
        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticket_id"));
                ticket.setUserid(rs.getInt("user_id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                tickets.add(ticket);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public Set<Ticket> getAllPendingTickets() {

        //Connects to the database and prepares the SQL statement
        Connection connection = Database.getConnection();
        String sql = "SELECT * FROM tickets WHERE status = 'Pending'";
        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticket_id"));
                ticket.setUserid(rs.getInt("user_id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                tickets.add(ticket);
            }
            connection.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public Set<Ticket> getUserTickets(Integer id, String filter) {

        //Connects to the database and prepares the SQL statement
        Connection connection = Database.getConnection();
        String sql = "";

        // if a filter is applied change the statement to only retrieve approved/denied/pending
        if (filter == "") {
            sql = "SELECT * FROM tickets WHERE user_id = '" + id + "'";
        }
        else {
            sql = "SELECT * FROM tickets WHERE user_id = '" + id + "' AND status = '" + filter + "'";
        }
        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setId(rs.getInt("ticket_id"));
                ticket.setUserid(rs.getInt("user_id"));
                ticket.setAmount(rs.getDouble("amount"));
                ticket.setDescription(rs.getString("description"));
                ticket.setStatus(rs.getString("status"));
                tickets.add(ticket);
            }
            connection.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public int insertNewTicket(Ticket ticket) {

        Connection connection = Database.getConnection();
        String sql = "INSERT INTO tickets (user_id, amount, description) VALUES (?,?,?)";
        Integer result = -1;

        try{
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getUser_Id());
            pstmt.setDouble(2,ticket.getAmount());
            pstmt.setString(3,ticket.getDescription());
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public int alterTicket(Integer id, String status) {

        Connection connection = Database.getConnection();
        String sql = "UPDATE tickets SET status = '" + status + "' WHERE ticket_id = '" + id + "'";
        Integer result = -1;

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
