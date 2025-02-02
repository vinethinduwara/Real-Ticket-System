import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Configuration {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    public Configuration() {}

    public void initializeConfiguration(Scanner scanner) {
        System.out.println("----Welcome to the Configuration Setup!-----");

        this.maxTicketCapacity = getValidInt(scanner, "Enter Maximum Ticket Capacity (>= 1):", 1, Integer.MAX_VALUE);
        this.totalTickets = getValidInt(scanner, "Enter Total Tickets per vendor:", 1, maxTicketCapacity);
        this.ticketReleaseRate = getValidInt(scanner, "Enter Ticket Release Rate (>= 1 second):", 1, Integer.MAX_VALUE);
        this.customerRetrievalRate = getValidInt(scanner, "Enter Customer Retrieval Rate (>= 1 second):", 1, Integer.MAX_VALUE);
    }

    public void saveConfiguration() {
        try (FileWriter writer = new FileWriter("configFile.json")) {
            new Gson().toJson(this, writer);
            System.out.println("Configuration saved to configFile.json");
        } catch (IOException e) {
            System.out.println("Error saving configuration: " + e.getMessage());
        }
    }

    public static Configuration loadConfiguration() {
        try (FileReader reader = new FileReader("configFile.json")) {
            return new Gson().fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("No configuration found. Creating a new one...");
            return null;
        }
    }

    private static int getValidInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.println(prompt + " (" + min + "-" + max + ")");
            try {
                int value = scanner.nextInt();
                if (value >= min && value <= max) return value;
                System.out.println("Value must be between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    @Override
    public String toString() {
        return "Configuration { " +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                " }";
    }
}


