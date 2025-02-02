package org.example.secondlastcopy.Service;


import org.example.secondlastcopy.model.Customer;
import org.example.secondlastcopy.model.TicketPool;
import org.example.secondlastcopy.model.Vendor;
import org.example.secondlastcopy.config.Configuration;
import org.example.secondlastcopy.Controller.WebSocketSimulationController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimulationRunner {
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();
    private boolean isRunning = false;

    @Autowired
    private WebSocketSimulationController webSocketSimulationController;

    public synchronized void runSimulation(Configuration config) {
        if (isRunning) {
            webSocketSimulationController.broadcastSimulationUpdate("Simulation is already running.");
            return;
        }

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());
        webSocketSimulationController.broadcastSimulationUpdate("Simulation started with configuration: " + config);

        // Start Vendor threads
        for (int i = 0; i < 20; i++) {
            Vendor vendor = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool, webSocketSimulationController);
            Thread vendorThread = new Thread(vendor, "Vendor-" + i);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Start Customer threads
        for (int i = 0; i < 20; i++) {
            Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate(), config.getTotalTickets(), webSocketSimulationController);
            Thread customerThread = new Thread(customer, "Customer-" + i);
            customerThreads.add(customerThread);
            customerThread.start();
        }

        isRunning = true;
    }

    public synchronized void stopSimulation() {
        if (!isRunning) {
            webSocketSimulationController.broadcastSimulationUpdate("Simulation is not running.");
            return;
        }

        webSocketSimulationController.broadcastSimulationUpdate("Stopping the simulation...");
        vendorThreads.forEach(Thread::interrupt);
        vendorThreads.clear();

        customerThreads.forEach(Thread::interrupt);
        customerThreads.clear();

        isRunning = false;
        webSocketSimulationController.broadcastSimulationUpdate("Simulation stopped.");
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }
}

