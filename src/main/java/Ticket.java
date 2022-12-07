package src.main.java;

import java.util.Objects;

public class Ticket {
    //This is the ticket object that holds all relevant data from the ticket table
    private Integer id;
    private Integer user_id;
    private Double amount;
    private String description;
    private String status;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(Integer user_id) {
        this.user_id = user_id;
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
        return user_id;
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
        return Objects.equals(getId(), ticket.getId()) && Objects.equals(user_id, ticket.user_id) && Objects.equals(getAmount(), ticket.getAmount()) && Objects.equals(getDescription(), ticket.getDescription()) && Objects.equals(getStatus(), ticket.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), user_id, getAmount(), getDescription(), getStatus());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", userid=" + user_id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
