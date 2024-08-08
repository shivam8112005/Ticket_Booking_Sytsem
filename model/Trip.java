import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Trip {
    private int tripID;
    private int routeID;
    private int busID;
    private Timestamp startTime;
    private Timestamp endTime;

    private static String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static String user = "root";
    private static String password = "";

    private static final Scanner scanner = new Scanner(System.in);

    // No-argument constructor
    public Trip() {
        System.out.println("Enter Trip Details:");

        System.out.print("Enter Route ID: ");
        this.routeID = scanner.nextInt();

        System.out.print("Enter Bus ID: ");
        this.busID = scanner.nextInt();

        scanner.nextLine(); // consume line

        System.out.print("Enter Start Time (YYYY-MM-DD HH:MM:SS): ");
        this.startTime = Timestamp.valueOf(scanner.nextLine());

        System.out.print("Enter End Time (YYYY-MM-DD HH:MM:SS): ");
        this.endTime = Timestamp.valueOf(scanner.nextLine());

        addTripToDB(routeID, busID, startTime, endTime);
    }

    // Parameterized constructor
    public Trip(int tripID, int routeID, int busID, Timestamp startTime, Timestamp endTime) {
        this.tripID = tripID;
        this.routeID = routeID;
        this.busID = busID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Method to add the trip details to the database
    public void addTripToDB(int routeID, int busID, Timestamp startTime, Timestamp endTime) {

        String query = "INSERT INTO Trip (RouteID, BusID, StartTime, EndTime) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, routeID);
            stmt.setInt(2, busID);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.tripID = rs.getInt(1);
                System.out.println("Trip added with ID: " + this.tripID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a Trip object from the database using the TripID
    public static Trip getTripFromDB(int tripID) {

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
                return new Trip(tripID, routeID, busID, startTime, endTime);
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
}
