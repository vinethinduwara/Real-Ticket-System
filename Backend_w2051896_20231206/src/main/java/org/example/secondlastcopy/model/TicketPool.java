package org.example.secondlastcopy.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class TicketPool {
    private final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        System.out.println("[" + getCurrentTimestamp() + "] TicketPool initialized with a capacity of " + maxCapacity);
    }
    // add ticket method
    public synchronized void addTicket(Ticket ticket) {
        while (tickets.size() >= maxCapacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        tickets.add(ticket);
        System.out.println("[" + getCurrentTimestamp() + "] " + Thread.currentThread().getName() +
                " added Ticket ID: " + tickets.indexOf(ticket) +
                ", Current size: " + tickets.size() +
                ", Ticket details: " + ticket);
        notifyAll();
    }

    public synchronized Ticket buyTicket() {
        while (tickets.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Ticket ticket = tickets.remove(0);
        System.out.println("[" + getCurrentTimestamp() + "] " + Thread.currentThread().getName() +
                " purchased Ticket ID: " + ticket.getId() +
                ", Current size: " + tickets.size() +
                ", Ticket details: " + ticket);
        notifyAll();
        return ticket;
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }
}



