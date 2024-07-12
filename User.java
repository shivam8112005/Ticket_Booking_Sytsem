import java.util.HashMap;

abstract class User {
    private int id;
    private String name;
    private String phoneNumber;
    private HashMap<Integer, Ticket> ticketMap;
    private String dateOfBirth;
    private HashMap<Integer, DiscountPass> discountPassMap;

    public User(int id, String name, String phoneNumber, Ticket ticket, String dateOfBirth, DiscountPass discountPass) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.ticketMap = new HashMap<>();
        this.ticketMap.put(ticket.getTicketId(), ticket);
        this.dateOfBirth = dateOfBirth;
        this.discountPassMap = new HashMap<>();
        this.discountPassMap.put(discountPass.getPassId(), discountPass);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public HashMap<Integer, Ticket> getTicketMap() {
        return ticketMap;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public HashMap<Integer, DiscountPass> getDiscountPassMap() {
        return discountPassMap;
    }

    public void addTicket(Ticket ticket) {
        this.ticketMap.put(ticket.getTicketId(), ticket);
    }

    public void addDiscountPass(DiscountPass discountPass) {
        this.discountPassMap.put(discountPass.getPassId(), discountPass);
    }
}
