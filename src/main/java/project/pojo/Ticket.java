package project.pojo;

import java.util.Objects;

public class Ticket {
    //This is the ticket object that holds all relevant data from the ticket table
    private Integer ticketId;
    private Integer userId;
    private Double amount;
    private String description;
    private String status;

    public Ticket() {
    }

    public Ticket(Integer ticketId, Integer userId, Double amount, String description, String status) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.amount = amount;
        this.description = description;
        this.status = status;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getTicketId(), ticket.getTicketId()) && Objects.equals(userId, ticket.userId) && Objects.equals(getAmount(), ticket.getAmount()) && Objects.equals(getDescription(), ticket.getDescription()) && Objects.equals(getStatus(), ticket.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketId(), userId, getAmount(), getDescription(), getStatus());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + ticketId +
                ", userid=" + userId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
