import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class TicketPool {
    private final List<Ticket> tickets = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;

        System.out.println("[" + getCurrentTimestamp() + "] TicketPool initialized with a capacity of " + maxCapacity);
    }

    public synchronized boolean addTicket(Ticket ticket) {
        if (tickets.size() < maxCapacity) {
            tickets.add(ticket);
             // Increment for each ticket added
            System.out.println("[" + getCurrentTimestamp() + "] " + Thread.currentThread().getName() +
                    " added Ticket ID: " + ticket.getTicketId() +
                    ", Current size: " + tickets.size());
            notifyAll();
            return true;
        } else {
            System.out.println("[" + getCurrentTimestamp() + "] TicketPool is full. Cannot add Ticket ID: " + ticket.getTicketId());
            return false;
        }
    }

    public synchronized Ticket buyTicket() {
        if (!tickets.isEmpty()) {
            Ticket ticket = tickets.remove(0);

            System.out.println("[" + getCurrentTimestamp() + "] " + Thread.currentThread().getName() +
                    " purchased Ticket ID: " + ticket.getTicketId() +
                    ", Current size: " + tickets.size());
            notifyAll();
            return ticket;
        }
        return null;
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(formatter);
    }
}









