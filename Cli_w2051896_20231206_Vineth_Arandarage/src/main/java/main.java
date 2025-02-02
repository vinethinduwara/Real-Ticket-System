import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize Configuration
        Configuration config;

        String choice;
        do {
            System.out.println("Do you want to load the previous configuration? (yes/no)");
            choice = scanner.nextLine().toLowerCase();

            if (!choice.equals("yes") && !choice.equals("no")) {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        } while (!choice.equals("yes") && !choice.equals("no"));

        if (choice.equals("yes")) {
            config = Configuration.loadConfiguration();

            if (config != null) {
                System.out.println("\nLoaded Configuration:");
                System.out.println(config);
            } else {
                System.out.println("Failed to load configuration. Creating a new one.");
                config = new Configuration();
                config.initializeConfiguration(scanner);
            }
        } else {
            config = new Configuration();
            config.initializeConfiguration(scanner);
            config.saveConfiguration();
        }

        //  Initialize TicketPool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        //Create Vendor Threads
        int vendorCount = 20; // Adjust vendor count as needed
        Vendor[] vendors = new Vendor[vendorCount];
        Thread[] vendorThreads = new Thread[vendorCount];
        for (int i = 0; i < vendorCount; i++) {
            vendors[i] = new Vendor(config.getTotalTickets(), config.getTicketReleaseRate(), ticketPool);
            vendorThreads[i] = new Thread(vendors[i], "Vendor ID-" + i);
        }

        // Create Customer Threads
        int customerCount = 20; // Adjust customer count as needed
        Customer[] customers = new Customer[customerCount];
        Thread[] customerThreads = new Thread[customerCount];
        for (int i = 0; i < customerCount; i++) {
            customers[i] = new Customer(ticketPool, config.getCustomerRetrievalRate(), config.getTotalTickets());
            customerThreads[i] = new Thread(customers[i], "Customer ID-" + i);
        }

        // Wait for "Start" Button
        System.out.println("\nType 'start' to begin the simulation:");


        while(!scanner.nextLine().equalsIgnoreCase("start")) {
            System.out.println("Please type 'start' to begin the simulation.");
        }

        // Start all threads after "start" is typed
        for (Thread vendorThread : vendorThreads) {
            vendorThread.start();
        }

        for (Thread customerThread : customerThreads) {
            customerThread.start();
        }

        System.out.println("\nSimulation started!");

        // Keep the application running
        try {
            while (true) {
                Thread.sleep(1000); // Sleep for 1 second to keep main thread alive
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Clean up resources
        scanner.close();
    }
}








