import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Vendor implements Runnable {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 1; i <= totalTickets; i++) {
            Ticket ticket = new Ticket(i);
            ticketPool.addTicket(ticket);

            try {
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                System.out.println("[" + getCurrentTimestamp() + "] Vendor thread interrupted.");
                Thread.currentThread().interrupt();
            }
        }
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }
}








