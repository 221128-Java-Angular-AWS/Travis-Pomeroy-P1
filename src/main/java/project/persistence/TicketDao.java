package project.persistence;

import project.pojo.Ticket;
import project.pojo.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class TicketDao {
    private Connection connection;

    public TicketDao(){
        this.connection = Database.getConnection();
    }
    public Set<Ticket> getAllTickets() {

        //Connects to the database and prepares the SQL statement
        String sql = "SELECT * FROM tickets";
        Set<Ticket> tickets = new HashSet<>();

       //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status"));

                tickets.add(ticket);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public Set<Ticket> getAllPendingTickets() {

        //Connects to the database and prepares the SQL statement
        String sql = "SELECT * FROM tickets WHERE status = 'Pending'";
        Set<Ticket> tickets = new HashSet<>();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("ticket_id"),
                                            rs.getInt("user_id"),
                                            rs.getDouble("amount"),
                                            rs.getString("description"),
                                            rs.getString("status"));

                tickets.add(ticket);
            }
            connection.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public void create(Ticket ticket) {
        try {
            String sql = "INSERT INTO tickets (user_id, amount, description) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, ticket.getUser_Id());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());


            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()) {
                System.out.println(rs.getInt("ticket_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Ticket ticket) {
        try {
            String sql = "DELETE FROM tickets WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getTicketId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Ticket ticket) {
        try {
            String sql = "UPDATE tickets SET user_id = ?, amount = ?, description = ?, role = ? WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getUser_Id());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getStatus());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Ticket> getUserTickets(User user) {


        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            String sql = "SELECT * FROM tickets WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status"));

                tickets.add(ticket);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public Set<Ticket> getUserTickets(User user, String filter) {


        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND status = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, user.getUserId());
            pstmt.setString(2, filter);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status"));

                tickets.add(ticket);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public int insertNewTicket(Ticket ticket) {

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
