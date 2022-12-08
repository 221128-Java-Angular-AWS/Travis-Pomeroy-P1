package project.pojo;

import java.util.Objects;

public class Ticket {
    //This is the ticket object that holds all relevant data from the ticket table
    private Integer id;
    private Integer userId;
    private Double amount;
    private String description;
    private String status;

    public Ticket() {
    }

    public Ticket(Integer userId, Double amount, String description) {
        this.userId = userId;
        this.amount = amount;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(Integer user_id) {
        this.userId = user_id;
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

    public Integer getId() {
        return id;
    }

    public Integer getUser_Id() {
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
        return Objects.equals(getId(), ticket.getId()) && Objects.equals(userId, ticket.userId) && Objects.equals(getAmount(), ticket.getAmount()) && Objects.equals(getDescription(), ticket.getDescription()) && Objects.equals(getStatus(), ticket.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), userId, getAmount(), getDescription(), getStatus());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userid=" + userId +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
