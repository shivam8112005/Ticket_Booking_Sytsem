package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.HashSet;

public class Bus {
    private int busID;
    private String numberPlate;
    private int numberOfSeats;

    private static final String URL = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final Scanner scanner = new Scanner(System.in);

    // No-argument constructor
    public Bus() {
        System.out.println("Enter Bus Details:");
        setNumberPlate();
        System.out.print("Enter number of seats: ");
        this.numberOfSeats = scanner.nextInt();
        addBusToDB(numberPlate, numberOfSeats);
    }

    // Parameterized constructor
    public Bus(int busID, String numberPlate, int numberOfSeats) {
        this.busID = busID;
        this.numberPlate = numberPlate;
        this.numberOfSeats = numberOfSeats;
    }

    // Constructor to access methods
    public Bus(int a) {
        // A constructor that is only to access methods
    }

    // Method to set the number plate with validation
    public void setNumberPlate() {
        // Define the pattern to match the number plate format
        // The format is two uppercase letters, followed by two digits,
        // followed by two uppercase letters, and ending with up to four digits.
        // Example: AB01XY1234
        String platePattern = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{1,4}$";

        while (true) {
            System.out.print("Enter number plate: ");
            String input = scanner.nextLine();

            // Check if the input matches the plate pattern and is not too long
            // Adjust the length if needed according to your database schema
            if (Pattern.matches(platePattern, input) && input.length() <= 10) {
                this.numberPlate = input;
                break;
            } else {
                System.out.println("Invalid number plate format. Please re-enter.");
                System.out.println(
                        "Expected format: AB01XY1234 (two letters, two digits, two letters, up to four digits)");
            }
        }
    }

    // Method to add the bus details to the database
    public void addBusToDB(String numberPlate, int numberOfSeats) {
        String query = "INSERT INTO Bus (NumberPlate, NumberOfSeats) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, numberPlate);
            stmt.setInt(2, numberOfSeats);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.busID = rs.getInt(1);
                System.out.println("Bus added with ID: " + this.busID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a Bus object from the database using the BusID
    public static Bus getBusFromDB(int busID) {
        String query = "SELECT * FROM Bus WHERE BusID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String numberPlate = rs.getString("NumberPlate");
                int numberOfSeats = rs.getInt("NumberOfSeats");
                return new Bus(busID, numberPlate, numberOfSeats);
            } else {
                System.out.println("No bus found with ID: " + busID);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to update the number plate in the database
    public void updateNumberPlate(String newNumberPlate) {
        String query = "UPDATE Bus SET NumberPlate = ? WHERE BusID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newNumberPlate);
            stmt.setInt(2, this.busID);
            stmt.executeUpdate();
            this.numberPlate = newNumberPlate;
            System.out.println("Number plate updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the number of seats in the database
    public void updateNumberOfSeats(int newNumberOfSeats) {
        String query = "UPDATE Bus SET NumberOfSeats = ? WHERE BusID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newNumberOfSeats);
            stmt.setInt(2, this.busID);
            stmt.executeUpdate();
            this.numberOfSeats = newNumberOfSeats;
            System.out.println("Number of seats updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print all buses
    public static void printAllBuses() {
        String query = "SELECT * FROM AllBus";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int busID = rs.getInt("BusID");
                String numberPlate = rs.getString("NumberPlate");
                int numberOfSeats = rs.getInt("NumberOfSeats");
                System.out.println("ID: " + busID + ", Plate: " + numberPlate + ", Seats: " + numberOfSeats);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a bus by ID
    public static void deleteBusByID(int busID) {
        String query = "DELETE FROM Bus WHERE BusID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, busID);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bus with ID " + busID + " deleted.");
            } else {
                System.out.println("No bus found with ID: " + busID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public HashSet<Integer> getAllBusIDs() {
        String query = "SELECT BusID FROM AllBusIDs";
        HashSet<Integer> busIDSet = new HashSet<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int busID = rs.getInt("BusID");
                busIDSet.add(busID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return busIDSet;
    }

    // Main method for testing
    public static void main(String[] args) {
        // Test adding a bus
        Bus bus1 = new Bus(); // Prompts for input
        Bus bus2 = new Bus(); // Prompts for input
        Bus bus3 = new Bus(); // Prompts for input
        Bus bus4 = new Bus(); // Prompts for input

        // // Test retrieving a bus
        // Bus retrievedBus = Bus.getBusFromDB(bus1.getBusID());
        // if (retrievedBus != null) {
        // System.out.println(
        // "Retrieved Bus: " + retrievedBus.getNumberPlate() + ", Seats: " +
        // retrievedBus.getNumberOfSeats());
        // }

        // // Test updating the number plate
        // if (retrievedBus != null) {
        // retrievedBus.updateNumberPlate("XX99 AA 1234");
        // }

        // // Test updating the number of seats
        // if (retrievedBus != null) {
        // retrievedBus.updateNumberOfSeats(50);
        // }

        // // Test printing all buses
        // System.out.println("All buses:");
        // printAllBuses();

        // // Test deleting a bus
        // deleteBusByID(bus1.getBusID());

        // Test printing all buses after deletion
        System.out.println("All buses after deletion:");
        printAllBuses();
    }

    // Getters and setters
    public int getBusID() {
        return busID;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }
}
