package org.example.secondlastcopy.model;



public class Ticket {

    private int ticketId;

    public Ticket(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getId() {
        return ticketId;
    }

    public void setId(int ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                '}';
    }
}

