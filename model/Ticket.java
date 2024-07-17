import java.util.LinkedList;
import java.util.Scanner;

class Ticket extends Bus{
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList<Ticket> allTickets = new LinkedList<>();
    private static int ticketCounter = 1;

    private final int ticketId;
    private final long transactionTimestamp;
     
    private final Bus bus;
    private final Passenger passenger;

    public Ticket(double price, Bus bus, Passenger passenger) {
        super(10);
        this.ticketId = ticketCounter++;
        this.transactionTimestamp = System.currentTimeMillis();
        this.price = price;
        this.bus = bus;
     this.passenger = passenger;
        allTickets.add(this);
    }

    public static LinkedList<Ticket> getAllTickets() {
        return allTickets;
    }

    public static int getTicketCounter() {
        return ticketCounter;
    }

    public int getTicketId() {
        return ticketId;
    }

    public long getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public double getPrice() {
        return price;
    }

    public Bus getBus() {
        return bus;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", transactionTimestamp=" + transactionTimestamp +
                ", busId=" + bus.getBusId() +
                ", passenger=" + passenger.getName() +
                ", price=" + price +
                '}';
    }
}
