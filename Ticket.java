import java.util.HashMap;

class Ticket {
    private int ticketId;
    private long transactionTimestamp;
    private HashMap<Integer, Bus> busMap;
    private HashMap<Integer, Passenger> passengerMap;
    private double price;

    public Ticket(int ticketId, Bus bus, Passenger passenger, double price) {
        this.ticketId = ticketId;
        this.transactionTimestamp = System.currentTimeMillis();
        this.busMap = new HashMap<>();
        this.busMap.put(bus.getBusId(), bus);
        this.passengerMap = new HashMap<>();
        this.passengerMap.put(passenger.getPassengerId(), passenger);
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public long getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(long transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public HashMap<Integer, Bus> getBusMap() {
        return busMap;
    }

    public void setBusMap(HashMap<Integer, Bus> busMap) {
        this.busMap = busMap;
    }

    public HashMap<Integer, Passenger> getPassengerMap() {
        return passengerMap;
    }

    public void setPassengerMap(HashMap<Integer, Passenger> passengerMap) {
        this.passengerMap = passengerMap;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Route getRoute(int busId) {
        return busMap.get(busId).getRoutes().values().iterator().next();
    }

    public String getStartLocation(int busId) {
        return getRoute(busId).getStartLocation();
    }

    public String getEndLocation(int busId) {
        return getRoute(busId).getEndLocation();
    }

    public String getStartTime(int busId) {
        return getRoute(busId).getStartTime();
    }

    public String getEndTime(int busId) {
        return getRoute(busId).getEndTime();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", transactionTimestamp=" + transactionTimestamp +
                ", busMap=" + busMap +
                ", passengerMap=" + passengerMap +
                ", price=" + price +
                '}';
    }
}
