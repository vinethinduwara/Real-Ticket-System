package org.example.secondlastcopy.model;


import org.example.secondlastcopy.Controller.WebSocketSimulationController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    private final WebSocketSimulationController webSocketSimulationController;

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool, WebSocketSimulationController webSocketSimulationController) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
        this.webSocketSimulationController = webSocketSimulationController;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i);
            ticketPool.addTicket(ticket);

            String logMessage = "[" + getCurrentTimestamp() + "] " + Thread.currentThread().getName() + " added Ticket ID: " + ticket.getId();
            System.out.println(logMessage);

            webSocketSimulationController.broadcastSimulationUpdate(logMessage);

            try {
                Thread.sleep(ticketReleaseRate * 1000L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.currentThread().getName() + " interrupted.");
                return;
            }
        }
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}


