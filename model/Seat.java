public class Seat {
    // seatNumber is not static as numbering will start from 1 for every bus , as in
    // all bus will have their own seats seat
    private int seatNumber;
    private Bus bus;
    private boolean occupied;
    private Passenger passenger;

    public Seat(int seatNumber, Bus bus) {
        this.seatNumber = seatNumber;
        this.bus = bus;
        this.occupied = false;
        this.passenger = null;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Bus getBus() {
        return bus;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatNumber=" + seatNumber +
                ", bus=" + bus +
                ", occupied=" + occupied +
                ", passenger=" + (passenger != null ? passenger.getName() : "None") +
                '}';
    }

}
