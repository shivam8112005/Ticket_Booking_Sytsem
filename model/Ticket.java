package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Scanner;

public class Ticket {

    private int ticketID;
    private int tripID;
    private int bookedBy; // CustomerID
    private int bookedFor; // PassengerID
    private Timestamp bookTime;

    private final String url = "jdbc:mysql://localhost:3306/ticket_booking_db";
    private final String user = "root";
    private final String password = "";

    private final Scanner scanner = new Scanner(System.in);

    // No-parameter constructor
    public Ticket() {
        System.out.println("Enter Ticket Details");

        System.out.println("Enter Trip ID: ");
        this.tripID = scanner.nextInt();

        System.out.println("Enter Customer ID (Booked By): ");
        this.bookedBy = scanner.nextInt();

        System.out.println("Enter Passenger ID (Booked For): ");
        this.bookedFor = scanner.nextInt();

        // Capture the current timestamp for bookTime
        this.bookTime = new Timestamp(System.currentTimeMillis());

        // Call the method to add the ticket to the database
        addTicketToDB(this.tripID, this.bookedBy, this.bookedFor, this.bookTime);
    }

    // Parameterized constructor
    public Ticket(int ticketID, int tripID, int bookedBy, int bookedFor, Timestamp bookTime) {
        this.ticketID = ticketID;
        this.tripID = tripID;
        this.bookedBy = bookedBy;
        this.bookedFor = bookedFor;
        this.bookTime = bookTime;
    }

    public Ticket(int a) {
        // A constructor that is only to access methods
    }

    // Method to add a ticket to the database
    public void addTicketToDB(int tripID, int bookedBy, int bookedFor, Timestamp bookTime) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO Ticket (TripID, BookedBy, BookedFor, BookTime) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, tripID);
            preparedStatement.setInt(2, bookedBy);
            preparedStatement.setInt(3, bookedFor);
            preparedStatement.setTimestamp(4, bookTime);

            preparedStatement.executeUpdate();
            System.out.println("Ticket added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve a ticket from the database using the TicketID
    public Ticket getTicketFromDB(int ticketID) {
        Ticket ticket = null;

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            String sql = "SELECT * FROM Ticket WHERE TicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, ticketID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("TicketID");
                int tripID = resultSet.getInt("TripID");
                int bookedBy = resultSet.getInt("BookedBy");
                int bookedFor = resultSet.getInt("BookedFor");
                Timestamp bookTime = resultSet.getTimestamp("BookTime");

                ticket = new Ticket(id, tripID, bookedBy, bookedFor, bookTime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticket;
    }

    // Method to update the 'BookedBy' field in the database
    public void updateBookedBy(int ticketID, int newBookedBy) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE Ticket SET BookedBy = ? WHERE TicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newBookedBy);
            preparedStatement.setInt(2, ticketID);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("BookedBy updated successfully.");
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the 'BookedFor' field in the database
    public void updateBookedFor(int ticketID, int newBookedFor) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE Ticket SET BookedFor = ? WHERE TicketID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newBookedFor);
            preparedStatement.setInt(2, ticketID);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("BookedFor updated successfully.");
            } else {
                System.out.println("Ticket not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public int getTicketID() {
        return ticketID;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(int bookedBy) {
        this.bookedBy = bookedBy;
    }

    public int getBookedFor() {
        return bookedFor;
    }

    public void setBookedFor(int bookedFor) {
        this.bookedFor = bookedFor;
    }

    public Timestamp getBookTime() {
        return bookTime;
    }

    public void setBookTime(Timestamp bookTime) {
        this.bookTime = bookTime;
    }

    // Main method for testing
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create a new Ticket
        System.out.println("Creating a new ticket...");
        Ticket newTicket = new Ticket();

        // Display ticket details
        System.out.println("New ticket created with ID: " + newTicket.getTicketID());

        // Update BookedBy for the created ticket
        System.out.println("Enter new Customer ID (Booked By) to update: ");
        int newBookedBy = scanner.nextInt();
        newTicket.updateBookedBy(newTicket.getTicketID(), newBookedBy);

        // Update BookedFor for the created ticket
        System.out.println("Enter new Passenger ID (Booked For) to update: ");
        int newBookedFor = scanner.nextInt();
        newTicket.updateBookedFor(newTicket.getTicketID(), newBookedFor);

        // Retrieve and display the updated ticket
        System.out.println("Retrieving updated ticket...");
        Ticket updatedTicket = newTicket.getTicketFromDB(newTicket.getTicketID());

        if (updatedTicket != null) {
            System.out.println("Updated Ticket Details:");
            System.out.println("Ticket ID: " + updatedTicket.getTicketID());
            System.out.println("Trip ID: " + updatedTicket.getTripID());
            System.out.println("Booked By: " + updatedTicket.getBookedBy());
            System.out.println("Booked For: " + updatedTicket.getBookedFor());
            System.out.println("Booking Time: " + updatedTicket.getBookTime());
        } else {
            System.out.println("Ticket not found.");
        }

        scanner.close();
    }
}
