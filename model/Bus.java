import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

class Bus {
    private Scanner scanner = new Scanner(System.in);
    private static HashMap<Bus,Trip> allBus = new HashMap<>();
    //  HashMap<Bus,Ticket> busTicketPrices = new HashMap<>();
    static int busCounter = 1;
    double price;
    private int busId;
    private String numberPlate;
    private Trip route;
    public Seat[][] busSeats;// 2d array of seats aka rectangle
    public Bus(int a){

    }
    public Bus(Trip trip) {
        this.busId = busCounter++;
        setNumberPlate();
        setRoute();
        setBusSeats();
        allBus.put(this,trip);
    }

    public int getBusId() {
        return this.busId;
    }

    public String getNumberPlate() {
        return this.numberPlate;
    }

    public void setNumberPlate() {
        String platePattern = "^[A-Z]{2}[0-9]{2} [A-Z]{1,2} [0-9]{1,4}$";
        while (true) {
            System.out.print("Enter number plate: ");
            String input = scanner.nextLine();
            if (Pattern.matches(platePattern, input)) {
                this.numberPlate = input;
                break;
            } else {
                System.out.println("Invalid number plate format. Please re-enter.");
            }
        }
    }

    public void setRoute() {
        while (true) {
            System.out.println("Available routes:");
            for (Trip route : Trip.getAllTrip()) {
                System.out.println(route.getTripId() + ": " + route.getName());
            }
            System.out.println("0: Add new route");

            System.out.print("Select a route by entering the route ID: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                this.route = new Trip();
                break;
            } else {
                boolean found = false;
                for (Trip route : Trip.getAllTrip()) {
                    if (route.getTripId() == choice) {
                        this.route = route;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                } else {
                    System.out.println("Invalid route ID. Please try again.");
                }
            }
        }
    }

    public void setBusSeats() {
        // Initializes bus seats
        System.out.print("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter the number of columns: ");
        int columns = scanner.nextInt();
        scanner.nextLine();
        busSeats = new Seat[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                busSeats[i][j] = new Seat((i * columns) + j + 1, this);
            }
        }
    }
    public boolean bookSeat(int row, int column, Passenger passenger) {
        if (row < 0 || row >= busSeats.length || column < 0 || column >= busSeats[0].length) {
            System.out.println("Invalid seat position.");
            return false;
        }
        
        Seat seat = busSeats[row][column];
        if (seat.isOccupied()) {
            System.out.println("Seat already occupied.");
            return false;
        } else {
            seat.setOccupied(true);
            seat.setPassenger(passenger);
            return true;
        }
    }

    public static void printAllBus() {
        for (Bus bus : Bus.getAllBus().keySet()) {
            System.out.println("Bus ID: " + bus.getBusId());
            System.out.println("Number Plate: " + bus.getNumberPlate());
            System.out.println("Route: " + bus.getRoute().getName());
            System.out.println(
                    "Seats: " + bus.busSeats.length + " rows, " + bus.busSeats[0].length + " columns");
            System.out.println();
        }
    }

    public static HashMap<Bus,Trip> getAllBus() {
        return allBus;
    }

    public static int getBusCounter() {
        return busCounter;
    }

    public Trip getRoute() {
        return route;
    }
    public void bookTicket(){

    }
    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", numberPlate='" + numberPlate + '\'' +
                ", route=" + route.getName() +
                '}';
    }
}
