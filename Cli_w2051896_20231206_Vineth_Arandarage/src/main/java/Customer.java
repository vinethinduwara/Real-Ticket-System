import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Customer implements Runnable {

    private final TicketPool ticketPool;
    private final int customerRetrievalRate;
    private final int totalTickets;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int totalTickets) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.totalTickets = totalTickets;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalTickets; i++) {
            Ticket ticket = ticketPool.buyTicket();

            try {
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                System.out.println("[" + getCurrentTimestamp() + "] Customer thread interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * return current time
     * 
     * @return
     */
    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }
}
