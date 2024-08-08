import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Route {
    private int routeID;
    private String startLocation;
    private String endLocation;

    private static String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static String user = "root";
    private static String password = "";

    private static final Scanner scanner = new Scanner(System.in);

    // No-argument constructor
    public Route() {
        System.out.println("Enter Route Details:");
        System.out.print("Enter start location: ");
        this.startLocation = scanner.nextLine();
        System.out.print("Enter end location: ");
        this.endLocation = scanner.nextLine();
        addRouteToDB(startLocation, endLocation);
    }

    // Parameterized constructor
    public Route(int routeID, String startLocation, String endLocation) {
        this.routeID = routeID;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    // Method to add the route details to the database
    public void addRouteToDB(String startLocation, String endLocation) {
        String query = "INSERT INTO Route (StartLocation, EndLocation) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, startLocation);
            stmt.setString(2, endLocation);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.routeID = rs.getInt(1);
                System.out.println("Route added with ID: " + this.routeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a Route object from the database using the RouteID
    public static Route getRouteFromDB(int routeID) {
        String query = "SELECT * FROM Route WHERE RouteID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, routeID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String startLocation = rs.getString("StartLocation");
                String endLocation = rs.getString("EndLocation");
                return new Route(routeID, startLocation, endLocation);
            } else {
                System.out.println("No route found with ID: " + routeID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters and setters (optional, depending on your needs)
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getRouteID() {
        return routeID;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }
}
