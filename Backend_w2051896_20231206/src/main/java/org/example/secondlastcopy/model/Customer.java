package org.example.secondlastcopy.model;

import org.example.secondlastcopy.Controller.WebSocketSimulationController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int totalTickets;
    private final WebSocketSimulationController webSocketSimulationController;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int totalTickets, WebSocketSimulationController webSocketSimulationController) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.totalTickets = totalTickets;
        this.webSocketSimulationController = webSocketSimulationController;
    }
    @Override
    public void run() {
        for (int i = 0; i <totalTickets; i++) {
            Ticket ticket = ticketPool.buyTicket();

            String logMessage = "[" + getCurrentTimestamp() + "] " + Thread.currentThread().getName() + " retrieved Ticket ID: " + ticket.getId();
            System.out.println(logMessage);

            webSocketSimulationController.broadcastSimulationUpdate(logMessage);

            try {
                Thread.sleep(customerRetrievalRate * 1000L);
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

