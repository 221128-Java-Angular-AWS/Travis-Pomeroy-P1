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
        String sql = "SELECT * FROM tickets WHERE status = 'Pending' ORDER BY ticket_id ASC";
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
                System.out.println(ticket);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
        return tickets;
    }

    public Ticket getTicket(Ticket ticket) {
        String sql = "SELECT * FROM tickets WHERE ticket_id = ?";

        //Submits the query and fills the Ticket Set with the result from the users table
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getTicketId());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {

                Ticket foundTicket = new Ticket(rs.getInt("ticket_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("description"),
                        rs.getString("status"));

                return foundTicket;
            }
            System.out.println("Cant find user");
            return null;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
    public void create(Ticket ticket) {
        try {
            String sql = "INSERT INTO tickets (user_id, amount, description) VALUES (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, ticket.getUserId());
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
            String sql = "UPDATE tickets SET user_id = ?, amount = ?, description = ?, status = ? WHERE ticket_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, ticket.getUserId());
            pstmt.setDouble(2, ticket.getAmount());
            pstmt.setString(3, ticket.getDescription());
            pstmt.setString(4, ticket.getStatus());
            pstmt.setInt(5, ticket.getTicketId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<Ticket> getUserTickets(Integer userId) {


        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            String sql = "SELECT * FROM tickets WHERE user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);

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

    public Set<Ticket> getUserTickets(Integer userId, String filter) {


        Set<Ticket> tickets = new HashSet();

        //Submits the query and fills the Ticket Set with the result from the ticket table
        try {
            String sql = "SELECT * FROM tickets WHERE user_id = ? AND status = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
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
            pstmt.setInt(1, ticket.getUserId());
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
