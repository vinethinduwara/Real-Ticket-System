package org.example.secondlastcopy.config;


public class Configuration {
    private int maxTicketCapacity;
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;

    // Getters and setters
    public int getMaxTicketCapacity() { return maxTicketCapacity; }
    public void setMaxTicketCapacity(int maxTicketCapacity) { this.maxTicketCapacity = maxTicketCapacity; }

    public int getTotalTickets() { return totalTickets; }
    public void setTotalTickets(int totalTickets) { this.totalTickets = totalTickets; }

    public int getTicketReleaseRate() { return ticketReleaseRate; }
    public void setTicketReleaseRate(int ticketReleaseRate) { this.ticketReleaseRate = ticketReleaseRate; }

    public int getCustomerRetrievalRate() { return customerRetrievalRate; }
    public void setCustomerRetrievalRate(int customerRetrievalRate) { this.customerRetrievalRate = customerRetrievalRate; }
}
