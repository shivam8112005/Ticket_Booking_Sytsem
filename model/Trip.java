package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import java.util.HashSet;

public class Trip {
    private int tripID;
    private int routeID;
    private int busID;
    private Timestamp startTime;
    private Timestamp endTime;
    private double price;

    private String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private String user = "root";
    private String password = "";

    private final Scanner sc = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // No-argument constructor
    public Trip() {
        System.out.println("Enter Trip Details:");

        setBusID();
        setRouteID();
        this.startTime = promptForTimestamp("Enter Start Time (YYYY-MM-DD HH:MM:SS): ");

        // Ensure endTime is after startTime
        while (true) {
            this.endTime = promptForTimestamp("Enter End Time (YYYY-MM-DD HH:MM:SS): ");
            if (endTime.after(startTime)) {
                break; // Valid end time, exit the loop
            } else {
                System.out.println("End time must be greater than start time. Please try again.");
            }
        }

        System.out.print("Enter Price: ");
        this.price = sc.nextDouble();

        addTripToDB(routeID, busID, startTime, endTime, price);
    }

    // Parameterized constructor
    public Trip(int tripID, int routeID, int busID, Timestamp startTime, Timestamp endTime, double price) {
        this.tripID = tripID;
        this.routeID = routeID;
        this.busID = busID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public Trip(int a) {
        // A constructor that is only to access methods
    }

    public void setBusID() {
        Bus bus = new Bus(0); // Constructor to only call methods
        HashSet<Integer> allBusIDSet = bus.getAllBusIDs();

        int busID;
        while (true) {
            System.out.println();
            bus.printAllBuses();
            System.out.print("Enter Bus ID: ");
            busID = sc.nextInt();
            sc.nextLine();
            if (allBusIDSet.contains(busID)) {
                this.busID = busID;
                return;
            }
            System.out.println("Invalid busID");
        }
    }

    public void setRouteID() {
        Route route = new Route(0); // Constructor to only call methods
        HashSet<Integer> allRouteIDSet = route.getAllRouteIDs();

        int routeID;
        while (true) {
            System.out.println();
            route.printAllRoutes();
            System.out.print("Enter Route ID: ");
            routeID = sc.nextInt();
            sc.nextLine();
            if (allRouteIDSet.contains(routeID)) {
                this.routeID = routeID;
                return;
            }
            System.out.println("Invalid busID");
        }
    }

    // Method to add the trip details to the database
    public void addTripToDB(int routeID, int busID, Timestamp startTime, Timestamp endTime, double price) {
        String query = "INSERT INTO Trip (RouteID, BusID, StartTime, EndTime, Price) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, routeID);
            stmt.setInt(2, busID);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.setDouble(5, price);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.tripID = rs.getInt(1);
                System.out.println("Trip added with ID: " + this.tripID);
                createSeatsForTrip(this.tripID, busID); // to create seats
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createSeatsForTrip(int tripID, int busID) {
        String getSeatsQuery = "SELECT NumberOfSeats FROM Bus WHERE BusID = ?";
        String createTableQuery = "CREATE TABLE IF NOT EXISTS TripSeat_" + tripID + " ("
                + "SeatNumber INT PRIMARY KEY, "
                + "TicketID INT NULL, "
                + "FOREIGN KEY (TicketID) REFERENCES Ticket(TicketID))";
        String insertSeatQuery = "INSERT INTO TripSeat_" + tripID + " (SeatNumber) VALUES (?)";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement seatStmt = conn.prepareStatement(getSeatsQuery);
            PreparedStatement createStmt = conn.prepareStatement(createTableQuery);
            PreparedStatement insertStmt = conn.prepareStatement(insertSeatQuery);

            // Get the number of seats for the bus
            seatStmt.setInt(1, busID);
            ResultSet rs = seatStmt.executeQuery();
            int numberOfSeats = 0;
            if (rs.next()) {
                numberOfSeats = rs.getInt("NumberOfSeats");
            }

            // Create the TripSeat_<TripID> table
            createStmt.executeUpdate();

            // Insert seat numbers into the TripSeat_<TripID> table
            for (int seatNum = 1; seatNum <= numberOfSeats; seatNum++) {
                insertStmt.setInt(1, seatNum);
                insertStmt.executeUpdate();
            }

            System.out.println("Seats created for TripID: " + tripID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // Method to update the routeID
    public void updateRouteID(int routeID) {
        String query = "UPDATE Trip SET RouteID = ? WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, routeID);
            stmt.setInt(2, this.tripID);
            stmt.executeUpdate();
            this.routeID = routeID;
            System.out.println("RouteID updated to " + routeID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the busID
    public void updateBusID(int busID) {
        String query = "UPDATE Trip SET BusID = ? WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busID);
            stmt.setInt(2, this.tripID);
            stmt.executeUpdate();
            this.busID = busID;
            System.out.println("BusID updated to " + busID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the startTime
    public void updateStartTime(Timestamp startTime) {
        String query = "UPDATE Trip SET StartTime = ? WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, startTime);
            stmt.setInt(2, this.tripID);
            stmt.executeUpdate();
            this.startTime = startTime;
            System.out.println("StartTime updated to " + startTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the endTime
    public void updateEndTime(Timestamp endTime) {
        String query = "UPDATE Trip SET EndTime = ? WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, endTime);
            stmt.setInt(2, this.tripID);
            stmt.executeUpdate();
            this.endTime = endTime;
            System.out.println("EndTime updated to " + endTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the price
    public void updatePrice(double price) {
        String query = "UPDATE Trip SET Price = ? WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, price);
            stmt.setInt(2, this.tripID);
            stmt.executeUpdate();
            this.price = price;
            System.out.println("Price updated to " + price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a Trip object from the database using the TripID
    public Trip getTripFromDB(int tripID) {
        String query = "SELECT * FROM Trip WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, tripID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int routeID = rs.getInt("RouteID");
                int busID = rs.getInt("BusID");
                Timestamp startTime = rs.getTimestamp("StartTime");
                Timestamp endTime = rs.getTimestamp("EndTime");
                double price = rs.getDouble("Price");
                return new Trip(tripID, routeID, busID, startTime, endTime, price);
            } else {
                System.out.println("No trip found with ID: " + tripID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to get the name of the trip (startLocation to endLocation)
    public String getName() {
        String query = "SELECT r.StartLocation, r.EndLocation FROM Route r JOIN Trip t ON r.RouteID = t.RouteID WHERE t.TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, this.tripID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String startLocation = rs.getString("StartLocation");
                String endLocation = rs.getString("EndLocation");
                return startLocation + " to " + endLocation;
            } else {
                return "Route not found for this trip.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error retrieving route.";
        }
    }

    // Method to calculate the time taken for the trip
    public long getTimeTakenForTrip() {
        long duration = endTime.getTime() - startTime.getTime(); // duration in milliseconds
        return duration / (1000 * 60); // convert to minutes
    }

    // Method to print all trips
    public void printAllTrips() {
        String query = "SELECT * FROM Trip";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int tripID = rs.getInt("TripID");
                int routeID = rs.getInt("RouteID");
                int busID = rs.getInt("BusID");
                Timestamp startTime = rs.getTimestamp("StartTime");
                Timestamp endTime = rs.getTimestamp("EndTime");
                double price = rs.getDouble("Price");
                System.out.println("ID: " + tripID +
                        ", RouteID: " + routeID +
                        ", BusID: " + busID +
                        ", Start Time: " + startTime +
                        ", End Time: " + endTime +
                        ", Price: " + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print all ongoing trips
    public void printOngoingTrips() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String query = "SELECT * FROM Trip WHERE StartTime <= ? AND EndTime >= ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, currentTime);
            stmt.setTimestamp(2, currentTime);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int tripID = rs.getInt("TripID");
                int routeID = rs.getInt("RouteID");
                int busID = rs.getInt("BusID");
                Timestamp startTime = rs.getTimestamp("StartTime");
                Timestamp endTime = rs.getTimestamp("EndTime");
                double price = rs.getDouble("Price");
                System.out.println("ID: " + tripID +
                        ", RouteID: " + routeID +
                        ", BusID: " + busID +
                        ", Start Time: " + startTime +
                        ", End Time: " + endTime +
                        ", Price: " + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print upcoming trips
    public void printUpcomingTrips() {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String query = "SELECT * FROM Trip WHERE StartTime > ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setTimestamp(1, currentTime);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int tripID = rs.getInt("TripID");
                int routeID = rs.getInt("RouteID");
                int busID = rs.getInt("BusID");
                Timestamp startTime = rs.getTimestamp("StartTime");
                Timestamp endTime = rs.getTimestamp("EndTime");
                double price = rs.getDouble("Price");
                System.out.println("ID: " + tripID +
                        ", RouteID: " + routeID +
                        ", BusID: " + busID +
                        ", Start Time: " + startTime +
                        ", End Time: " + endTime +
                        ", Price: " + price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a trip by ID
    public void deleteTripByID(int tripID) {
        String query = "DELETE FROM Trip WHERE TripID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, tripID);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Trip with ID " + tripID + " has been deleted.");
            } else {
                System.out.println("No trip found with ID: " + tripID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to prompt for a Timestamp input with validation
    private Timestamp promptForTimestamp(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                dateFormat.setLenient(false);
                java.util.Date parsedDate = dateFormat.parse(input);
                return new Timestamp(parsedDate.getTime());
            } catch (ParseException e) {
                System.out.println("Invalid date and time format. Please use YYYY-MM-DD HH:MM:SS format.");
            }
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        // Example usage
        Scanner sc = new Scanner(System.in);
        Trip trip = new Trip(); // This will prompt for trip details and add to DB

        // Test printing all trips
        System.out.println("All Trips:");
        trip.printAllTrips();

        // Test printing ongoing trips
        System.out.println("\nOngoing Trips:");
        trip.printOngoingTrips();

        // Test printing upcoming trips
        System.out.println("\nUpcoming Trips:");
        trip.printUpcomingTrips();

        // Test deleting a trip by ID
        System.out.print("\nEnter ID of trip to delete: ");
        int idToDelete = sc.nextInt();
        trip.deleteTripByID(idToDelete);

        // Verify deletion
        System.out.println("\nAll Trips after deletion:");
        trip.printAllTrips();
    }
}
