package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.HashSet;

public class Route {
    private int routeID;
    private String startLocation;
    private String endLocation;

    private static String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static String user = "root";
    private static String password = "";

    private static final Scanner scanner = new Scanner(System.in);

    public Route() {
        System.out.println("Enter Route Details:");
        System.out.print("Enter start location: ");
        this.startLocation = scanner.nextLine();
        System.out.print("Enter end location: ");
        this.endLocation = scanner.nextLine();
        addRouteToDB(startLocation, endLocation);
    }

    public Route(int routeID, String startLocation, String endLocation) {
        this.routeID = routeID;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    public Route(int a) {
    }

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

    public void updateStartLocationInDB(int routeID, String newStartLocation) {
        String query = "UPDATE Route SET StartLocation = ? WHERE RouteID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newStartLocation);
            stmt.setInt(2, routeID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Route start location updated successfully.");
                this.startLocation = newStartLocation;
            } else {
                System.out.println("No route found with ID: " + routeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEndLocationInDB(int routeID, String newEndLocation) {
        String query = "UPDATE Route SET EndLocation = ? WHERE RouteID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newEndLocation);
            stmt.setInt(2, routeID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Route end location updated successfully.");
                this.endLocation = newEndLocation;
            } else {
                System.out.println("No route found with ID: " + routeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRouteFromDB(int routeID) {
        String query = "DELETE FROM Route WHERE RouteID = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, routeID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Route deleted successfully.");
            } else {
                System.out.println("No route found with ID: " + routeID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printAllRoutes() {
        String query = "SELECT * FROM Route";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            System.out.println("Routes:");
            while (rs.next()) {
                int id = rs.getInt("RouteID");
                String startLocation = rs.getString("StartLocation");
                String endLocation = rs.getString("EndLocation");
                System.out.println("ID: " + id +
                        ", Start Location: " + startLocation +
                        ", End Location: " + endLocation);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Route route1 = new Route();
        Route route3 = new Route();
        Route route2 = Route.getRouteFromDB(route1.getRouteID());
        if (route2 != null) {
            route2.updateStartLocationInDB(route2.getRouteID(), "New Start Location");
            route2.updateEndLocationInDB(route2.getRouteID(), "New End Location");
        }

        Route.printAllRoutes();
        // route2.deleteRouteFromDB(route2.getRouteID());
        Route.printAllRoutes();
    }

    public static HashSet<Integer> getAllRouteIDs() {
        HashSet<Integer> routeIDSet = new HashSet<>();
        String query = "SELECT RouteID FROM AllRouteIDs";

        try (Connection conn = DriverManager.getConnection(url, user, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                routeIDSet.add(rs.getInt("RouteID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return routeIDSet;
    }

    // Getters and setters
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
